package com.thedancercodes.knownspies.Activities.SecretDetails;

import android.view.View;
import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import io.realm.Realm;
import java.util.function.Consumer;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

class SecretDetailsPresenter {

  private ModelLayer modelLayer = new ModelLayer();

  private SpyDTO spy;
  public String password;

  // Constructor
  public SecretDetailsPresenter(int spyId) {
    spy = modelLayer.spyForId(spyId);

    password = spy.password;
  }

  public void crackPassword(Consumer<String> finished) {
    Threading.async(()-> {
      //fake processing work
      Thread.sleep(2000);
      return true;
    }, success -> {
      finished.accept(password);
    });
  }
}
