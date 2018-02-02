package com.thedancercodes.knownspies.ModelLayer.Network;

import io.reactivex.functions.Consumer;

/**
 * Created by TheDancerCodes on 02/02/2018.
 */

public interface NetworkLayer {
  void loadJson(Consumer<String> finished);
}
