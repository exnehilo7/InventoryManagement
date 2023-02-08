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

import static java.lang.Integer.parseInt;
import static hopp.inventorymanagement.IMSApplication.tempID;

/**
 * Class for the Modify Part form
 */
public class ModifyPartFormController implements Initializable {
    /**
     * Label for part MMachine ID or company name
     */
    public Label lblModifyPartIDName;
    /**
     * Part ID text field
     */
    public TextField txtModifyPartID;
    /**
     * Part name text field
     */
    public TextField txtModifyPartName;
    /**
     * Part inventory text field
     */
    public TextField txtModifyPartInventoryAmt;
    /**
     * Part price text field
     */
    public TextField txtModifyPartPrice;
    /**
     * Part maximum inventory text field
     */
    public TextField txtModifyPartMaxCount;
    /**
     * Part Machine ID or Company Name text field
     */
    public TextField txtModifyPartIDName;
    /**
     * Part minimum inventory text field
     */
    public TextField txtModifyPartMinCount;
    /**
     * Radio option for InHouse
     */
    public RadioButton optModifyPartInHouse;
    /**
     * Radio option for Outsourced
     */
    public RadioButton optModifyPartOutsourced;
    /**
     * Machine ID value for radio option toggling
     */
    private int initialMachineIdVal = 0;
    /**
     * Company Name value for radio option toggling
     */
    private String initialCoNameVal = "";


    /**
     * When initialized, populate the form fields to the selected table row from the Part table in Main form. Set the
     * radio buttons to the correct selection. Place txtModifyPartIDName's value into a variable for when the radio
     * button is toggled back to its initial state.
     * @param url Default url parameter for initialize method
     * @param resourceBundle Default resourceBundle parameter for initialize method
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Display values in the fields. Set option button to appropriate selection.
        Part part = Inventory.lookupPart(tempID);
        txtModifyPartID.setText(Integer.toString(tempID));
        txtModifyPartName.setText(part.getName());
        txtModifyPartInventoryAmt.setText(Integer.toString(part.getStock()));
        txtModifyPartPrice.setText(Double.toString(part.getPrice()));
        txtModifyPartMaxCount.setText(Integer.toString(part.getMax()));
        txtModifyPartMinCount.setText(Integer.toString(part.getMin()));
        // Put the initial option value into a variable. Toggle the correct radio option. Populate the correct form field
        if (part instanceof InHouse){
            optModifyPartInHouse.setSelected(true);
            txtModifyPartIDName.setText(Integer.toString(((InHouse) part).getMachineId()));
            initialMachineIdVal = parseInt(txtModifyPartIDName.getText().strip());
            initialCoNameVal = "";
        }
        else {
            optModifyPartOutsourced.setSelected(true);
            txtModifyPartIDName.setText(((Outsourced) part).getCompanyName());
            initialCoNameVal = txtModifyPartIDName.getText().strip();
            initialMachineIdVal = 0;
        }

    }

    /**
     * If all field data is valid, delete the current record and write a new record. Switch scenes to the Main form.
     * If the fields are invalid, alert the user and do nothing. Determine if the Part is InHouse or Outsourced.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnModifyPartSaveRecord(ActionEvent actionEvent) throws IOException{

        // Verify that all field data is valid
        if (GenericFunctions.arePartFormFieldsValid(txtModifyPartName, txtModifyPartInventoryAmt, txtModifyPartPrice,
                txtModifyPartMaxCount, txtModifyPartMinCount, txtModifyPartIDName, optModifyPartInHouse)) {

            // Update the record's data. Delete the current object type and create a new updated record
            // Is In-House selected?
            if (optModifyPartInHouse.isSelected()) {
                // Was the record initially Outsourced?
                if (initialMachineIdVal == 0){
                    Outsourced partOutSrcd = (Outsourced)Inventory.lookupPart(tempID);
                    Inventory.deletePart(partOutSrcd);
                }
                else {
                    InHouse partInHouse = (InHouse)Inventory.lookupPart(tempID);
                    Inventory.deletePart(partInHouse);
                }
                // Create a new updated record
                GenericFunctions.addInHousePartObject(txtModifyPartID, txtModifyPartName,
                        txtModifyPartInventoryAmt, txtModifyPartPrice, txtModifyPartMaxCount, txtModifyPartMinCount,
                        txtModifyPartIDName);
            }
            else {
                // Was the record initially In-House?
                if (initialCoNameVal.length() == 0){
                    InHouse partInHouse = (InHouse)Inventory.lookupPart(tempID);
                    Inventory.deletePart(partInHouse);
                }
                else {
                    Outsourced partOutSrcd = (Outsourced)Inventory.lookupPart(tempID);
                    Inventory.deletePart(partOutSrcd);
                }
                // Create a new updated record
                GenericFunctions.addOutsourcedPartObject(txtModifyPartID, txtModifyPartName,
                        txtModifyPartInventoryAmt, txtModifyPartPrice, txtModifyPartMaxCount, txtModifyPartMinCount,
                        txtModifyPartIDName);
            }

            // Go to the main form
            btnModifyPartCancelForm(actionEvent);
        }
    }

    /**
     * Close the form and open the Main form.
     * @param actionEvent Default actionEvent parameter for event listener
     * @throws IOException Default IOException error throw
     */
    public void btnModifyPartCancelForm(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        GenericFunctions.changeSceneToMain(root, actionEvent);
    }

    /**
     * Change the lblAddPartIDName's label to "Machine ID". If toggle is a return to the initial state, display the
     * original value.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void optModifyPartShowInHouse(ActionEvent actionEvent) {
        // Toggle label's text
        GenericFunctions.changeLabelText(lblModifyPartIDName,"Machine ID");

        // Display original value if option was toggled back from Outsourced. Clear text if not
        if (initialMachineIdVal > 0){
            txtModifyPartIDName.setText(Integer.toString(initialMachineIdVal));
        }
        else {
            txtModifyPartIDName.setText("");
        }

    }

    /**
     * Change the lblAddPartIDName's label to "Company Name". If toggle is a return to the initial state, display the
     * original value.
     * @param actionEvent Default actionEvent parameter for event listener
     */
    public void optModifyPartShowOutsourced(ActionEvent actionEvent) {
        // Toggle label's text
        GenericFunctions.changeLabelText(lblModifyPartIDName,"Company Name");

        // Display original value if option was toggled back from InHouse. Clear text if not
        if (initialCoNameVal.length() > 0){
            txtModifyPartIDName.setText(initialCoNameVal);
        }
        else {
            txtModifyPartIDName.setText("");
        }

    }
}
