package com.thedancercodes.knownspies.ModelLayer.Database;

import android.util.Log;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import com.thedancercodes.knownspies.ModelLayer.Translation.SpyTranslator;

import io.reactivex.Observable;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.Realm;
import io.realm.RealmResults;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by TheDancerCodes on 25/01/2018.
 */

public class DataLayer {

  private static final String TAG = "DataLayer";

  // Realm instance
  private Realm realm;

  private Callable<Realm> newRealmInstanceOnCurrentThread;

  public DataLayer(Realm realm, Callable<Realm> newRealmInstanceOnCurrentThread) {
    this.realm = realm;
    this.newRealmInstanceOnCurrentThread = newRealmInstanceOnCurrentThread;
  }

  //region Database Methods

  public void loadSpiesFromLocal(Function<Spy, SpyDTO> translationBlock,
      Consumer<List<SpyDTO>> onNewResults) throws Exception {
    Log.d(TAG, "Loading spies from DB");
    loadSpiesFromRealm(spies -> {
      List<SpyDTO> dtos= translate(spies, translationBlock);
      onNewResults.accept(dtos);
    });
  }

  private List<SpyDTO> translate(List<Spy> spies, Function<Spy, SpyDTO> translationBlock) {
    // Use RxJava to map the spies coming in, to a SpyDTO Object going out
    List<SpyDTO> dtos = Observable.fromArray(spies)
                                  .flatMapIterable(list -> list)
                                  .map(translationBlock::apply)
                                  .toList()
                                  .blockingGet();
    return dtos;
  }

  private void loadSpiesFromRealm(Consumer<List<Spy>> finished) {
    RealmResults<Spy> spyResults = realm.where(Spy.class).findAll();

    List<Spy> spies = realm.copyFromRealm(spyResults);
    try {
      finished.accept(spies);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void clearSpies(Action finished) throws Exception {
    Log.d(TAG, "clearing DB");

    Realm backgroundRealm = newRealmInstanceOnCurrentThread.call();
    backgroundRealm.executeTransaction(r -> r.delete(Spy.class));
    backgroundRealm.close();

    finished.run();
  }

  public void persistDTOs(List<SpyDTO> dtos, BiFunction<SpyDTO, Realm, Spy> translationBlock) {
    Log.d(TAG, "persisting dtos to DB");

    try {
      Realm backgroundRealm = newRealmInstanceOnCurrentThread.call();

      //ignore result and just save in realm
      dtos.forEach(dto -> convertToSpy(translationBlock, backgroundRealm, dto));
      backgroundRealm.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void convertToSpy(BiFunction<SpyDTO, Realm, Spy> translationBlock, Realm backgroundRealm,
      SpyDTO dto) {
    try {
      translationBlock.apply(dto, backgroundRealm);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  //endregion


  // To load spy within the other presenters
  public Spy spyForId(int spyId) {
    Spy tempSpy = realm.where(Spy.class).equalTo("id", spyId).findFirst();
    return realm.copyFromRealm(tempSpy);
  }

}
