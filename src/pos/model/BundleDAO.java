package pos.model;

import java.sql.*;
import javafx.collections.*;

public class BundleDAO {

    public static ObservableList<BundleItem> getItems(Connection conn, int bundleProductId){
        ObservableList<BundleItem> list = FXCollections.observableArrayList();
        String sql =
            "SELECT b.id, b.bundleProductId, b.itemProductId, p.name AS itemName, b.quantity " +
            "FROM productbundle b JOIN product p ON p.id=b.itemProductId " +
            "WHERE b.bundleProductId=? ORDER BY b.id DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bundleProductId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new BundleItem(
                            rs.getInt("id"),
                            rs.getInt("bundleProductId"),
                            rs.getInt("itemProductId"),
                            rs.getString("itemName"),
                            rs.getInt("quantity")
                    ));
                }
            }
        } catch(Exception e){ e.printStackTrace(); }
        return list;
    }

    public static void insert(Connection conn, int bundleProductId, int itemProductId, int qty){
        String sql = "INSERT INTO productbundle(bundleProductId,itemProductId,quantity,createdAt,updatedAt) VALUES(?,?,?,NOW(),NOW())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bundleProductId);
            ps.setInt(2, itemProductId);
            ps.setInt(3, qty);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void update(Connection conn, int id, int qty){
        String sql = "UPDATE productbundle SET quantity=?, updatedAt=NOW() WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, qty);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }

    public static void delete(Connection conn, int id){
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM productbundle WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch(Exception e){ e.printStackTrace(); }
    }
}
