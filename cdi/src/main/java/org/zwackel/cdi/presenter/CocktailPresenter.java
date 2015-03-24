package org.zwackel.cdi.presenter;

import javax.inject.*;

import org.zwackel.cdi.service.*;

@Named
public class CocktailPresenter {
    
    // field injection
    @Inject
    private BarInfo barInfo;

    private GreetingService greetingService;

    private ExtendedGreetingService extendedGreetingService;

    // constructor injection
    @Inject
    public CocktailPresenter(GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    // method injection
    @Inject
    private void setExtendedGreetingService(ExtendedGreetingService extendedGreetingService) {
        this.extendedGreetingService = extendedGreetingService;
    }

    public BarInfo getBarInfo() {
        return barInfo;
    }

    public GreetingService getGreetingService() {
        return greetingService;
    }

    public ExtendedGreetingService getExtendedGreetingService() {
        return extendedGreetingService;
    }
}
