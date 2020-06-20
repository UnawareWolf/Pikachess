package com.example.pikachess.game.battle;

import com.example.pikachess.game.battle.pikamen.Pikamuno;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class PikaType {

    protected PikaTypeID id;
    protected Set<PikaTypeID> weaknesses;
    protected Set<PikaTypeID> resistances;
    protected Set<PikaTypeID> immunities;

    public PikaType(PikaTypeID id) {
        this.id = id;
        weaknesses = new HashSet<>();
        resistances = new HashSet<>();
        immunities = new HashSet<>();
        setIDLists();
    }

    public double getEffectivenessMultiplier(Set<PikaTypeID> typeIDs) {
        double effectivenessMultiplier = 1;
        for (PikaTypeID typeID : typeIDs) {
            effectivenessMultiplier *= getEffectivenessMultiplier(typeID);
        }
        return effectivenessMultiplier;
    }

    public double getEffectivenessMultiplier(PikaTypeID typeID) {
        double effectivenessMultiplier = 1;
        if (getWeak(typeID)) {
            effectivenessMultiplier = 2;
        }
        else if (getResisted(typeID)) {
            effectivenessMultiplier = 0.5;
        }
        else if (getImmune(typeID)) {
            effectivenessMultiplier = 0;
        }
        return effectivenessMultiplier;
    }

    protected abstract void setIDLists();

    private boolean getWeak(PikaTypeID typeID) {
        return weaknesses.contains(typeID);
    }

    private boolean getResisted(PikaTypeID typeID) {
        return resistances.contains(typeID);
    }

    private boolean getImmune(PikaTypeID typeID) {
        return immunities.contains(typeID);
    }

    public PikaTypeID getID() {
        return id;
    }

}
