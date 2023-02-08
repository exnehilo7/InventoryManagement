package hopp.inventorymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static hopp.inventorymanagement.IMSApplication.tempID;

/**
 * Class for the Modify Product form.
 */
public class ModifyProductFormController implements Initializable {
    /**
     * Product ID text field
     */
    public TextField txtModifyProductID;
    /**
     * Product name text field
     */
    public TextField txtModifyProductName;
    /**
     * Product inventory text field
     */
    public TextField txtModifyProductInventory;
    /**
     * Product price text field
     */
    public TextField txtModifyProductPrice;
    /**
     * Product maximum inventory text field
     */
    public TextField txtModifyProductMaxCount;
    /**
     * Product minimum inventory text field
     */
    public TextField txtModifyProductMinCount;
    /**
     * Table for all parts
     */
    public TableView tblModifyProductAllParts;
    /**
     * All parts ID column
     */
    public TableColumn colModifyPartId;
    /**
     * All parts name column
     */
    public TableColumn colModifyPartName;
    /**
     * All parts inventory column
     */
    public TableColumn colModifyPartInventory;
    /**
     * All parts price column
     */
    public TableColumn colModifyPartPrice;
    /**
     * Table for associated parts
     */
    public TableView tblModifyProductAssociatedParts;
    /**
     * Associated parts ID column
     */
    public TableColumn colModifyAssoPartId;
    /**
     * Associated parts name column
     */
    public TableColumn colModifyAssoPartName;
    /**
     * Associated parts inventory column
     */
    public TableColumn colModifyAssoPartInventory;
    /**
     * Associated parts price column
     */
    public TableColumn colModifyAssoPartPrice;
    /**
     * Search text field for parts
     */
    public TextField txtModifyProductSearch;
    /**
     * List for associated parts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * List for initial associated parts
     */
    private ObservableList<Part> originalAssociatedParts = FXCollections.observableArrayList();
    /**
     * List for all parts
     */
    private ObservableList<Part> parts = Inventory.getAllParts();

    /**
     * When initialized, auto-generate the next unique ID and display it in txtAddProductID. Prep the two tables and
     * populate the Parts table with all available parts and the Associated Parts table with the product's associated
     * parts. Place the Associated Parts in a variable for Associated Part additions/removals.
     * @param url Default url parameter for initialize method
     * @param resourceBundle Default resourceBundle parameter for initialize method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Display values in the fields. Set option button to appropriate selection.
        Product product = Inventory.lookupProduct(tempID);

        txtModifyProductID.setText(Integer.toString(tempID));
        txtModifyProductName.setText(product.getName());
        txtModifyProductInventory.setText(Integer.toString(product.getStock()));
        txtModifyProductPrice.setText(Double.toString(product.getPrice()));
        txtModifyProductMaxCount.setText(Integer.toString(product.getMax()));
        txtModifyProductMinCount.setText(Integer.toString(product.getMin()));

        // Populate the part and associated parts tables
        GenericFunctions.setCellValues(colModifyPartId, colModifyPartName, colModifyPartInventory, colModifyPartPrice);
        GenericFunctions.setCellValues(colModifyAssoPartId, colModifyAssoPartName, colModifyAssoPartInventory,
                colModifyAssoPartPrice);
        tblModifyProductAllParts.setItems(parts);
        // Original AssociatedParts list for when the user cancels the form
        originalAssociatedParts = product.getAllAssociatedParts();

        // Temp AssociatedParts list for additions/removals
        associatedParts = FXCollections.observableArrayList();
        for (Part part : originalAssociatedParts) {
            associatedParts.add(part);
        }

        tblModifyProductAssociatedParts.setItems(associatedParts);

    }

    /**
     * Set the table's values.
     */
    private void setTableValues(){

        tblModifyProductAllParts.setItems(parts);
        tblModifyProductAssociatedParts.setItems(associatedParts);

    }

    /**
     * Move a selected part to the associated parts table.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnModifyProductAddToAssoc(ActionEvent actionEvent) {
        Part part = (Part) tblModifyProductAllParts.getSelectionModel().getSelectedItem();

        // If no item is selected, do nothing
        if (part == null) {
        }
        else {
            associatedParts.add(part);
            setTableValues();
            // Maintain search results
            btnModifyProductSearch(actionEvent);
        }
    }

    /**
     * Move a selected associated part to the parts table. Get user confirmation before the move.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnModifyProductRemoveAssoc(ActionEvent actionEvent) {

        Part part = (Part) tblModifyProductAssociatedParts.getSelectionModel().getSelectedItem();

        // If no item is selected, do nothing
        if (part == null) {
        }
        else {
            // Verify Deletion
            if (GenericFunctions.displayConfirm("Confirm Deletion",
                    "Are you sure you want to remove this associated part?").get() == ButtonType.OK) {
                // Remove from associatedParts
                associatedParts.remove(part);
                setTableValues();
                // Maintain search results
                btnModifyProductSearch(actionEvent);
            }
        }
    }

    /**
     * Close the form and open the Main form.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnModifyProductCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        GenericFunctions.changeSceneToMain(root, actionEvent);
    }

    /**
     * If all field data is valid, delete the current record and write a new record. Switch scenes to the Main form.
     * If the fields are invalid, alert the user and do nothing.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnModifyProductSaveRecord(ActionEvent actionEvent) throws IOException {
        // Verify that all field data is valid
        if (GenericFunctions.areProductFormFieldsValid(txtModifyProductName, txtModifyProductInventory,
                txtModifyProductPrice, txtModifyProductMaxCount, txtModifyProductMinCount)) {

            // Clear out current product
            Product CurrentProduct = Inventory.lookupProduct(tempID);
            Inventory.deleteProduct(CurrentProduct);

            // Write data to the record
            GenericFunctions.addProductObject(txtModifyProductID, txtModifyProductName, txtModifyProductInventory,
                    txtModifyProductPrice, txtModifyProductMaxCount, txtModifyProductMinCount, associatedParts);

            // Go to the main form
            btnModifyProductCancel(actionEvent);
        }
    }

    /**
     * Search the Part table for the matching ID or partial name match. If the search field is blank, display all
     * available parts.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnModifyProductSearch(ActionEvent actionEvent) {

        ObservableList<Part> partList = Inventory.getAllParts();

        // If field has text, search
        if (txtModifyProductSearch.getText().strip().length() > 0) {
            GenericFunctions.partTableSearch(txtModifyProductSearch, tblModifyProductAllParts, partList);
        }
        // If no text, display all
        else {
            tblModifyProductAllParts.setItems(partList);
        }

    }
}
