package com.thedancercodes.knownspies.ModelLayer.Translation;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.Enums.DTOType;
import io.realm.Realm;
import java.util.List;

/**
 * Created by TheDancerCodes on 02/02/2018.
 */

public interface TranslationLayer {
  List<SpyDTO> convertJson(String json);

  // A method to translate out of this
  SpyTranslator translatorFor(DTOType type);

  SpyDTO translate(Spy spy);

  Spy translate(SpyDTO dto, Realm realm);
}
