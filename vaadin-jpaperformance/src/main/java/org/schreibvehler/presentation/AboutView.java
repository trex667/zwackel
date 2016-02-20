
package org.schreibvehler.presentation;

import javax.annotation.PostConstruct;

import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.*;

@CDIView("")
public class AboutView extends MVerticalLayout implements View {

    private static final long serialVersionUID = 8683894390819076069L;

    @PostConstruct
    void init() {
        addComponent(new RichText().withMarkDownResource("/about.md"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
