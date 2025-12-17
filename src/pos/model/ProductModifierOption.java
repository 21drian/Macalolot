package pos.model;

import java.math.BigDecimal;

public class ProductModifierOption {
    private final int id;
    private final int modifierId;
    private final String name;
    private final BigDecimal price;

    public ProductModifierOption(int id, int modifierId, String name, BigDecimal price) {
        this.id = id;
        this.modifierId = modifierId;
        this.name = name;
        this.price = price;
    }

    public int getId(){ return id; }
    public int getModifierId(){ return modifierId; }
    public String getName(){ return name; }
    public BigDecimal getPrice(){ return price; }
}
