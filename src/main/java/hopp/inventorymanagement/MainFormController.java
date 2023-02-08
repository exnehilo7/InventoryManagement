package hopp.inventorymanagement;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static hopp.inventorymanagement.IMSApplication.tempID;

/**
 * Class for the Main form.
 */
public class MainFormController implements Initializable {

    /**
     * Table for parts
     */
    public TableView tblMainPart;
    /**
     * Part ID column
     */
    public TableColumn colPartID;
    /**
     *  Part name column
     */
    public TableColumn colPartName;
    /**
     *  Part inventory column
     */
    public TableColumn colPartInventory;
    /**
     *  Part price column
     */
    public TableColumn colPartPrice;
    /**
     * Table for products
     */
    public TableView tblMainProduct;
    /**
     * Product ID column
     */
    public TableColumn colProductID;
    /**
     * Product name column
     */
    public TableColumn colProductName;
    /**
     * Product inventory column
     */
    public TableColumn colProductInventory;
    /**
     * Product price column
     */
    public TableColumn colProductPrice;
    /**
     * Button to delete a part
     */
    public Button btnMainPartDelete;
    /**
     * Button to modify a part
     */
    public Button btnMainPartModify;
    /**
     * Button to add a part
     */
    public Button btnMainPartAdd;
    /**
     * Search field for part table
     */
    public TextField txtMainPartSearch;
    /**
     * Button to delete a product
     */
    public Button btnMainProductDelete;
    /**
     * Button to modify a product
     */
    public Button btnMainProductModify;
    /**
     * Button to add a product
     */
    public Button btnMainProductAdd;
    /**
     * Search field for product table
     */
    public TextField txtMainProductSearch;
    /**
     * List for all parts
     */
    private ObservableList<Part> parts = Inventory.getAllParts();
    /**
     * List for all products
     */
    private ObservableList<Product> products = Inventory.getAllProducts();

    /**
     * When initialized, set the table's cell values and populate the tables with data.
     * @param url Default url parameter for initialize method
     * @param resourceBundle Default resourceBundle parameter for initialize method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Prep columns
        GenericFunctions.setCellValues(colPartID, colPartName, colPartInventory, colPartPrice);
        GenericFunctions.setCellValues(colProductID, colProductName, colProductInventory, colProductPrice);

        // Populate Part and Product table data
        setTableValues();

    }

    /**
     * Set the table values.
     */
    private void setTableValues(){
        tblMainPart.setItems(parts);
        tblMainProduct.setItems(products);
    }

    /**
     * Exit the program. Prompt user for confirmation.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnMainExitProgram(ActionEvent actionEvent) {

        // Confirm application exit
        if (GenericFunctions.displayConfirm("Confirm Exit",
                "Are you sure you want exit the Inventory Management System?").get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    //Part section
    /**
     * Search the Part table for the matching ID or partial name match. If the search field is blank, display all
     * available parts.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void txtMainPartSearch(ActionEvent actionEvent) {

        ObservableList<Part> partList = Inventory.getAllParts();

        // If field has text, search
        if (txtMainPartSearch.getText().strip().length() > 0) {
            GenericFunctions.partTableSearch(txtMainPartSearch,tblMainPart, partList);
        }
        // If no text, display all
        else {
            tblMainPart.setItems(partList);
        }

    }

    /**
     * Delete a selected part form the table. Prompt the user before removal. Alert the user if nothing is selected.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnMainPartDeleteItem(ActionEvent actionEvent) {

        Part part = (Part) tblMainPart.getSelectionModel().getSelectedItem();

        // If no item is selected, do nothing
        if (part == null) {
            tempID = 0;
            GenericFunctions.displayInformation("Nothing Selected", "A part must first be selected before it"
            + " can be deleted.");
        }
        else {
            // Verify Deletion
            if (GenericFunctions.displayConfirm("Confirm Deletion",
                    "Are you sure you want to delete this record?").get() == ButtonType.OK) {
                Inventory.deletePart(part);
                setTableValues();
            }
        }
    }

    /**
     * Open the Modify Part form and populate its fields with the selected row's data. Alert the user if nothing is
     * selected.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnMainPartOpenModify(ActionEvent actionEvent) throws IOException {
        // Get selected item and set its ID to public static variable for form field population
        Part part = (Part) tblMainPart.getSelectionModel().getSelectedItem();

        // Don't open form if no item is selected
        if (part == null) {
            tempID = 0;
            GenericFunctions.displayInformation("Nothing Selected", "Please select a part.");
            return;
        }
        else {
            tempID = part.getId();
        }

        // Switch forms
        Parent root = FXMLLoader.load(getClass().getResource("PartModifyForm.fxml"));
        GenericFunctions.changeSceneToPart(root, actionEvent, "Modify");
    }

    /**
     * Open the Add Part form.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnMainPartOpenAdd(ActionEvent actionEvent) throws IOException {
        // Switch forms
        Parent root = FXMLLoader.load(getClass().getResource("PartAddForm.fxml"));
        GenericFunctions.changeSceneToPart(root, actionEvent, "Add");
    }

    // Product section

    /**
     * Search the Product table for the matching ID or partial name match. If the search field is blank, display all
     * available parts.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void txtMainProductSearch(ActionEvent actionEvent) {

        ObservableList<Product> productList = Inventory.getAllProducts();

        // If field has text, search
        if (txtMainProductSearch.getText().strip().length() > 0) {
            GenericFunctions.productTableSearch(txtMainProductSearch, tblMainProduct, productList);
        }
        // If no text, display all
        else {
            tblMainProduct.setItems(productList);
        }

    }

    /**
     * Delete a selected product form the table. Prompt the user before removal. Alert the user if nothing is selected.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnMainProductDeleteItem(ActionEvent actionEvent) {
        Product product = (Product) tblMainProduct.getSelectionModel().getSelectedItem();

        // If no item is selected, do nothing
        if (product == null) {
            tempID = 0;
            GenericFunctions.displayInformation("Nothing Selected", "A product must first be selected before it"
                    + " can be deleted.");
        }
        else {
            // If there are no associated parts, continue
            tempID = product.getId();
            Product productItem = Inventory.lookupProduct(tempID);
            if (productItem.getAllAssociatedParts().size() > 0){
                GenericFunctions.displayWarning("Associated Parts Exist", "This product cannot be deleted"
                + " until its associated parts are removed.");
            }
            else {
                // Verify Deletion
                if (GenericFunctions.displayConfirm("Confirm Deletion",
                        "Are you sure you want to delete this record?").get() == ButtonType.OK) {
                    // delete record
                    Inventory.deleteProduct(product);
                    setTableValues();
                }
            }
        }
    }

    /**
     * Open the Modify Product form and populate its fields with the selected row's data. Alert the user if nothing is
     * selected.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnMainProductOpenModify(ActionEvent actionEvent) throws IOException {
        Product product = (Product) tblMainProduct.getSelectionModel().getSelectedItem();

        // Don't open form if no item is selected
        if (product == null) {
            tempID = 0;
            GenericFunctions.displayInformation("Nothing Selected", "Please select a product.");
            return;
        }
        else {
            tempID = product.getId();
        }

        // Switch forms
        Parent root = FXMLLoader.load(getClass().getResource("ProductModifyForm.fxml"));
        GenericFunctions.changeSceneToProduct(root, actionEvent, "Modify");
    }

    /**
     * Open the Add Product form.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnMainProductOpenAdd(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("ProductAddForm.fxml"));
        GenericFunctions.changeSceneToProduct(root, actionEvent, "Add");
    }
}
