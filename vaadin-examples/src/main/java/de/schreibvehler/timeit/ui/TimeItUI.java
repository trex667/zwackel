package de.schreibvehler.timeit.ui;

import java.util.Collection;

import com.vaadin.data.util.ObjectProperty;
import com.vaadin.server.*;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;

import de.schreibvehler.timeit.biz.*;
import de.schreibvehler.timeit.biz.test.*;

public class TimeItUI extends UI {
    private static final long serialVersionUID = -6609783287804115742L;

    private static final TestSet[] testSets = new TestSet[] { new LongVsInt(), new StringVsStringBuffer(),
            new ShortCircuitVsNoShortCircuit() };

    private VerticalLayout layout = new VerticalLayout();
    private ComboBox combo = new ComboBox("Test");
    private final TextField textField = new TextField("Number of iterations", "1000");
    private CheckBox checkBox = new CheckBox("Keep previous results");
    private Button button = new Button("Time it!");
    private VerticalLayout resultsLayout = new VerticalLayout();

    @Override
    protected void init(VaadinRequest request) {
        initCombo();
        initButton();
        initLayout();
    }

    private void initCombo() {
        for (TestSet testSet : testSets) {
            combo.addItem(testSet);
            combo.setItemCaption(testSet, testSet.getTitle());
        }

        combo.addValueChangeListener((event) -> {
            TestSet testSet = (TestSet) combo.getValue();
            textField.setValue("" + testSet.getDefaultTimes());
            button.setDescription("<big>" + testSet.getDescription() + "</big>");
        });

        combo.setImmediate(true);
    }

    private void initButton() {
        button.addClickListener((event) -> {
            if (isValid()) {
                runSelectedTest();
            }
        });
    }

    private void initLayout() {
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(combo);
        layout.addComponent(textField);
        layout.addComponent(checkBox);
        layout.addComponent(button);
        layout.addComponent(resultsLayout);

        TextField textField = new TextField("Data");
        textField.setImmediate(true);

        Label label = new Label();
        layout.addComponent(textField);
        layout.addComponent(label);

        ObjectProperty<String> objProp = new ObjectProperty<String>("The value");
        textField.setPropertyDataSource(objProp);
        label.setPropertyDataSource(objProp);

        TextArea textArea = new TextArea("Text area:");
        textArea.setImmediate(true);
        textArea.setWordwrap(false);
        textArea.addValueChangeListener((e) -> {
            Notification.show(e.getProperty().getValue().toString());
        });
        layout.addComponent(textArea);

        RichTextArea richTextArea = new RichTextArea("RichText area:");
        richTextArea.setImmediate(true);
        Label richTextLabel = new Label(richTextArea, ContentMode.HTML);
        layout.addComponent(richTextArea);
        layout.addComponent(richTextLabel);
        
        
        
        setContent(layout);
    }

    public boolean isValid() {
        combo.setComponentError(null);
        textField.setComponentError(null);

        boolean isValid = true;

        if (combo.getValue() == null) {
            combo.setComponentError(new UserError("Select a test from the list."));
            isValid = false;
        }

        if (textField.getValue().toString().isEmpty()) {
            textField.setComponentError(new UserError("You must introduce the number of iterations to execute"));
            isValid = false;
        }

        try {
            Long.parseLong(textField.getValue());
        } catch (NumberFormatException e) {
            textField.setComponentError(new UserError("You must introduce a valid number"));
            isValid = false;
        }

        return isValid;
    }

    public void runSelectedTest() {
        Long times = Long.parseLong(textField.getValue());
        Collection<String> results = TestSetExecutor.execute((TestSet) combo.getValue(), times);
        showResults(results);
    }

    private void showResults(Collection<String> results) {
        if (!checkBox.getValue()) {
            resultsLayout.removeAllComponents();

        } else if (resultsLayout.getComponentCount() > 0) {
            resultsLayout.addComponent(new Label("--"));
        }

        for (String result : results) {
            resultsLayout.addComponent(new Label(result));
        }
    }

}
