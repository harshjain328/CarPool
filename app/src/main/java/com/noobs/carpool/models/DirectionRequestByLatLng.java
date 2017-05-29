package com.noobs.carpool.models;

/**
 * Created by deepak on 28/5/17.
 */

public class DirectionRequestByLatLng implements DirectionRequest{

    private float sourceLat;
    private float sourceLng;
    private float destLat;
    private float destLng;

    public DirectionRequestByLatLng(){

    }

    public DirectionRequestByLatLng(float sourceLat, float sourceLng, float destLat, float destLng){
        this.setSourceLat(sourceLat);
        this.setSourceLng(sourceLng);
        this.setDestLat(destLat);
        this.setDestLng(destLng);
    }

    public float getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(float sourceLat) {
        this.sourceLat = sourceLat;
    }

    public float getSourceLng() {
        return sourceLng;
    }

    public void setSourceLng(float sourceLng) {
        this.sourceLng = sourceLng;
    }

    public float getDestLat() {
        return destLat;
    }

    public void setDestLat(float destLat) {
        this.destLat = destLat;
    }

    public float getDestLng() {
        return destLng;
    }

    public void setDestLng(float destLng) {
        this.destLng = destLng;
    }

    @Override
    public String getSource() {
        return getSourceLat()+", "+getSourceLng();
    }

    @Override
    public String getDestination() {
        return getDestLat() + ", " + getDestLng();
    }
}
