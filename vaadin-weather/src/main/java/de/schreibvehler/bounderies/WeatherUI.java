package de.schreibvehler.bounderies;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.*;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.server.VaadinCDIServlet;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.*;

@CDIUI("weather")
public class WeatherUI extends UI {

    private static final long serialVersionUID = -770620417286164257L;

    @Override
    protected void init(VaadinRequest request) {
        VerticalLayout content = new VerticalLayout();
        setContent(content);

        // Display the greeting
        content.addComponent(new Label("Hello World!"));

        // Have a clickable button        
        content.addComponent(new Button("Push Me!", e -> {Notification.show("Pushed!");}));
    }

    @WebServlet(urlPatterns = "/*", name = "WeatherUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = WeatherUI.class, productionMode = false)
    public static class WeatherUIServlet extends VaadinCDIServlet {
        private static final long serialVersionUID = 4744450260730974755L;
    }
}
