package de.schreibvehler;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

/**
 * The Application's "main" class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

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
            String message = getFunnyPhrase(nameField1.getValue(), nameField2.getValue());
            Label label = new Label(message);
            label.setContentMode(ContentMode.HTML);
            layout.addComponent(label);
            Notification.show("Button click successfully finished!");
        });
        layout.addComponent(button);
    }

    private String getFunnyPhrase(String name1, String name2) {
        String[] verbs = new String[] { "eats", "melts", "breaks", "washes", "sells" };
        String[] bodyPart = new String[] { "head", "hands", "waist", "eyes", "elboes" };

        return String.format("%s %s %s's %s", name1, verbs[(int) Math.random() * 100 % verbs.length], name2,
                bodyPart[(int) Math.random() * 100 % bodyPart.length]);

    }

}
