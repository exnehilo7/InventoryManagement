package hopp.inventorymanagement;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static hopp.inventorymanagement.IMSApplication.uniqueID;

/**
 * Class for the Add Part form
 */
public class AddPartFormController implements Initializable {

    /**
     * Part Machine ID or Company name label
     */
    public Label lblAddPartIDName;
    /**
     * Part ID text field
     */
    public TextField txtAddPartID;
    /**
     * Part name text field
     */
    public TextField txtAddPartName;
    /**
     * Part inventory text field
     */
    public TextField txtAddPartInventoryAmt;
    /**
     * Part price text field
     */
    public TextField txtAddPartPrice;
    /**
     * Part max inventory count text field
     */
    public TextField txtAddPartMaxCount;
    /**
     * Part Machine ID or Company text field
     */
    public TextField txtAddPartIDName;
    /**
     * Part min inventory count text field
     */
    public TextField txtAddPartMinCount;
    /**
     * Radio button for InHouse or Outsourced toggle
     */
    public RadioButton optAddPartInHouse;

    /**
     * When initialized, auto-generate the next unique ID and display it in txtAddPartID.
     * @param url Default url parameter for initialize method
     * @param resourceBundle Default resourceBundle parameter for initialize method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Auto-generate the next unique ID and display it in txtAddPartID
        txtAddPartID.setText(Integer.toString(uniqueID));
    }

    /**
     * If all field data is valid, write it to a new record and switch scenes to the Main form. Increase the unique ID
     * by 1 for the next record. If the fields are invalid, alert the user and do nothing. Determine if the Part is
     * InHouse or Outsourced.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnAddPartSaveRecord(ActionEvent actionEvent) throws IOException {

        // Verify that all field data is valid
        if (GenericFunctions.arePartFormFieldsValid(txtAddPartName, txtAddPartInventoryAmt, txtAddPartPrice,
                txtAddPartMaxCount, txtAddPartMinCount, txtAddPartIDName, optAddPartInHouse)) {

            // Write data to a record
            // Is In-House selected?
            if (optAddPartInHouse.isSelected()) {
                GenericFunctions.addInHousePartObject(txtAddPartID, txtAddPartName, txtAddPartInventoryAmt,
                        txtAddPartPrice, txtAddPartMaxCount, txtAddPartMinCount, txtAddPartIDName);
            }
            else {
                GenericFunctions.addOutsourcedPartObject(txtAddPartID, txtAddPartName, txtAddPartInventoryAmt,
                        txtAddPartPrice, txtAddPartMaxCount, txtAddPartMinCount, txtAddPartIDName);
            }

            // Up ID by 1
            uniqueID = GenericFunctions.increaseInt(uniqueID);

            // Go to the main form
            btnAddPartCancelForm(actionEvent);
        }
    }

    /**
     * Close the form and open the Main form.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnAddPartCancelForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        GenericFunctions.changeSceneToMain(root, actionEvent);
    }

    /**
     * Change the lblAddPartIDName's label to "Machine ID".
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void optAddPartShowInHouse(ActionEvent actionEvent) {
        // Toggle label's text
        GenericFunctions.changeLabelText(lblAddPartIDName,"Machine ID");
    }

    /**
     * Change the lblAddPartIDName's label to "Company Name".
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void optAddPartShowOutsourced(ActionEvent actionEvent) {
        // Toggle label's text
        GenericFunctions.changeLabelText(lblAddPartIDName,"Company Name");
    }
}
