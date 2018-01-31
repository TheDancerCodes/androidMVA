package com.thedancercodes.knownspies.Dependencies;

import com.google.gson.Gson;
import com.thedancercodes.knownspies.ModelLayer.Database.DataLayer;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import com.thedancercodes.knownspies.ModelLayer.Network.NetworkLayer;
import com.thedancercodes.knownspies.ModelLayer.Translation.SpyTranslator;
import com.thedancercodes.knownspies.ModelLayer.Translation.TranslationLayer;
import io.realm.Realm;

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
}
