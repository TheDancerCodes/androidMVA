package com.thedancercodes.knownspies.Activities.SpyList;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Enums.Gender;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import com.thedancercodes.knownspies.ModelLayer.ModelLayer;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import io.realm.Realm;
import java.util.Arrays;
import java.util.List;

/**
 * Created by TheDancerCodes on 23/01/2018.
 */

public class SpyListPresenterImpl implements SpyListPresenter {

  private static final String TAG = "SpyListPresenter";

  // ModelLayer Variable
  private ModelLayer modelLayer;

  private BehaviorSubject<List<SpyDTO>> spies = BehaviorSubject.create();

  public SpyListPresenterImpl(ModelLayer modelLayer) {
    this.modelLayer = modelLayer;
  }

  // region Presenter methods

  // provide a way to oad the data a layer on up
  @Override
  public void loadData(Consumer<Source> notifyDataReceived) {

    modelLayer.loadData(this::onDataLoaded, notifyDataReceived);

  }

  private void onDataLoaded(List<SpyDTO> spyList) {

    // onNext pushes out the old list currently in the spies Observable,
    // adds the new list and tells all the subscribers that the list has changed.
    spies.onNext(spyList);
  }

  @Override
  public void addNewSpy() {
    String name = "Adam Smith";
    List<SpyDTO> newSpies =
        Arrays.asList(new SpyDTO(100, 25, name, Gender.male, "wealth", "adamsmith", true));

    modelLayer.save(newSpies, () -> {
      SpyDTO adam = modelLayer.spyForName(name);

      List<SpyDTO> spyList = spies.getValue();
      spyList.add(0, adam);

      // Update the Observable
      spies.onNext(spyList);
    });
  }

  @Override
  public BehaviorSubject<List<SpyDTO>> spies() {
    return spies;
  }

  // endregion

}
