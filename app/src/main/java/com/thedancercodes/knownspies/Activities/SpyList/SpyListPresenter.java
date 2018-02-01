package com.thedancercodes.knownspies.Activities.SpyList;

import android.support.annotation.Nullable;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import com.thedancercodes.knownspies.ModelLayer.Translation.SpyTranslator;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmResults;
import java.io.IOException;
import java.util.List;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

public class SpyListPresenter {

  private static final String TAG = "SpyListPresenter";

  // ModelLayer Variable
  ModelLayer modelLayer;

  private Realm realm = Realm.getDefaultInstance();

  public SpyListPresenter(ModelLayer modelLayer) {
    this.modelLayer = modelLayer;
  }

  // region Presenter methods

  // provide a way to oad the data a layer on up
  public void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived) {

    modelLayer.loadData(onNewResults, notifyDataReceived);

  }

  // endregion

}
