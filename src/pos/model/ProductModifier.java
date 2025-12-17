package pos.model;

public class ProductModifier {
    private final int id;
    private final int productId;
    private final String name;
    private final String type;   // single or multiple
    private final boolean required;

    public ProductModifier(int id, int productId, String name, String type, boolean required) {
        this.id = id;
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.required = required;
    }

    public int getId(){ return id; }
    public int getProductId(){ return productId; }
    public String getName(){ return name; }
    public String getType(){ return type; }
    public boolean getRequired(){ return required; }
}
