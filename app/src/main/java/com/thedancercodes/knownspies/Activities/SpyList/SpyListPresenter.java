package com.thedancercodes.knownspies.Activities.SpyList;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.BehaviorSubject;
import java.util.List;

/**
 * Created by TheDancerCodes on 02/02/2018.
 */

public interface SpyListPresenter {
  // provide a way to oad the data a layer on up
  void loadData(Consumer<Source> notifyDataReceived);

  // Method for Activity to trigger Presenter to add a new Spy
  void addNewSpy();

  // BehaviorSubject is an RxJava type - People can use this as an Observable & they can subscribe
  // to any type of changes.
  BehaviorSubject<List<SpyDTO>> spies();
}
