package de.schreibvehler;

import com.vaadin.cdi.CDIUI;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

/**
 * The Application's "main" class
 */
@CDIUI("")
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        TextField nameField1 = new TextField("city");
        TextField nameField2 = new TextField("country code");

        layout.addComponent(nameField1);
        layout.addComponent(nameField2);

        Button button = new Button("Click Me");
        button.addClickListener((event) -> {
            for (WeatherForcast wf : new OpenWeatherService().getWeatherForcast(nameField1.getValue(),
                    nameField2.getValue())) {
                layout.addComponent(new Label(wf.getClouds()));
            }
        });
        layout.addComponent(button);
    }
}
