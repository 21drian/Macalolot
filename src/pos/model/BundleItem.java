package pos.model;

public class BundleItem {
    private final int id;
    private final int bundleProductId;
    private final int itemProductId;
    private final String itemName;
    private final int quantity;

    public BundleItem(int id, int bundleProductId, int itemProductId, String itemName, int quantity) {
        this.id = id;
        this.bundleProductId = bundleProductId;
        this.itemProductId = itemProductId;
        this.itemName = itemName;
        this.quantity = quantity;
    }

    public int getId(){ return id; }
    public int getBundleProductId(){ return bundleProductId; }
    public int getItemProductId(){ return itemProductId; }
    public String getItemName(){ return itemName; }
    public int getQuantity(){ return quantity; }
}
