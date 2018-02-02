package com.thedancercodes.knownspies.ModelLayer.Translation;

import com.thedancercodes.knownspies.ModelLayer.Enums.Gender;
import com.thedancercodes.knownspies.ModelLayer.DTOs.SpyDTO;
import com.thedancercodes.knownspies.ModelLayer.Database.Realm.Spy;

import io.realm.Realm;


public class SpyTranslatorImpl implements SpyTranslator {
    @Override public SpyDTO translate(Spy from) {
        if (from == null) return null;

        Gender gender = Gender.valueOf(from.gender);

        return new SpyDTO(from.id,
                from.age,
                from.name,
                gender,
                from.password,
                from.imageName,
                from.isIncognito);
    }

    @Override public Spy translate(SpyDTO dto, Realm realm) {
        if (dto == null) return null;

        realm.executeTransaction(realm1 -> {
            Spy spy = realm1.createObject(Spy.class);
            spy.id          = dto.id;
            spy.age         = dto.age;
            spy.name        = dto.name;
            spy.gender      = dto.gender.name();
            spy.password    = dto.password;
            spy.imageName   = dto.imageName;
            spy.isIncognito = dto.isIncognito;
        });

        Spy spy = realm.where(Spy.class).equalTo("name", dto.name).findFirst();

        return spy;
    }

}
