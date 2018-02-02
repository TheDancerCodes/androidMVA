package com.thedancercodes.knownspies.Activities.SecretDetails;

import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import java.util.function.Consumer;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

public class SecretDetailsPresenterImpl implements SecretDetailsPresenter {

  public String password;

  // Getter Method
  @Override
  public String getPassword() {
    return password;
  }

  private int spyId;
  private SpyDTO spy;
  private ModelLayer modelLayer;

  // Constructor
  public SecretDetailsPresenterImpl(int spyId, ModelLayer modelLayer) {
    this.spyId = spyId;
    this.modelLayer = modelLayer;

    configureData();
  }

  private void configureData() {
    spy = this.modelLayer.spyForId(spyId);
    password = spy.password;
  }

  @Override public void crackPassword(Consumer<String> finished) {
    Threading.async(() -> {
      //fake processing work
      Thread.sleep(2000);
      return true;
    }, success -> {
      finished.accept(password);
    });
  }
}
