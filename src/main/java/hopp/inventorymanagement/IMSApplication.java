/** <p><b>
 * FUTURE ENHANCEMENT
 * </b></p>
 * <p><b>
 * The Add and Modify Part forms can be combined into a single form, with code to change the form's display-name label.
 * The same can be done with the Add and Modify Product forms. In addition, the user would be able to remove multiple
 * items from a table's list at once by Shift+Left-clicking several rows or Ctrl+Left-clicking various individual rows.
 * A third enhancement on the Product form will prevent the user from adding duplicate associated parts to a product.
 * A fourth enhancement would be the order of the Min and Max fields on the forms. Min should be on the left. A fifth
 * enhancement can update a product's associated part's inventory count if that part's inventory count
 * has been changed. A sixth enhancement would allow the user to sort the table rows by column. A seventh enhancement
 * can ask the user if they want to discard any changes after canceling a part or product modification. An eighth
 * enhancement will keep the forms in the center of the screen as the user switches from form to form.
 * <p><b>
 * <p><b>
 * Description:
 * This program is an Inventory Management System. A user can add, delete, modify, and search Parts and Products. Parts
 * can be assigned to Products. A Part can either be Outsourced or InHouse. Its data will not persist after closing
 * the program.
 * </b></p>
 * @date 25-OCT-2022
 * @author Daniel Hopp
 * */

package hopp.inventorymanagement;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

/**
 * Main class to launch the application.
 */
public class IMSApplication extends Application {

    /**
     * HashMap for textfiled name and user-friendly name translations
     */
    public static final HashMap<String, String> FORM_FIELD_TO_NAME = new HashMap<>();

    /**
     * Unique ID for Parts and Products
     */
    public static int uniqueID = 1;

    /**
     * Temp ID for Modify Part and Product ID forms
     */
    public static int tempID = 0;

    /**
     * The Javadoc files are located in the inventoryManagement folder. Look for a folder named JavaDoc.
     *
     * Main method to start the application. Initializes Part and Product observable lists. Creates HashMap for alert
     * messaging.
     * @param args Java's default Main parameter
     */
    public static void main(String[] args) {

        // Initialize Parts and Products observable lists
        Inventory.initializeLists();

        // Make list of formID names and their user-friendly equivalents
        addValuesToHashMap();

        // Start app
        launch();
    }

    /**
     * Set and display the main form.
     * @param stage JavaFX's stage
     * @throws IOException Default IOException error throw
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IMSApplication.class.getResource("MainForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 500);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Map user-friendly terms to form object names for use in JavaFX alerts.
     */
    private static void addValuesToHashMap(){
        FORM_FIELD_TO_NAME.put("txtAddPartName", "Name");
        FORM_FIELD_TO_NAME.put("txtAddPartInventoryAmt", "Inventory Count");
        FORM_FIELD_TO_NAME.put("txtAddPartPrice", "Price");
        FORM_FIELD_TO_NAME.put("txtAddPartMaxCount", "Max Count");
        FORM_FIELD_TO_NAME.put("txtAddPartIDName", "Machine ID/Company Name");
        FORM_FIELD_TO_NAME.put("txtAddPartMinCount", "Min Count");
        FORM_FIELD_TO_NAME.put("txtModifyPartName", "Name");
        FORM_FIELD_TO_NAME.put("txtModifyPartInventoryAmt", "Inventory Count");
        FORM_FIELD_TO_NAME.put("txtModifyPartPrice", "Price");
        FORM_FIELD_TO_NAME.put("txtModifyPartMaxCount", "Max Count");
        FORM_FIELD_TO_NAME.put("txtModifyPartIDName", "Machine ID/Company Name");
        FORM_FIELD_TO_NAME.put("txtModifyPartMinCount", "Min Count");
        FORM_FIELD_TO_NAME.put("txtAddProductName", "Name");
        FORM_FIELD_TO_NAME.put("txtAddProductInventory", "Inventory Count");
        FORM_FIELD_TO_NAME.put("txtAddProductPrice", "Price");
        FORM_FIELD_TO_NAME.put("txtAddProductMaxCount", "Max Count");
        FORM_FIELD_TO_NAME.put("txtAddProductMinCount", "Min Count");
        FORM_FIELD_TO_NAME.put("txtModifyProductName", "Name");
        FORM_FIELD_TO_NAME.put("txtModifyProductInventory", "Inventory Count");
        FORM_FIELD_TO_NAME.put("txtModifyProductPrice", "Price");
        FORM_FIELD_TO_NAME.put("txtModifyProductMaxCount", "Max Count");
        FORM_FIELD_TO_NAME.put("txtModifyProductMinCount", "Min Count");

    }

}