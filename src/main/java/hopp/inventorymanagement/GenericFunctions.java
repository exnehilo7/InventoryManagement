package hopp.inventorymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Optional;

import static hopp.inventorymanagement.IMSApplication.FORM_FIELD_TO_NAME;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/**
 * Class for program-wide functions.
 */
public class GenericFunctions {

    /**
     * Switch to and show the Part scene.
     * @param root Parent for the scene
     * @param actionEvent actionEvent from calling event
     * @param stageTitle Title for the stage
     */
    public static void changeSceneToPart(Parent root, ActionEvent actionEvent, String stageTitle) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switch to and show the Product scene.
     * @param root Parent for the scene
     * @param actionEvent actionEvent from calling event
     * @param stageTitle Title for the stage
     */
    public static void changeSceneToProduct(Parent root, ActionEvent actionEvent, String stageTitle) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Switch to and show the Main scene.
     * @param root Parent for the scene
     * @param actionEvent actionEvent from calling event
     */
    public static void changeSceneToMain(Parent root, ActionEvent actionEvent) {
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 500);
        stage.setTitle("Main");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Change a label's text.
     * @param label A form's label
     * @param text The text to change the label text to
     */
    public static void changeLabelText(Label label, String text){
        label.setText(text);
    }

    /**
     * Verify that all Part field data is valid:
     * -All fields except ID are not blank.
     * -All fields except ID are the appropriate data type.
     * -Min is less than or equal to Inventory, which is in turn less than or equal to Max.
     * -If In-House is selected, the IDName text field is int.
     * -If Outsourced is selected, the IDName text field is text.
     * @param name The name text field
     * @param stock The inventory text field
     * @param price The price text field
     * @param max The max text field
     * @param min The min text field
     * @param IdName The CompanyName/Machine ID text field
     * @param inHouse The InHouse radio option
     * @return Return true if successful. False if not
     */
    public static boolean arePartFormFieldsValid(TextField name, TextField stock, TextField price, TextField max,
                                                 TextField min, TextField IdName, RadioButton inHouse){

        // Are all fields except ID not blank?
        if (isTextFieldNotBlank(name) && isTextFieldNotBlank(stock) && isTextFieldNotBlank(price) &&
                isTextFieldNotBlank(max) && isTextFieldNotBlank(min) && isTextFieldNotBlank(IdName)){
            // Are all fields except ID the appropriate type?
             if (isTextFieldAnInteger(stock) && isTextFieldAnInteger(max) && isTextFieldAnInteger(min)
             && isTextFieldADouble(price) && isTextFieldAString(name)) {
                 // Is min <= inventory/stock <= max?
                if (isInventoryInRange(stock, min, max)){
                    // Is the IdName valid? If In-House is selected, the IDName text field is int
                    if (inHouse.isSelected()){
                        if (isTextFieldAnInteger(IdName)){
                            return true;
                        }
                    }
                    // Else, string
                    else {
                        if (isTextFieldAString(IdName)) {
                            return true;
                        }
                    }
                }
             }
        }
        return false;
    }

