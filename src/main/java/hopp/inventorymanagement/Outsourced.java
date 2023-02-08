package hopp.inventorymanagement;

/**
 * Class for the Outsourced object. Extends the Part object.
 */
public class Outsourced extends Part {

    /**
     * Part company name
     */
    private String companyName;

    /**
     * Constructor.
     * @param id Part ID
     * @param name Part name
     * @param price Part price
     * @param stock Part inventory
     * @param min Part minimum inventory
     * @param max Part maximum inventory
     * @param companyName Part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {

        super(id, name, price, stock, min, max);
        this.companyName = companyName;

    }

    /**
     * Default constructor.
     */
    public Outsourced(){
        this(0, "NA", 0.0, 0, 0, 0, "NA");
    }

    /**
     * Set companyName.
     * @param companyName Part company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Get companyName.
     * @return part company name
     */
    public String getCompanyName() {
        return companyName;
    }


}
