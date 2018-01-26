package com.thedancercodes.knownspies.ModelLayer;

import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.DataLayer;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.Enums.DTOType;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import com.thedancercodes.knownspies.ModelLayer.Network.NetworkLayer;
import com.thedancercodes.knownspies.ModelLayer.Translation.SpyTranslator;
import com.thedancercodes.knownspies.ModelLayer.Translation.TranslationLayer;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by TheDancerCodes on 25/01/2018.
 */

public class ModelLayer {

  // Instances of the other layers
  private NetworkLayer networkLayer = new NetworkLayer();
  private DataLayer dataLayer = new DataLayer();
  private TranslationLayer translationLayer = new TranslationLayer();

  public void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived) {

    // Translator for the SpyDTO that we are passing in
    SpyTranslator translator = translationLayer.translatorFor(SpyDTO.dtoType);

    // load from local DB
    try {
      dataLayer.loadSpiesFromLocal(translator::translate, onNewResults);
      notifyDataReceived.accept(Source.local);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // load from network
    networkLayer.loadJson(json -> {
      notifyDataReceived.accept(Source.network);
      persistJson(json, () -> dataLayer.loadSpiesFromLocal(translator::translate, onNewResults));
    });
  }

  public SpyDTO spyForId(int spyId) {
    Spy spy = dataLayer.spyForId(spyId);
    SpyDTO spyDTO = translationLayer.translate(spy);
    return spyDTO;
  }

  private void persistJson(String json, Action finished) {
    List<SpyDTO> dtos = translationLayer.convertJson(json);

    // Use Threading lib to trigger the block on another thread.
    Threading.async(()->{
      dataLayer.clearSpies(() -> {
        dtos.forEach(dto -> dto.initialize()); // Sets up the image id from the image name.

        SpyTranslator translator = translationLayer.translatorFor(SpyDTO.dtoType);
        dataLayer.persistDTOs(dtos, translator::translate);

        Threading.dispatchMain(() -> finished.run());
      });
      return true;
    });
  }
}
