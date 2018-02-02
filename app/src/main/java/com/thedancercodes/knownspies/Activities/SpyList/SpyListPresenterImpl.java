package com.thedancercodes.knownspies.Activities.SpyList;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import java.util.List;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

public class SpyListPresenterImpl implements SpyListPresenter {

  private static final String TAG = "SpyListPresenter";

  // ModelLayer Variable
  ModelLayer modelLayer;

  private Realm realm = Realm.getDefaultInstance();

  public SpyListPresenterImpl(ModelLayer modelLayer) {
    this.modelLayer = modelLayer;
  }

  // region Presenter methods

  // provide a way to oad the data a layer on up
  @Override public void loadData(Consumer<List<SpyDTO>> onNewResults,
      Consumer<Source> notifyDataReceived) {

    modelLayer.loadData(onNewResults, notifyDataReceived);

  }

  // endregion

}