    /**
     * Verify that all Product field data is valid:
     * -All fields except ID are not blank.
     * -All fields except ID are the appropriate data type.
     * -Min is less than or equal to Inventory, which is in turn less than or equal to Max.
     * @param name The name text field
     * @param stock The inventory text field
     * @param price The price text field
     * @param max The max text field
     * @param min The min text field
     * @return Return true if successful. False if not
     */
    public static boolean areProductFormFieldsValid (TextField name, TextField stock, TextField price, TextField max,
                                                     TextField min){
        // Are all fields except ID not blank?
        if (isTextFieldNotBlank(name) && isTextFieldNotBlank(stock) && isTextFieldNotBlank(price) &&
                isTextFieldNotBlank(max) && isTextFieldNotBlank(min)){
            // Are all fields except ID the appropriate type?
            if (isTextFieldAnInteger(stock) && isTextFieldAnInteger(max) && isTextFieldAnInteger(min)
                    && isTextFieldADouble(price) && isTextFieldAString(name)) {
                // Is min <= inventory/stock <= max?
                if (isInventoryInRange(stock, min, max)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verify that min inventory count <= inventory <= max inventory count. Display an alert to the user if invalid.
     * @param invField The inventory text field
     * @param minField The max text field
     * @param maxField The min text field
     * @return Return true if successful. False if not
     */
    public static boolean isInventoryInRange(TextField invField, TextField minField, TextField maxField){
        int inventory = parseInt(invField.getText().strip());
        int min = parseInt(minField.getText().strip());
        int max = parseInt(maxField.getText().strip());

        // Is inventory <= max count?
        if (inventory <= max){
            // Is min count <= max count?
            if (min <= inventory){
                return true;
            }
            else {
                // Alert user
                displayError("Invalid Inventory Range",getFieldName(minField) + " must be less than "
                        + "or equal to the " + getFieldName(invField) + "!");
            }
        }
        else {
            // Alert user
            displayError("Invalid Inventory Range", getFieldName(invField) + " must be less than "
                    + "or equal to the " + getFieldName(maxField) + "!");
        }

        return false;
    }

    /**
     * Check a text field to see if it's blank. Display an alert to the user if it is.
     * @param field A text field
     * @return Return true if successful. False if not
     */
    protected static boolean isTextFieldNotBlank(TextField field){

        // Is it empty?
        if (field.getText().strip().length() < 1){
            // Alert user
            displayWarning("Blank Field", getFieldName(field) + " is blank!");
            return false;
        }

        return true;
    }

    /**<p><b>
     * Check a text field to see if its value is an integer greater than -1. Alert the user if it's not.
     *</b></p>
     * <p><b>
     * RUNTIME ERROR
     *</b></p>
     * <p><b>
     * If a number in the field was less than zero, a single message should have alerted the user and all the other form
     * validations were to cease. However, if more than one numeric field was less than zero, a message would display
     * for each less-than-zero field plus a message for every type of invalid field on the form (text in integer,
     * integer in text, invalid inventory count range, etc.). Adding a "return false;" at the end
     * of the less-than-zero alert message corrected the bug.
     *</b></p>
     *
     * @param field A text field
     * @return Return true if successful. False if not
     */
    protected static boolean isTextFieldAnInteger(TextField field){

        try {
           int temp = parseInt(field.getText().strip());
           if (temp < 0){
               // Alert user
               displayWarning("Number is Less Than Zero", getFieldName(field) + " cannot"
               + " be less than zero!");
               return false;
           }
        }
        catch (NumberFormatException except) {
            // Alert user
            displayError("Invalid Data Type", getFieldName(field) + " must be an integer" +
                    " and no greater than 21,474,836,479! (eg. 7, 42, 18)");
            return false;
        }

        return true;
    }

    /**
     * Check a text field to see if its value is a double greater than -1. Alert the user if it's not.
     * @param field A text field
     * @return Return true if successful. False if not
     */
    protected static boolean isTextFieldADouble(TextField field){

        try {
            double temp = parseDouble(field.getText().strip());
            if (temp < 0){
                // Alert user
                displayWarning("Number is Less Than Zero", getFieldName(field) + " cannot"
                        + " be less than zero!");
                return false;
            }
        }
        catch (NumberFormatException except) {
            // Alert user
            displayError("Invalid Data Type", getFieldName(field) + " must be a decimal" +
                    " and no greater than a hundred thousand centillion! (eg. 1.50, 485.99, 3.00)");
            return false;
        }

        return true;
    }

    /**
     * Check a text field to see if its value contains only letters and spaces. Alert the user if it's not.
     * @param field A text field
     * @return Return true if successful. False if not
     */
    protected static boolean isTextFieldAString(TextField field){

        if (!field.getText().strip().matches("[a-zA-Z\s]+")){
            // Alert user
            displayError("Invalid Data Type", getFieldName(field) + " must contain only" +
                    " letters and spaces! (eg. Tiara, Wonder Woman, lasso)");
            return false;
        }

        return true;
    }

    /**
     * Display a warning message to the user.
     * @param title Alert title
     * @param message Alert message
     */
    public static void displayWarning(String title, String message){
        // Create alert and display. Wait for user acknowledgement.
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Display an error message to the user.
     * @param title Alert title
     * @param message Alert message
     */
    public static void displayError(String title, String message){
        // Create alert and display. Wait for user acknowledgement.
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Display an information message to the user.
     * @param title Alert title
     * @param message Alert message
     */
    public static void displayInformation(String title, String message){
        // Create alert and display. Wait for user acknowledgement.
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Display a confirmation message to the user.
     * @param title Alert title
     * @param message Alert message
     * @return Return user's selection
     */
    public static Optional displayConfirm(String title, String message){
        // Create alert and display. Wait for user acknowledgement.
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        Optional<ButtonType> option = alert.showAndWait();
        return option;
    }

    /**
     * Get the user-friendly form field name from the HashMap.
     * @param field A text field
     * @return Return readable name
     */
    public static String getFieldName(TextField field){
        return FORM_FIELD_TO_NAME.get(field.getId());
    }

    /**
     * Increase an integer variable by 1.
     * @param number An integer
     * @return RReturn the integer
     */
    public static int increaseInt(int number){
       return ++number;
    }

    /**
     * Create a new InHouse object and set its variables with the data from the Part form. Add the part to the Inventory.
     * @param id The ID text field
     * @param name The name text field
     * @param stock The inventory text field
     * @param price The price text field
     * @param max The max text field
     * @param min The min text field
     * @param partIdName The Machine ID/Company Name text field
     */
    public static void addInHousePartObject(TextField id, TextField name, TextField stock, TextField price,
                                            TextField max, TextField min, TextField partIdName) {

        InHouse part = new InHouse();

        part.setId(parseInt(id.getText().strip()));
        part.setMax(parseInt(max.getText().strip()));
        part.setMin(parseInt(min.getText().strip()));
        part.setName(name.getText().strip());
        part.setPrice(parseDouble(price.getText().strip()));
        part.setStock(parseInt(stock.getText().strip()));
        part.setMachineId(parseInt(partIdName.getText().strip()));

        Inventory.addPart(part);

    }

    /**
     * Create a new Outsourced object and set its variables with the data from the Part form. Add the part to the Inventory.
     * @param id The ID text field
     * @param name The name text field
     * @param stock The inventory text field
     * @param price The price text field
     * @param max The max text field
     * @param min The min text field
     * @param partIdName The Machine ID/Company Name text field
     */
    public static void addOutsourcedPartObject(TextField id, TextField name, TextField stock,
                                               TextField price, TextField max, TextField min, TextField partIdName) {

        Outsourced part = new Outsourced();

        part.setId(parseInt(id.getText().strip()));
        part.setMax(parseInt(max.getText().strip()));
        part.setMin(parseInt(min.getText().strip()));
        part.setName(name.getText().strip());
        part.setPrice(parseDouble(price.getText().strip()));
        part.setStock(parseInt(stock.getText().strip()));
        part.setCompanyName(partIdName.getText().strip());

        Inventory.addPart(part);

    }

    /**
     * Create a new Product object and set its variables with the data from the Product form. Add the part to the Inventory.
     * @param id The ID text field
     * @param name The name text field
     * @param stock The inventory text field
     * @param price The price text field
     * @param max The max text field
     * @param min The min text field
     * @param list A list of associated parts
     */
    public static void addProductObject(TextField id, TextField name, TextField stock,
                                        TextField price, TextField max, TextField min, ObservableList<Part> list){

        Product product = new Product();

        product.setId(parseInt(id.getText().strip()));
        product.setMax(parseInt(max.getText().strip()));
        product.setMin(parseInt(min.getText().strip()));
        product.setName(name.getText().strip());
        product.setPrice(parseDouble(price.getText().strip()));
        product.setStock(parseInt(stock.getText().strip()));
        product.addAssociatedParts(list);

        Inventory.addProduct(product);

    }

    /**
     * Sets the CellValueFactory for the table's columns.
     * @param id The ID column
     * @param name The name column
     * @param inv The inventory column
     * @param price The price column
     */
    public static void setCellValues(TableColumn id, TableColumn name, TableColumn inv, TableColumn price){
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        inv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * See if the text in a search field is numeric. If so, return the number. If not, return 0.
     * @param searchFieldText Text from a search field
     * @return Return the field's number value. 0 if none found
     */
    private static int getIntForSearch(String searchFieldText){
        if (searchFieldText.matches("[0-9]+")) {
            try {
                int num = parseInt(searchFieldText);
                return num;

            } catch (NumberFormatException except) {
                // Alert user
                displayError("Invalid Number", "The ID must be an integer" +
                        " and no greater than 21,474,836,479! (eg. 7, 42, 18)");
                return 0;
            }
        }
        return 0;
    }

    /**
     * See if the text in a search field is alpha characters and spaces. If so, return the string's length. If not,
     * return 0.
     * @param searchFieldText Text from a search field
     * @return Return the field value length. 0 if none found
     */
    private static int getStringForSearch(String searchFieldText){
        if (searchFieldText.matches("[a-zA-Z\s]+")){
            return searchFieldText.length();
        }
        return 0;
    }

    /**
     * Search the Part table. Determine if the search text is numeric or string. If neither, alert user no matches were
     * found. If match is found, add it to a list and display the results in the table.
     * @param searchField The table search field
     * @param tbl The table
     * @param partList A list of all parts
     */
    public static void partTableSearch(TextField searchField, TableView tbl, ObservableList<Part> partList){

        String searchFieldText = searchField.getText().strip();
        ObservableList<Part> results = FXCollections.observableArrayList();

        // Determine if characters are all numeric or all text
        // If numeric, search by exact ID
        int num = getIntForSearch(searchFieldText);
        if (num > 0) {
            // Search ObservableList
            for (Part part : partList) {
                if (part.getId() == num){
                    results.add(part);
                }
            }
        }
        // If text, search by partial or full name
        else if (getStringForSearch(searchFieldText) > 0){
            // Search ObservableList
            for (Part part : partList) {
                if (part.getName().contains(searchFieldText)){
                    results.add(part);
                }
            }
        }
        else{
            displayNoMatchFound(searchFieldText);
            return;
        }

        displayPartResults(tbl, results, searchFieldText, partList);

    }

    /**
     * Search the Product table. Determine if the search text is numeric or string. If neither, alert user no matches were
     * found. If match is found, add it to a list and display the results in the table.
     * @param searchField The table search field
     * @param tbl The table
     * @param productList A list of all Products
     */
    public static void productTableSearch(TextField searchField, TableView tbl, ObservableList<Product> productList){
        String searchFieldText = searchField.getText().strip();
        ObservableList<Product> results = FXCollections.observableArrayList();

        // Determine if characters are all numeric or all text
        // If numeric, search by exact ID
        int num = getIntForSearch(searchFieldText);
        if (num > 0) {
            // Search ObservableList
            for (Product product : productList) {
                if (product.getId() == num){
                    results.add(product);
                }
            }
        }
        // If text, search by partial or full name
        else if (getStringForSearch(searchFieldText) > 0){
            // Search ObservableList
            for (Product product : productList) {
                if (product.getName().contains(searchFieldText)){
                    results.add(product);
                }
            }
        }
        else{
            displayNoMatchFound(searchFieldText);
            return;
        }

        displayProductResults(tbl, results, searchFieldText, productList);

    }

    /**
     * Display the Part search results. If one result, highlight the row. If more than one result, filter the rows. If no
     * results, alert the user no match was found.
     * @param tbl The table
     * @param results A list of search results
     * @param searchFieldText The search criteria
     * @param partList A list of all parts
     */
    private static void displayPartResults(TableView tbl, ObservableList<Part> results, String searchFieldText,
                                           ObservableList<Part> partList){
        // If one match found, highlight row
        if (results.size() == 1){
            // Show all
            tbl.setItems(partList);
            // Select one
            Part part = results.get(0);
            tbl.scrollTo(part);
            tbl.getSelectionModel().select(part);
        }
        // If more than one match, show results
        else if (results.size() > 1){
            tbl.setItems(results);
        }
        // If no matches, inform user
        else {
            displayNoMatchFound(searchFieldText);
        }
    }

    /**
     * Display the Product search results. If one result, highlight the row. If more than one result, filter the rows. If no
     * results, alert the user no match was found.
     * @param tbl The table
     * @param results A list of search results
     * @param searchFieldText The search criteria
     * @param productList A list of all products
     */
    private static void displayProductResults(TableView tbl, ObservableList<Product> results, String searchFieldText,
                                              ObservableList<Product> productList){
        // If one match found, highlight row
        if (results.size() == 1){
            // Show all
            tbl.setItems(productList);
            // Select one
            Product product = results.get(0);
            tbl.scrollTo(product);
            tbl.getSelectionModel().select(product);
        }
        // If more than one match, show results
        else if (results.size() > 1){
            tbl.setItems(results);
        }
        // If no matches, inform user
        else {
            displayNoMatchFound(searchFieldText);
        }
    }

    /**
     * Display a message to the user that no match was found. Include the search value.
     * @param searchFieldText The search criteria
     */
    private static void displayNoMatchFound(String searchFieldText){
        GenericFunctions.displayInformation("No Match", "No match found for \"" + searchFieldText + "\".");
    }
}
