package pos.controller;

import java.math.BigDecimal;
import java.sql.Timestamp;
import javafx.beans.property.*;

public class SaleRow {
    private final ObjectProperty<Timestamp> createdAt = new SimpleObjectProperty<>();
    private final StringProperty productName = new SimpleStringProperty();
    private final IntegerProperty quantity = new SimpleIntegerProperty();
    private final ObjectProperty<BigDecimal> total = new SimpleObjectProperty<>();

    public SaleRow(Timestamp createdAt, String productName, int quantity, BigDecimal total) {
        this.createdAt.set(createdAt);
        this.productName.set(productName);
        this.quantity.set(quantity);
        this.total.set(total);
    }

    public Timestamp getCreatedAt() { return createdAt.get(); }
    public String getProductName() { return productName.get(); }
    public int getQuantity() { return quantity.get(); }
    public BigDecimal getTotal() { return total.get(); }

    public ObjectProperty<Timestamp> createdAtProperty() { return createdAt; }
    public StringProperty productNameProperty() { return productName; }
    public IntegerProperty quantityProperty() { return quantity; }
    public ObjectProperty<BigDecimal> totalProperty() { return total; }
}
