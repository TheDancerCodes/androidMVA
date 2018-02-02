package com.thedancercodes.knownspies.ModelLayer.Translation;

import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.Enums.DTOType;
import io.realm.Realm;
import java.util.List;

/**
 * Created by TheDancerCodes on 25/01/2018.
 */

public class TranslationLayerImpl implements TranslationLayer {

  private static final String TAG = "TranslationLayer";

  private Gson gson;

  private SpyTranslator spyTranslator;

  // Constructor
  public TranslationLayerImpl(Gson gson, SpyTranslator spyTranslator) {
    this.gson = gson;
    this.spyTranslator = spyTranslator;
  }

  @Override public List<SpyDTO> convertJson(String json) {
    Log.d(TAG, "converting JSON to DTOs");

    // Use GSON parser to convert incoming JSON to our list of SpyDTO objects
    TypeToken<List<SpyDTO>> token = new TypeToken<List<SpyDTO>>() {
    }; // an anonymous object

    return gson.fromJson(json, token.getType());
  }

  // A method to translate out of this
  @Override public SpyTranslator translatorFor(DTOType type) {
    switch (type) {
      case spy:
        return spyTranslator;
      default:
        return spyTranslator;
    }
  }

  @Override public SpyDTO translate(Spy spy) {
    SpyDTO dto = spyTranslator.translate(spy);
    return dto;
  }

  @Override public Spy translate(SpyDTO dto, Realm realm) {
    Spy spy = spyTranslator.translate(dto, realm);
    return spy;
  }
}
