package pos.model;

import java.math.BigDecimal;

public class ProductVariant {
    private final int id;
    private final int productId;
    private final String name;
    private final String sku;
    private final String barcode;
    private final BigDecimal price;
    private final BigDecimal cost;
    private final int stock;
    private final boolean isActive;

    public ProductVariant(int id, int productId, String name, String sku, String barcode,
                          BigDecimal price, BigDecimal cost, int stock, boolean isActive) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.sku = sku;
        this.barcode = barcode;
        this.price = price;
        this.cost = cost;
        this.stock = stock;
        this.isActive = isActive;
    }

    public int getId() { return id; }
    public int getProductId() { return productId; }
    public String getName() { return name; }
    public String getSku() { return sku; }
    public String getBarcode() { return barcode; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getCost() { return cost; }
    public int getStock() { return stock; }
    public boolean getIsActive() { return isActive; }
}
