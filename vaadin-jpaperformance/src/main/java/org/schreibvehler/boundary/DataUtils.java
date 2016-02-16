package org.schreibvehler.boundary;

import org.apache.commons.lang3.RandomStringUtils;

public class DataUtils {

    private static final String[] citites = new String[]{"Trier", "Bonn", "Köln", "Berlin", "München", "Hamburg", "Dresden", "Mainz", "Leipzig", "Frankfurt", "Emmerich"};
    public static String getRandomCity() {
        return citites[Integer.valueOf(RandomStringUtils.randomNumeric(1))];
    }

}
