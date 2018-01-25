package com.thedancercodes.knownspies.ModelLayer;

import com.thedancercodes.knownspies.ModelLayer.Database.DataLayer;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import com.thedancercodes.knownspies.ModelLayer.Network.NetworkLayer;
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

  public void loadData(Consumer<List<Spy>> onNewResults, Consumer<Source> notifyDataReceived) {

    // load from local DB
    try {
      dataLayer.loadSpiesFromLocal(onNewResults);
      notifyDataReceived.accept(Source.local);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // load from network
    networkLayer.loadJson(json -> {
      notifyDataReceived.accept(Source.network);
      persistJson(json, () -> dataLayer.loadSpiesFromLocal(onNewResults));
    });
  }

  private void persistJson(String json, Action finished) {
    translationLayer.convertJson(json);
  }
}
