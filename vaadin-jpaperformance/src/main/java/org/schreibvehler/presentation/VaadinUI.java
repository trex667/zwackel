package org.schreibvehler.presentation;

import javax.inject.Inject;

import com.vaadin.annotations.*;
import com.vaadin.cdi.*;
import com.vaadin.navigator.*;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@CDIUI("")
@Theme("valo")
@Title("JPA performance")
public class VaadinUI extends UI implements ViewDisplay {

    private static final long serialVersionUID = 1572152680961504715L;

    @Inject
    private CDIViewProvider viewProvider;

    private HorizontalLayout content;

    private Component createMenuComponent() {
        VerticalLayout menu = new VerticalLayout();
        menu.addComponent(new Button("About", e -> {
            getNavigator().navigateTo("");
        }));
        menu.addComponent(new Button("V1", e -> {
            getNavigator().navigateTo("V1");
        }));
        menu.addComponent(new Button("V2", e -> {
            getNavigator().navigateTo("V2");
        }));
        menu.addComponent(new Button("V3", e -> {
            getNavigator().navigateTo("V3");
        }));
        menu.addComponent(new Button("V4", e -> {
            getNavigator().navigateTo("V4");
        }));
        menu.addComponent(new Button("V5", e -> {
            getNavigator().navigateTo("V5");
        }));
        menu.addComponent(new Button("V6", e -> {
            getNavigator().navigateTo("V6");
        }));
        return menu;
    }

    @Override
    protected void init(VaadinRequest request) {
        Navigator navigator = new Navigator(this, (ViewDisplay) this);
        navigator.addProvider(viewProvider);
        content = new HorizontalLayout();
        setContent(content);
    }

    @Override
    public void showView(View view) {
        content.removeAllComponents();
        content.addComponent(createMenuComponent());
        if (view instanceof Component) {
            content.addComponent((Component) view);
        }
    }
}
