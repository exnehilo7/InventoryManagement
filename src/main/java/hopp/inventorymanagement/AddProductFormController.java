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
import java.util.*;

import static hopp.inventorymanagement.IMSApplication.uniqueID;

/**
 * Class for the Add Product form.
 */
public class AddProductFormController implements Initializable {
    /**
     * Product ID text field
     */
    public TextField txtAddProductID;
    /**
     * Product name text field
     */
    public TextField txtAddProductName;
    /**
     * Product Inventory text field
     */
    public TextField txtAddProductInventory;
    /**
     * Product price text field
     */
    public TextField txtAddProductPrice;
    /**
     * Product max inventory text field
     */
    public TextField txtAddProductMaxCount;
    /**
     * Product min inventory text field
     */
    public TextField txtAddProductMinCount;
    /**
     * Part search text field
     */
    public TextField txtAddProductSearch;
    /**
     * All parts table
     */
    public TableView tblAddProductAllParts;
    /**
     * Associated parts table
     */
    public TableView tblAddProductAssociatedParts;
    /**
     * Part ID table column
     */
    public TableColumn colAddPartId;
    /**
     * Part name table column
     */
    public TableColumn colAddPartName;
    /**
     * Part inventory table column
     */
    public TableColumn colAddPartInventory;
    /**
     * Part price table column
     */
    public TableColumn colAddPartPrice;
    /**
     * Associated part ID table column
     */
    public TableColumn colAddAssoPartId;
    /**
     * Associated part name table column
     */
    public TableColumn colAddAssoPartName;
    /**
     * Associated part inventory table column
     */
    public TableColumn colAddAssoPartInventory;
    /**
     * Associated part price table column
     */
    public TableColumn colAddAssoPartPrice;
    /**
     * ObservableList for associatedParts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /**
     * ObservableList for parts
     */
    private ObservableList<Part> parts = Inventory.getAllParts();

    /**
     * When initialized, auto-generate the next unique ID and display it in txtAddProductID. Prep the two tables and
     * populate the Parts table with all available parts.
     * @param url Default url parameter for initialize method
     * @param resourceBundle Default resourceBundle parameter for initialize method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Auto-generate the next unique ID and display it in txtAddProductID
        txtAddProductID.setText(Integer.toString(uniqueID));

        // Prep table columns
        GenericFunctions.setCellValues(colAddPartId, colAddPartName, colAddPartInventory, colAddPartPrice);
        GenericFunctions.setCellValues(colAddAssoPartId, colAddAssoPartName, colAddAssoPartInventory,
                colAddAssoPartPrice);

        tblAddProductAllParts.setItems(parts);

        associatedParts = FXCollections.observableArrayList();

    }

    /**
     * Move a selected part to the associated parts table.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnAddProductAddToAssoc(ActionEvent actionEvent) {
        Part part = (Part) tblAddProductAllParts.getSelectionModel().getSelectedItem();

        // If no item is selected, do nothing
        if (part == null) {
        }
        else {
            associatedParts.add(part);
            tblAddProductAssociatedParts.setItems(associatedParts);
        }
    }

    /**
     * Move a selected associated part to the parts table. Get user confirmation before the move.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnAddProductRemoveAssoc(ActionEvent actionEvent) {

        Part part = (Part) tblAddProductAssociatedParts.getSelectionModel().getSelectedItem();

        // If no item is selected, do nothing
        if (part == null) {
        }
        else {
            // Verify Deletion
            if (GenericFunctions.displayConfirm("Confirm Deletion",
                    "Are you sure you want to remove this associated part?").get() == ButtonType.OK) {
                // Remove from associatedParts
                associatedParts.remove(part);
                tblAddProductAssociatedParts.setItems(associatedParts);
            }
        }

    }

    /**
     * Close the form and open the Main form.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnAddProductCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        GenericFunctions.changeSceneToMain(root, actionEvent);
    }

    /**
     * If all field data is valid, write it to a new record and switch scenes to the Main form. Increase the unique ID
     * by 1 for the next record. If the fields are invalid, alert the user and do nothing.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnAddProductSaveRecord(ActionEvent actionEvent) throws IOException {

        // Verify that all field data is valid
        if (GenericFunctions.areProductFormFieldsValid(txtAddProductName, txtAddProductInventory, txtAddProductPrice,
                txtAddProductMaxCount, txtAddProductMinCount)) {

            // Write data to a record
            GenericFunctions.addProductObject(txtAddProductID, txtAddProductName, txtAddProductInventory,
                    txtAddProductPrice, txtAddProductMaxCount, txtAddProductMinCount, associatedParts);

            // Up ID by 1
            uniqueID = GenericFunctions.increaseInt(uniqueID);

            // Go to the main form
            btnAddProductCancel(actionEvent);
        }

    }

    /**
     * Search the Part table for the matching ID or partial name match. If the search field is blank, display all
     * available parts.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void btnAddProductSearch(ActionEvent actionEvent) {

        ObservableList<Part> partList = Inventory.getAllParts();

        // If field has text, search
        if (txtAddProductSearch.getText().strip().length() > 0) {
            GenericFunctions.partTableSearch(txtAddProductSearch, tblAddProductAllParts, partList);
        }
        // If no text, display all
        else {
            tblAddProductAllParts.setItems(partList);
        }

    }
}
