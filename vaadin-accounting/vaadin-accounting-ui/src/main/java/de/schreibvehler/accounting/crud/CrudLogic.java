package de.schreibvehler.accounting.crud;

import de.schreibvehler.accounting.MyUI;
import de.schreibvehler.accounting.samples.backend.DataService;
import de.schreibvehler.accounting.samples.backend.data.Product;

import com.vaadin.server.Page;

/**
 * This class provides an interface for the logical operations between the CRUD
 * view, its parts like the product editor form and the data source, including
 * fetching and saving products.
 *
 * Having this separate from the view makes it easier to test various parts of
 * the system separately, and to e.g. provide alternative views for the same
 * data.
 */
public class CrudLogic {

    private EvidenceView view;

    public CrudLogic(EvidenceView simpleCrudView) {
        view = simpleCrudView;
    }

    public void init() {
        editProduct(null);
        // Hide and disable if not admin
        if (!MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.setNewEvidenceEnabled(false);
        }

        view.showEvidence(DataService.get().getAllProducts());
    }

    public void cancelProduct() {
        setFragmentParameter("");
        view.clearSelection();
        view.editEvidence(null);
    }

    /**
     * Update the fragment without causing navigator to change view
     */
    private void setFragmentParameter(String productId) {
        String fragmentParameter;
        if (productId == null || productId.isEmpty()) {
            fragmentParameter = "";
        } else {
            fragmentParameter = productId;
        }

        Page page = MyUI.get().getPage();
        page.setUriFragment("!" + EvidenceView.VIEW_NAME + "/"
                + fragmentParameter, false);
    }

    public void enter(String productId) {
        if (productId != null && !productId.isEmpty()) {
            if (productId.equals("new")) {
                newEvidence();
            } else {
                // Ensure this is selected even if coming directly here from
                // login
                try {
                    int pid = Integer.parseInt(productId);
                    Product product = findProduct(pid);
                    view.selectRow(product);
                } catch (NumberFormatException e) {
                }
            }
        }
    }

    private Product findProduct(int productId) {
        return DataService.get().getProductById(productId);
    }

    public void saveProduct(Product product) {
        view.showSaveNotification(product.getProductName() + " ("
                + product.getId() + ") updated");
        view.clearSelection();
        view.editEvidence(null);
        view.refreshProduct(product);
        setFragmentParameter("");
    }

    public void deleteProduct(Product product) {
        DataService.get().deleteProduct(product.getId());
        view.showSaveNotification(product.getProductName() + " ("
                + product.getId() + ") removed");

        view.clearSelection();
        view.editEvidence(null);
        view.removeProduct(product);
        setFragmentParameter("");
    }

    public void editProduct(Product product) {
        if (product == null) {
            setFragmentParameter("");
        } else {
            setFragmentParameter(product.getId() + "");
        }
        view.editEvidence(product);
    }

    public void newEvidence() {
        view.clearSelection();
        setFragmentParameter("new");
        view.editEvidence(new Product());
    }

    public void rowSelected(Product product) {
        if (MyUI.get().getAccessControl().isUserInRole("admin")) {
            view.editEvidence(product);
        }
    }
}
