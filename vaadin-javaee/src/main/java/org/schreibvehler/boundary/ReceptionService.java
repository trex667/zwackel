
package org.schreibvehler.boundary;

import javax.ejb.Stateless;

@Stateless
public class ReceptionService {

    public String welcome() {
        return "Hello, Developer! No XML, No Configuration, and it works!";
    }
}
