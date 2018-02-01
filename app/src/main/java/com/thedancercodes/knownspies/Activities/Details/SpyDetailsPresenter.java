package com.thedancercodes.knownspies.Activities.Details;

import android.content.Context;
import com.thedancercodes.knownspies.Helpers.Helper;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import io.realm.Realm;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

public class SpyDetailsPresenter {

  // Instance of the ModelLayer
  ModelLayer modelLayer = new ModelLayer();

  // Its the job of the Presenter to provide the data in the shape that the View Controller needs.
  public int spyId;
  public String age;
  public String name;
  public String gender;
  public String imageName;
  public int imageId;

  private Context context;
  private SpyDTO spy;

  public SpyDetailsPresenter(int spyId) {
    this.spyId = spyId;

    spy = modelLayer.spyForId(spyId);

    configureSpy();
  }

  private void configureSpy() {
    age = String.valueOf(spy.age);
    name = spy.name;
    gender = spy.gender.name();
    imageName = spy.imageName;
  }

  public void configureWithContext(Context context) {
    this.context = context;

    imageId = Helper.resourceIdWith(context, imageName);

  }
}
