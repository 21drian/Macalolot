package pos.model;

import java.sql.*;
import javafx.collections.*;

public class ModifierOptionDAO {

    public static ObservableList<ProductModifierOption> getByModifier(Connection conn, int modifierId){
        ObservableList<ProductModifierOption> list = FXCollections.observableArrayList();
        String sql = "SELECT id, modifierId, name, price FROM productmodifieroption WHERE modifierId=? ORDER BY id DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, modifierId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ProductModifierOption(
                            rs.getInt("id"),
                            rs.getInt("modifierId"),
                            rs.getString("name"),
                            rs.getBigDecimal("price")
                    ));
                }
            }
        } catch(Exception e){ e.printStackTrace(); }
        return list;
    }

    public static void insert(Connection conn, int modifierId, String name, double price){
        String sql = "INSERT INTO productmodifieroption(modifierId,name,price,createdAt,updatedAt) VALUES(?,?,?,NOW(),NOW())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, modifierId);
            ps.setString(2, name);
            ps.setDouble(3, price);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void update(Connection conn, int id, String name, double price){
        String sql = "UPDATE productmodifieroption SET name=?, price=?, updatedAt=NOW() WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, id);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void delete(Connection conn, int id){
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM productmodifieroption WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }
}
