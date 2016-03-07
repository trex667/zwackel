
package org.schreibvehler.presentation;

import javax.annotation.PostConstruct;

import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.*;
import com.vaadin.server.Page;

@CDIView("")
public class AboutView extends MVerticalLayout implements View {

    private static final long serialVersionUID = 8683894390819076069L;

    @PostConstruct
    void init() {
        addComponent(new RichText().withMarkDownResource("/about.md"));
        addComponent(new RichText().withMarkDownResource("/V1.md"));
        addComponent(new RichText().withMarkDownResource("/V2.md"));
        addComponent(new RichText().withMarkDownResource("/V3.md"));
        addComponent(new RichText().withMarkDownResource("/V4.md"));
        addComponent(new RichText().withMarkDownResource("/V5.md"));
        addComponent(new RichText().withMarkDownResource("/V6.md"));
        addComponent(new RichText().withMarkDownResource("/V7.md"));
        addComponent(new RichText().withMarkDownResource("/ComplexV1.md"));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        Page.getCurrent().setTitle("About");
    }

}
