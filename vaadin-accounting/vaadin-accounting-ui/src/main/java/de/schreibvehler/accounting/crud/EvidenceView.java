package de.schreibvehler.accounting.crud;

import java.util.Collection;

import com.vaadin.event.*;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.*;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid.SelectionModel;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;

import de.schreibvehler.accounting.samples.ResetButtonForTextField;
import de.schreibvehler.accounting.samples.backend.DataService;
import de.schreibvehler.accounting.samples.backend.data.Product;

/**
 * A view for performing create-read-update-delete operations on products.
 *
 * See also {@link CrudLogic} for fetching the data, the actual CRUD
 * operations and controlling the view based on events from outside.
 */
public class EvidenceView extends CssLayout implements View {

    public static final String VIEW_NAME = "Evidence";
    private EvidenceGrid grid;
    private EvidenceForm form;

    private CrudLogic viewLogic = new CrudLogic(this);
    private Button newEvidence;

    public EvidenceView() {
        setSizeFull();
        addStyleName("crud-view");
        HorizontalLayout topLayout = createTopBar();

        grid = new EvidenceGrid();
        grid.addSelectionListener(new SelectionListener() {

            @Override
            public void select(SelectionEvent event) {
                viewLogic.rowSelected(grid.getSelectedRow());
            }
        });

        form = new EvidenceForm(viewLogic);
        form.setCategories(DataService.get().getAllCategories());

        VerticalLayout barAndGridLayout = new VerticalLayout();
        barAndGridLayout.addComponent(topLayout);
        barAndGridLayout.addComponent(grid);
        barAndGridLayout.setMargin(true);
        barAndGridLayout.setSpacing(true);
        barAndGridLayout.setSizeFull();
        barAndGridLayout.setExpandRatio(grid, 1);
        barAndGridLayout.setStyleName("crud-main-layout");

        addComponent(barAndGridLayout);
        addComponent(form);

        viewLogic.init();
    }

    public HorizontalLayout createTopBar() {
        TextField filter = new TextField();
        filter.setStyleName("filter-textfield");
        filter.setInputPrompt("Filter");
        ResetButtonForTextField.extend(filter);
        filter.setImmediate(true);
        filter.addTextChangeListener(new FieldEvents.TextChangeListener() {
            @Override
            public void textChange(FieldEvents.TextChangeEvent event) {
                grid.setFilter(event.getText());
            }
        });

        newEvidence = new Button("New Evidence");
        newEvidence.addStyleName(ValoTheme.BUTTON_PRIMARY);
        newEvidence.setIcon(FontAwesome.PLUS_CIRCLE);
        newEvidence.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(ClickEvent event) {
                viewLogic.newEvidence();
            }
        });

        HorizontalLayout topLayout = new HorizontalLayout();
        topLayout.setSpacing(true);
        topLayout.setWidth("100%");
        topLayout.addComponent(filter);
        topLayout.addComponent(newEvidence);
        topLayout.setComponentAlignment(filter, Alignment.MIDDLE_LEFT);
        topLayout.setExpandRatio(filter, 1);
        topLayout.setStyleName("top-bar");
        return topLayout;
    }

    @Override
    public void enter(ViewChangeEvent event) {
        viewLogic.enter(event.getParameters());
    }

    public void showError(String msg) {
        Notification.show(msg, Type.ERROR_MESSAGE);
    }

    public void showSaveNotification(String msg) {
        Notification.show(msg, Type.TRAY_NOTIFICATION);
    }

    public void setNewEvidenceEnabled(boolean enabled) {
        newEvidence.setEnabled(enabled);
    }

    public void clearSelection() {
        grid.getSelectionModel().reset();
    }

    public void selectRow(Product row) {
        ((SelectionModel.Single) grid.getSelectionModel()).select(row);
    }

    public Product getSelectedRow() {
        return grid.getSelectedRow();
    }

    public void editEvidence(Product product) {
        if (product != null) {
            form.addStyleName("visible");
            form.setEnabled(true);
        } else {
            form.removeStyleName("visible");
            form.setEnabled(false);
        }
        form.editEvidence(product);
    }

    public void showEvidence(Collection<Product> products) {
        grid.setEvidence(products);
    }

    public void refreshProduct(Product product) {
        grid.refresh(product);
        grid.scrollTo(product);
    }

    public void removeProduct(Product product) {
        grid.remove(product);
    }

}
