package com.thedancercodes.knownspies.ModelLayer.Database;

import android.util.Log;
import com.thedancercodes.knownspies.Helpers.Threading;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.List;

/**
 * Created by TheDancerCodes on 25/01/2018.
 */

public class DataLayer {

  private static final String TAG = "DataLayer";

  //region Database Methods

  public void loadSpiesFromLocal(Consumer<List<Spy>> onNewResults) throws Exception {
    Log.d(TAG, "Loading spies from DB");
    loadSpiesFromRealm(spyList -> {
      onNewResults.accept(spyList);
    });
  }

  private void persistJson(String json, Action finished) {
    Threading.async(() -> {

      clearSpies(() -> {
        List<SpyDTO> dtos = convertJson(json);
        dtos.forEach(dto -> dto.initialize());
        persistDTOs(dtos);

        Threading.dispatchMain(() -> finished.run());
      });

      return true;
    });
  }

  private void loadSpiesFromRealm(Consumer<List<Spy>> finished) throws Exception {
    RealmResults<Spy> spyResults = realm.where(Spy.class).findAll();

    List<Spy> spies = realm.copyFromRealm(spyResults);
    finished.accept(spies);
  }

  private void clearSpies(Action finished) throws Exception {
    Log.d(TAG, "clearing DB");

    Realm backgroundRealm = Realm.getInstance(realm.getConfiguration());
    backgroundRealm.executeTransaction(r -> r.delete(Spy.class));

    finished.run();
  }

  private void persistDTOs(List<SpyDTO> dtos) {
    Log.d(TAG, "persisting dtos to DB");

    Realm backgroundRealm = Realm.getInstance(realm.getConfiguration());
    backgroundRealm.executeTransaction(r -> r.delete(Spy.class));

    //ignore result and just save in realm
    dtos.forEach(dto -> spyTranslator.translate(dto, backgroundRealm));
  }

  //endregion

}
