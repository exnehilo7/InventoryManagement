/**
 * Module Info. Req's javafx.controls, javafx.fxml. opens hopp.inventorymanagement to javafx.fxml.
 * exports hopp.inventorymanagement
 */
module hopp.inventorymanagement {
    requires javafx.controls;
    requires javafx.fxml;


    opens hopp.inventorymanagement to javafx.fxml;
    exports hopp.inventorymanagement;
}