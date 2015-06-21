package de.schreibvehler.cdi;

import com.vaadin.cdi.*;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

@CDIUI("")
//@URLMapping("vaadin-cdi")
public class CDIRootUI extends UI {

    private static final long serialVersionUID = -7150577658589755824L;

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        TextField nameField1 = new TextField("name one");
        TextField nameField2 = new TextField("name two");

        layout.addComponent(nameField1);
        layout.addComponent(nameField2);

        Button button = new Button("Click Me");
        button.addClickListener((event) -> {
            Notification.show("Button click successfully finished!");
        });
        layout.addComponent(button);
    }

}
