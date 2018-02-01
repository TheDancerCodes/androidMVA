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

  // Its the job of the Presenter to provide the data in the shape that the View Controller needs.
  public int spyId;
  public String age;
  public String name;
  public String gender;
  public String imageName;
  public int imageId;

  private SpyDTO spy;
  private Context context;
  private ModelLayer modelLayer;   // Instance of the ModelLayer

  // Initialise variables in this Constructor.
  public SpyDetailsPresenter(int spyId, Context context, ModelLayer modelLayer) {
    this.spyId = spyId;
    this.context = context;
    this.modelLayer = modelLayer;

    configureData();
  }

  private void configureData() {
    spy = modelLayer.spyForId(spyId);

    age = String.valueOf(spy.age);
    name = spy.name;
    gender = spy.gender.name();
    imageName = spy.imageName;

    imageId = Helper.resourceIdWith(context, imageName);
  }
}
