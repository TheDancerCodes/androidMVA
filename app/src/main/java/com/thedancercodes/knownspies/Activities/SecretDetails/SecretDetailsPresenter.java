package com.thedancercodes.knownspies.Activities.SecretDetails;

import android.view.View;
import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import io.realm.Realm;
import java.util.function.Consumer;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

class SecretDetailsPresenter {

  private Realm realm = Realm.getDefaultInstance();

  private Spy spy;
  public String password;

  // Constructor
  public SecretDetailsPresenter(int spyId) {
    spy = getSpy(spyId);

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

  //region Data loading
  public Spy getSpy(int id) {
    Spy tempSpy = realm.where(Spy.class).equalTo("id", id).findFirst();
    return realm.copyFromRealm(tempSpy);
  }
  //endregion


}
