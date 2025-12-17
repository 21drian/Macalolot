package pos.model;

import java.sql.*;
import javafx.collections.*;

public class ModifierDAO {

    public static ObservableList<ProductModifier> getByProduct(Connection conn, int productId){
        ObservableList<ProductModifier> list = FXCollections.observableArrayList();
        String sql = "SELECT id, productId, name, type, required FROM productmodifier WHERE productId=? ORDER BY id DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ProductModifier(
                            rs.getInt("id"),
                            rs.getInt("productId"),
                            rs.getString("name"),
                            rs.getString("type"),
                            rs.getBoolean("required")
                    ));
                }
            }
        } catch(Exception e){ e.printStackTrace(); }
        return list;
    }

    public static void insert(Connection conn, int productId, String name, String type, boolean required){
        String sql = "INSERT INTO productmodifier(productId,name,type,required,createdAt,updatedAt) VALUES(?,?,?,?,NOW(),NOW())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, name);
            ps.setString(3, type);
            ps.setBoolean(4, required);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void update(Connection conn, int id, String name, String type, boolean required){
        String sql = "UPDATE productmodifier SET name=?, type=?, required=?, updatedAt=NOW() WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, type);
            ps.setBoolean(3, required);
            ps.setInt(4, id);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void delete(Connection conn, int id){
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM productmodifier WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }
}
