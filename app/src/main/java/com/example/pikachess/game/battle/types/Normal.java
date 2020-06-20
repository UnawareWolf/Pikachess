package com.example.pikachess.game.battle.types;

import com.example.pikachess.game.battle.PikaType;
import com.example.pikachess.game.battle.PikaTypeID;

import java.util.HashSet;

public class Normal extends PikaType {

    public Normal() {
        super(PikaTypeID.Normal);
    }

    @Override
    protected void setIDLists() {
        immunities.add(PikaTypeID.Ghost);
    }
}
