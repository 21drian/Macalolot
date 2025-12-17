
package session;

public class Category {
    private int id;
    private String category;

    public Category(int id, String category) {
        this.category = category;
        this.id = id;
    }

    public String getCategory() {
        return category; 
    }
    
    public int getId() {
        return id;
    }
    
    @Override
    public String toString() {
        return category;
    }
}
