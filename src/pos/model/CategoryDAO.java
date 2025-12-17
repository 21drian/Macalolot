package pos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import session.Category;

public class CategoryDAO {

    public static ObservableList<Category> getAllCategories(Connection conn) {
        ObservableList<Category> categories = FXCollections.observableArrayList();
        String query = "SELECT id, name FROM category";

        try {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");

                categories.add(new Category(id, name));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return categories;
    }
    
    public static void insertCategory(Connection conn, String name) {
        try (PreparedStatement ps =
             conn.prepareStatement("INSERT INTO category (name) VALUES (?)")) {
            ps.setString(1, name);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void updateCategory(Connection conn, int id, String name) {
        try (PreparedStatement ps =
             conn.prepareStatement("UPDATE category SET name=? WHERE id=?")) {
            ps.setString(1, name);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void deleteCategory(Connection conn, int id) {
        try (PreparedStatement ps =
             conn.prepareStatement("DELETE FROM category WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

}
