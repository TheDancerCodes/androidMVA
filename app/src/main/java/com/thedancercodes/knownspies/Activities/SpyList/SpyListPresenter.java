package com.thedancercodes.knownspies.Activities.SpyList;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by TheDancerCodes on 02/02/2018.
 */

public interface SpyListPresenter {
  // provide a way to oad the data a layer on up
  void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived);
}
