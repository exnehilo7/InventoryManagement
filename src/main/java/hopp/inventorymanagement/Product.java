package hopp.inventorymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for the Product object.
 */
public class Product {

    /**
     * List of associated Parts
     */
    private ObservableList<Part> associatedParts;
    /**
     * Product ID
     */
    private int id;
    /**
     * Product name
     */
    private String name;
    /**
     * Product price
     */
    private double price;
    /**
     * Product inventory
     */
    private int stock;
    /**
     * Product minimum inventory
     */
    private int min;
    /**
     * Product maximum inventory
     */
    private int max;


    /**
     * Constructor.
     * @param id Product ID
     * @param name Product name
     * @param price Product price
     * @param stock Product inventory
     * @param min Product minimum inventory
     * @param max Product maximum inventory
     * @param list List of associated Parts
     */
    public Product(int id, String name, double price, int stock, int min, int max, ObservableList<Part> list) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = list;
    }

    /**
     * Default constructor.
     */
    public Product(){
        this(0, "NA", 0.0, 0, 0, 0, FXCollections.observableArrayList());
    }

    // Setter constructors

    /**
     * Set ID.
     * @param id Product ID
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set name.
     * @param name Product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set price.
     * @param price Product price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Set stock (inventory).
     * @param stock Product inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Set min (minimum inventory).
     * @param min Product minimum inventory
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Set max (maximum inventory).
     * @param max Product maximum inventory
     */
    public void setMax(int max) {
        this.max = max;
    }

    // Getter constructors

    /**
     * Get ID.
     * @return Product ID
     */
    public int getId() {
        return id;
    }

    /**
     * Get name.
     * @return  Product name
     */
    public String getName() {
        return name;
    }

    /**
     * Get price.
     * @return Product price
     */
    public double getPrice() {
        return price;
    }

    /**
     * Get stock (inventory).
     * @return Product inventory
     */
    public int getStock() {
        return stock;
    }

    /**
     * Get min (minimum inventory).
     * @return Product minimum inventory
     */
    public int getMin() {
        return min;
    }

    /**
     * Get max (maximum inventory).
     * @return Product maximum inventory
     */
    public int getMax() {
        return max;
    }

    /**
     * Add a part to associatedParts.
     * @param part A part
     */
    public void addAssociatedPart(Part part){

        this.associatedParts.add(part);

    }

    /**
     * Add a list of parts to associatedParts.
     * @param list A list of Parts
     */
    public void addAssociatedParts(ObservableList<Part> list){
        // For each part in the list, add to associatedParts
        for (Part item : list) {
            associatedParts.add(item);
        }
    }

    /**
     * Delete all parts from associatedParts.
     */
    public void deleteAssociatedParts(){
        associatedParts.removeAll();
    }

    /**
     * Delete a part from associatedParts.
     * @param selectedAssociatedPart An associated part
     * @return return true if successful. False if not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Get a list of all associatedParts.
     * @return a list of all associated Parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
