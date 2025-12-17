package pos.model;

import javafx.beans.property.*;

public class VariantRow {
    private final StringProperty name = new SimpleStringProperty();
    private final StringProperty sku = new SimpleStringProperty();
    private final StringProperty barcode = new SimpleStringProperty();
    private final DoubleProperty price = new SimpleDoubleProperty();
    private final DoubleProperty cost = new SimpleDoubleProperty();
    private final IntegerProperty stock = new SimpleIntegerProperty();
    private final BooleanProperty active = new SimpleBooleanProperty(true);

    public VariantRow(String name, String sku, String barcode, double price, double cost, int stock, boolean active) {
        this.name.set(name);
        this.sku.set(sku);
        this.barcode.set(barcode);
        this.price.set(price);
        this.cost.set(cost);
        this.stock.set(stock);
        this.active.set(active);
    }

    public String getName() { return name.get(); }
    public String getSku() { return sku.get(); }
    public String getBarcode() { return barcode.get(); }
    public double getPrice() { return price.get(); }
    public double getCost() { return cost.get(); }
    public int getStock() { return stock.get(); }
    public boolean getActive() { return active.get(); }

    public StringProperty nameProperty() { return name; }
    public StringProperty skuProperty() { return sku; }
    public StringProperty barcodeProperty() { return barcode; }
    public DoubleProperty priceProperty() { return price; }
    public DoubleProperty costProperty() { return cost; }
    public IntegerProperty stockProperty() { return stock; }
    public BooleanProperty activeProperty() { return active; }
}
