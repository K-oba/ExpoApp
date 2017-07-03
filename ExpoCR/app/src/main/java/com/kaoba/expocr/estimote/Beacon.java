package com.kaoba.expocr.estimote;

import com.estimote.coresdk.observation.region.beacon.BeaconRegion;

import java.util.UUID;

public class Beacon {

    private UUID proximityUUID;
    private int major;
    private int minor;

    public Beacon(UUID proximityUUID, int major, int minor) {
        this.proximityUUID = proximityUUID;
        this.major = major;
        this.minor = minor;
    }

    public Beacon(String UUIDString, int major, int minor) {
        this(UUID.fromString(UUIDString), major, minor);
    }

    public UUID getProximityUUID() {
        return proximityUUID;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public BeaconRegion toBeaconRegion() {
        return new BeaconRegion(toString(), getProximityUUID(), getMajor(), getMinor());
    }

    public String toString() {
        return getProximityUUID().toString() + ":" + getMajor() + ":" + getMinor();
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (getClass() != o.getClass()) {
            return super.equals(o);
        }

        Beacon other = (Beacon) o;

        return getProximityUUID().equals(other.getProximityUUID())
                && getMajor() == other.getMajor()
                && getMinor() == other.getMinor();
    }
}
