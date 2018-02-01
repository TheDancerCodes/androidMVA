package com.thedancercodes.knownspies.Dependencies;

import android.os.Bundle;
import com.google.gson.Gson;
import com.thedancercodes.knownspies.Activities.Details.SpyDetailsActivity;
import com.thedancercodes.knownspies.Activities.Details.SpyDetailsPresenter;
import com.thedancercodes.knownspies.Helpers.Constants;
import com.thedancercodes.knownspies.ModelLayer.Database.DataLayer;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import com.thedancercodes.knownspies.ModelLayer.Network.NetworkLayer;
import com.thedancercodes.knownspies.ModelLayer.Translation.SpyTranslator;
import com.thedancercodes.knownspies.ModelLayer.Translation.TranslationLayer;
import io.realm.Realm;
import java.util.NoSuchElementException;

/**
 * Created by TheDancerCodes on 26/01/2018.
 */

public class DependencyRegistry {
  public static DependencyRegistry shared = new DependencyRegistry();

  private Gson gson = new Gson();

  //region Realm

  private Realm realm = Realm.getDefaultInstance();

  // Method to deal with threading issues in Realm
  public Realm newRealmInstanceOnCurrentThread() {
    return Realm.getInstance(realm.getConfiguration());
  }

  //endregion

  //region Singletons

  // These Singletons don’t have a shared instance
  public SpyTranslator spyTranslator = new SpyTranslator();

  public TranslationLayer translationLayer = createTranslationLayer();

  private TranslationLayer createTranslationLayer() {
    return new TranslationLayer(gson, spyTranslator);
  }

  public DataLayer dataLayer = createDataLayer();

  private DataLayer createDataLayer() {
    return new DataLayer(realm, this::newRealmInstanceOnCurrentThread);
  }

  public NetworkLayer networkLayer = new NetworkLayer();

  public ModelLayer modelLayer = createModelLayer();

  private ModelLayer createModelLayer() {
    return new ModelLayer(networkLayer, dataLayer, translationLayer);
  }

  //endregion

  public void inject(SpyDetailsActivity activity, Bundle bundle) throws NoSuchElementException {
    int spyId = idFromBundle(bundle);

    SpyDetailsPresenter presenter = new SpyDetailsPresenter(spyId);
    activity.configureWith(presenter);

  }

  //region Helper Methods

  private int idFromBundle(Bundle bundle) {
    if (bundle == null) throw new NoSuchElementException("Unable to get spy id from bundle");

    int spyId = bundle.getInt(Constants.spyIdKey);
    if (spyId == 0) throw new NoSuchElementException("Unable to get spy id from bundle");
    return spyId;
  }

  //endregion
}
