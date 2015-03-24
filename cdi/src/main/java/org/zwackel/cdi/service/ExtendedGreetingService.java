package org.zwackel.cdi.service;

import javax.enterprise.inject.Typed;
import javax.inject.Inject;

@Typed(ExtendedGreetingService.class)
public class ExtendedGreetingService implements GreetingService {

    @Inject
    private BarInfo barInfo;
    
    private static int count;
    
    public String getGreetings() {
        return String.format("Ein wundervolles extended Willkommen! Sie sind der %d. Besucher in der %s", ++count, barInfo.getName());
    }

}
