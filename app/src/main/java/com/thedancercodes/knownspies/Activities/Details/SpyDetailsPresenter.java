package com.thedancercodes.knownspies.Activities.Details;

import android.content.Context;
import com.thedancercodes.knownspies.Helpers.Helper;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import io.realm.Realm;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

class SpyDetailsPresenter {

  // Instance to a Realm Object
  private Realm realm = Realm.getDefaultInstance();

  // Its the job of the Presenter to provide the data in the shape that the View Controller needs.
  public int spyId;
  public String age;
  public String name;
  public String gender;
  public String imageName;
  public int imageId;

  private Context context;

  public SpyDetailsPresenter(int spyId) {
    this.spyId = spyId;

    Spy spy = getSpy(spyId);
    age = String.valueOf(spy.age);
    name = spy.name;
    gender = spy.gender;
    imageName = spy.imageName;
  }

  public void configureWithContext(Context context) {
    this.context = context;

    imageId = Helper.resourceIdWith(context, imageName);

  }

  //region Data loading

  private Spy getSpy(int id) {
    Spy tempSpy = realm.where(Spy.class).equalTo("id", id).findFirst();
    return realm.copyFromRealm(tempSpy);
  }

  //endregion

}
