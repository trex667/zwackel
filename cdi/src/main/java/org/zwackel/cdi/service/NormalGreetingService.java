package org.zwackel.cdi.service;

import javax.inject.Inject;

public class NormalGreetingService implements GreetingService {

    private static int count;

    @Inject
    private BarInfo barInfo;
    
    public String getGreetings() {
        return String.format("Willkommen! Sie sind der %d. Besucher in der %s", ++count, barInfo.getName());
    }

}
