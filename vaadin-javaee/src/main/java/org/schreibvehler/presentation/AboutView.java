
package org.schreibvehler.presentation;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import javax.annotation.PostConstruct;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

@CDIView("")
@ViewMenuItem(icon = FontAwesome.LIFE_BOUY, order = ViewMenuItem.END)
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
