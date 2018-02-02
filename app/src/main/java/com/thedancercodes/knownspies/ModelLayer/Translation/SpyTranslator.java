package com.thedancercodes.knownspies.ModelLayer.Translation;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import io.realm.Realm;

/**
 * Created by TheDancerCodes on 01/02/2018.
 */

public interface SpyTranslator {
  SpyDTO translate(Spy from);

  Spy translate(SpyDTO dto, Realm realm);
}
