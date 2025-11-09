
package session;

public class Category {
    private int id;
    private String category;

    public Category(int id, String category) {
        this.category = category;
        this.id = id;
    }

    public String getEmail() {
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
