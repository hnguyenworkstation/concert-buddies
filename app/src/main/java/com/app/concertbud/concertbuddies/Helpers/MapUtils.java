package com.app.concertbud.concertbuddies.Helpers;

/**
 * Created by hungnguyen on 3/27/18.
 */

public class MapUtils {
    public static String getMapLocationUrl(String latitudeFinal, String longitudeFinal) {
        return "https://maps.googleapis.com/maps/api/staticmap?center=" + latitudeFinal + ","
                + longitudeFinal + "&zoom=16&size=1290x720&markers=color:red|" + latitudeFinal + "," + longitudeFinal;
    }
}
