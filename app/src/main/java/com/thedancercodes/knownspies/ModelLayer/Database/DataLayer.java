package com.thedancercodes.knownspies.ModelLayer.Database;

import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;
import io.reactivex.functions.Action;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.realm.Realm;
import java.util.List;

/**
 * Created by TheDancerCodes on 02/02/2018.
 */

public interface DataLayer {
  void loadSpiesFromLocal(Function<Spy, SpyDTO> translationBlock,
      Consumer<List<SpyDTO>> onNewResults) throws Exception;

  void clearSpies(Action finished) throws Exception;

  void persistDTOs(List<SpyDTO> dtos, BiFunction<SpyDTO, Realm, Spy> translationBlock);

  // To load spy within the other presenters
  Spy spyForId(int spyId);
}
