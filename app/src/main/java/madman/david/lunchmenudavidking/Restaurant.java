package madman.david.lunchmenudavidking;

/**
 * A record according to the restaurant's type
 */
public class Restaurant {
    private String _name;
    private String _address;
    private String _note;
    private String _type;
    private String _id;

    public static final String CARRY_OUT = "Carry Out";
    public static final String DINE_IN = "Dine In";
    public static final String DELIVERY = "Delivery";

    public Restaurant(String name, String address, String type) {
        setName(name);
        setAddress(address);
        setType(type);
    }
    public Restaurant(String name, String address, String type, String note) {
        setName(name);
        setAddress(address);
        setType(type);
        setNote(note);
    }
    public Restaurant(String name, String address, String type, String note, String ID) {
        setName(name);
        setAddress(address);
        setType(type);
        setNote(note);
        setID(ID);
    }
    public void setID(String value) {_id = value;}
    public String getID() {return _id;}

    public void setNote(String value) {_note = value;}
    public String getNote() { return _note;}

    public void setName(String value) {
        _name = value;
    }
    public String getName() {
        return _name;
    }
    public void setAddress(String value) {
        _address= value;
    }
    public String getAddress() {
        return _address;
    }
    public void setType(String value) {
        _type = value;
    }
    public String getType() {
        return _type;
    }

    // check the type
    public boolean isCarryOut() {
        return getType().equals(CARRY_OUT);
    }
    public boolean isDineIn() {
        return getType().equals(DINE_IN);
    }
    public boolean isDelivery() {
        return getType().equals(DELIVERY);
    }

    @Override
    public String toString() {
        return  "Name: " + getName() + "\n" +
                "Address: " + getAddress() + "\n" +
                "Type: " + getType();
    }
}
