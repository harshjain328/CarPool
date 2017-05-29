package com.noobs.carpool.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by deepak on 11/5/17.
 */

public class Util {

    public static void addSharedPreference(Context context, String key, String value){
//        // Storing request_id in shared-preference for later verification
//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        SharedPreferences.Editor editor = preferences.edit();
//        editor.putString("request_id", requestId);
//        editor.apply();
    }

    public static List<LatLng> decodeRouteFrom(MapModels.DirectionResults directionResultsResponse){
        List<LatLng> routelist = new ArrayList<LatLng>();
        if (directionResultsResponse.getRoutes().size() > 0) {
            ArrayList<LatLng> decodeList;
            MapModels.Route routeA = directionResultsResponse.getRoutes().get(0);
            if (routeA.getLegs().size() > 0) {
                List<MapModels.Steps> steps = routeA.getLegs().get(0).getSteps();
                MapModels.Steps step;
                MapModels.Location location;
                String polyline;
                for (int i = 0; i < steps.size(); i++) {
                    step = steps.get(i);
                    location = step.getStart_location();
                    routelist.add(new LatLng(location.getLat(), location.getLng()));

                    polyline = step.getPolyline().getPoints();
                    decodeList = Util.decodePoly(polyline);
                    routelist.addAll(decodeList);
                    location = step.getEnd_location();
                    routelist.add(new LatLng(location.getLat(), location.getLng()));
                }
            }
        }
        return routelist;
    }

    public static ArrayList<LatLng> decodePoly(String encoded) {
        ArrayList<LatLng> poly = new ArrayList<LatLng>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;
        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;
            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng position = new LatLng((double) lat / 1E5, (double) lng / 1E5);
            poly.add(position);
        }
        return poly;
    }

}
