package com.noobs.carpool.models;

/**
 * Created by deepak on 28/5/17.
 */

public class DirectionRequestByPlace implements DirectionRequest {

    private String source;
    private String destination;

    public DirectionRequestByPlace(String source, String destination){
        this.source = source;
        this.destination = destination;
    }

    @Override
    public String getSource() {
        return source;
    }

    @Override
    public String getDestination() {
        return destination;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
