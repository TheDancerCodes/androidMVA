package com.thedancercodes.knownspies.Activities.SecretDetails;

import java.util.function.Consumer;

/**
 * Created by TheDancerCodes on 02/02/2018.
 */

public interface SecretDetailsPresenter {

  // Getter Method
  String getPassword();

  void crackPassword(Consumer<String> finished);
}
