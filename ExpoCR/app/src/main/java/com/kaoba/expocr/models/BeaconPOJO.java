package com.kaoba.expocr.models;

/**
 * Created by valeriaramirez on 7/17/17.
 */

public class BeaconPOJO {
    private Long id;
    private String uuid;

    @Override
    public String toString() {
        return super.toString();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }
}
