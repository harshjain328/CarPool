package com.noobs.carpool.services.listeners;

/**
 * Created by deepak on 11/5/17.
 */

public interface NetworkStatusListener {

    /**
     * onChange() will be fired whenever client's network status changes.
     * @param status param indicates whether network(Internet) is available or not.
     */
    public void onChange(boolean status);

}
