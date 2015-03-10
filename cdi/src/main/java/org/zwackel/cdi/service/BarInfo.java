package org.zwackel.cdi.service;

import java.io.Serializable;

public class BarInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    private static int count;
    public String getName(){
        return "Endstation";
    }
    
    public String getWelcomeMessage(){
        return String.format("Herzlich willkommen! Sie sind der %d. Besucher in der Endstation", ++count);
    }
}
