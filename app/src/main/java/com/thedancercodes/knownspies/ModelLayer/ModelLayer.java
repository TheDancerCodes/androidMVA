package com.thedancercodes.knownspies.ModelLayer;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Enums.Source;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.List;

/**
 * Created by TheDancerCodes on 02/02/2018.
 */

public interface ModelLayer {
  void loadData(Consumer<List<SpyDTO>> onNewResults, Consumer<Source> notifyDataReceived);

  SpyDTO spyForId(int spyId);

  SpyDTO spyForName(String name);

  // So that we can persist it into the DB by handing the specific List as opposed to loading from
  // the network and saving only in that manner.
  void save(List<SpyDTO> dtos, Action finished);
}
