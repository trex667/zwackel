package org.schreibvehler.presentation;


import javax.inject.Inject;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;


@CDIUI("")
@Theme("valo")
@Title("JPA performance")
public class VaadinUI extends UI
{

    private static final long serialVersionUID = 1572152680961504715L;

    @Inject
    private CDIViewProvider viewProvider;


    @Override
    protected void init(VaadinRequest request)
    {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
    }
}