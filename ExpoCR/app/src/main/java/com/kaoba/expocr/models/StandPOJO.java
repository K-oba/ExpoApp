package com.kaoba.expocr.models;

/**
 * Created by Robert on 7/3/17.
 */

public class StandPOJO {
    private String name;
    private Long id;
    private Long beaconId;

    @Override
    public String toString() {
        return name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBeaconId(Long beaconId){this.beaconId = beaconId;}

    public Long getBeaconId(){return beaconId;}

}
