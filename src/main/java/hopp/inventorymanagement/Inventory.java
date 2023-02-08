package hopp.inventorymanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Cass for the Inventory object. Holds Parts and Products.
 */
public class Inventory {

    /**
     * List to hold all the parts
     */
    private static ObservableList<Part> allParts;
    /**
     * List to hold all the products
     */
    private static ObservableList<Product> allProducts;

    /**
     * Initialize the class' ObservableLists.
     */
    public static void initializeLists(){
        allParts = FXCollections.observableArrayList();
        allProducts = FXCollections.observableArrayList();
    }

    /**
     * Add a part.
     * @param newPart Part to add to allParts
     */
    public static void addPart(Part newPart){
        allParts.add(newPart);
    }

    /**
     * Add a product.
     * @param newProduct Product to add to allProducts
     */
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }

    /**
     * Lookup a part by its ID.
     * @param partId Part ID
     * @return Part
     */
    public static Part lookupPart(int partId){
        // Run through list and find a matching ID
        for (Part part : allParts) {
            if (part.getId() == partId){
                return part;
            }
        }
        return null;
    }

    /**
     * Lookup a product by its ID.
     * @param productId Product ID
     * @return Product
     */
    public static Product lookupProduct(int productId){
        // Run through list and find a matching ID
        for (Product product : allProducts) {
            if (product.getId() == productId){
                return product;
            }
        }
        return null;
    }

    /**
     * Lookup a part by its name. Return all matches.
     * @param partName Part name
     * @return ObservableList of found parts
     */
    public static ObservableList<Part> lookupPart(String partName){

        ObservableList<Part> temp = FXCollections.observableArrayList();

        for (Part part : allParts) {
            if (part.getName().contains(partName)){
                temp.add(part);
            }
        }
        return temp;

    }

    /**
     * Lookup a product by its name. Return all matches.
     * @param productName Product name
     * @return ObservableList of found products
     */
    public static ObservableList<Product> lookupProduct(String productName){

        ObservableList<Product> temp = FXCollections.observableArrayList();

        for (Product product : allProducts) {
            if (product.getName().contains(productName)){
                temp.add(product);
            }
        }
        return temp;
    }

    /**
     * Update a part via its index position.
     * @param index Part ObservableList index number
     * @param selectedPart A selected part
     */
    public static void updatePart(int index, Part selectedPart){

        Part temp = allParts.get(index);
        temp.setId(selectedPart.getId());
        temp.setName(selectedPart.getName());
        temp.setStock(selectedPart.getStock());
        temp.setPrice(selectedPart.getPrice());
        temp.setMin(selectedPart.getMin());
        temp.setMax(selectedPart.getMax());

    }

    /**
     * Update a product via its index position.
     * @param index Product ObservableList index number
     * @param newProduct A selected product
     */
    public static void updateProduct(int index, Product newProduct){

        Product temp = allProducts.get(index);
        temp.setId(newProduct.getId());
        temp.setName(newProduct.getName());
        temp.setStock(newProduct.getStock());
        temp.setPrice(newProduct.getPrice());
        temp.setMin(newProduct.getMin());
        temp.setMax(newProduct.getMax());

    }

    /**
     * Delete a part.
     * @param selectedPart A selected part
     * @return Return true when finished
     */
    public static boolean deletePart(Part selectedPart){
        allParts.remove(selectedPart);
        return true;
    }

    /**
     * Delete a product.
     * @param selectedProduct A selected product
     * @return Return true when finished
     */
    public static boolean deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
        return true;
    }

    /**
     * Get a list of all parts.
     * @return Return an ObservableList
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }

    /**
     * Get a list of all products.
     * @return Return an ObservableList
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}
