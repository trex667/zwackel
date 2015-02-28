package org.zwackel.cdi.presenter;

import javax.inject.*;

import org.zwackel.cdi.service.BarInfo;

@Named
public class CocktailPresenter {

    @Inject
    private BarInfo barInfo;

    public BarInfo getBarInfo() {
        return barInfo;
    }
}
