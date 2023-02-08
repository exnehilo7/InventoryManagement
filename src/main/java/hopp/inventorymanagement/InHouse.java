package hopp.inventorymanagement;

/**
 * Class for InHouse object. Extends Part object.
 */
public class InHouse extends Part {

    /**
     * Part machine ID
     */
    private int machineId;

    /**
     * Constructor.
     * @param id Part id
     * @param name Part name
     * @param price Part price
     * @param stock Part inventory
     * @param min Part minimum allowable inventory
     * @param max Part maximum allowable inventory
     * @param machineId Part machine ID
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        super(id, name, price, stock, min, max);
        this.machineId = machineId;

    }

    /**
     * Default constructor.
     */
    public InHouse(){
        this(0, "NA", 0.0, 0, 0, 0, 0);
    }

    /**
     * Set machineId.
     * @param machineId The Part machine ID
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }

    /**
     * Get machineId.
     * @return Return the part machine ID
     */
    public int getMachineId() {
        return machineId;
    }


}
