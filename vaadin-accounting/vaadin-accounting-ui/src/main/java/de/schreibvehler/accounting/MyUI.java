package de.schreibvehler.accounting;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.*;
import com.vaadin.server.*;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;

import de.schreibvehler.accounting.samples.authentication.*;
import de.schreibvehler.accounting.samples.authentication.LoginScreen.LoginListener;

/**
 * Main UI class of the application that shows either the login screen or the
 * main view of the application depending on whether a user is signed in.
 *
 * The @Viewport annotation configures the viewport meta tags appropriately on
 * mobile devices. Instead of device based scaling (default), using responsive
 * layouts.
 */
@Viewport("user-scalable=no,initial-scale=1.0")
@Theme("mytheme")
@Widgetset("de.schreibvehler.accounting.MyAppWidgetset")
public class MyUI extends UI {

    private static final long serialVersionUID = -7189207316045689087L;
    
    private AccessControl accessControl = new BasicAccessControl();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle("My");
        if (!accessControl.isUserSignedIn()) {
            setContent(new LoginScreen(accessControl, new LoginListener() {
                private static final long serialVersionUID = 1437925200219404678L;

                @Override
                public void loginSuccessful() {
                    showMainView();
                }
            }));
        } else {
            showMainView();
        }
    }

    protected void showMainView() {
        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(new MainScreen(MyUI.this));
        getNavigator().navigateTo(getNavigator().getState());
    }

    public static MyUI get() {
        return (MyUI) UI.getCurrent();
    }

    public AccessControl getAccessControl() {
        return accessControl;
    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
        private static final long serialVersionUID = 4744450260730974755L;
    }
}
