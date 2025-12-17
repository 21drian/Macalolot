package pos.model;

import java.sql.*;
import java.util.*;
import javafx.collections.*;

public class VariantDAO {

    public static ObservableList<ProductVariant> getByProduct(Connection conn, int productId) {
        ObservableList<ProductVariant> list = FXCollections.observableArrayList();
        String sql = "SELECT id, productId, name, sku, barcode, price, cost, stock, isActive " +
                     "FROM productvariant WHERE productId=? ORDER BY id DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(new ProductVariant(
                            rs.getInt("id"),
                            rs.getInt("productId"),
                            rs.getString("name"),
                            rs.getString("sku"),
                            rs.getString("barcode"),
                            rs.getBigDecimal("price"),
                            rs.getBigDecimal("cost"),
                            rs.getInt("stock"),
                            rs.getBoolean("isActive")
                    ));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    public static void insert(Connection conn, int productId, String name, String sku, String barcode,
                              double price, double cost, int stock, boolean isActive) {
        String sql = "INSERT INTO productvariant(productId,name,sku,barcode,price,cost,stock,isActive,createdAt,updatedAt) " +
                     "VALUES(?,?,?,?,?,?,?,?,NOW(),NOW())";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, productId);
            ps.setString(2, name);
            ps.setString(3, sku);
            ps.setString(4, barcode);
            ps.setDouble(5, price);
            ps.setDouble(6, cost);
            ps.setInt(7, stock);
            ps.setBoolean(8, isActive);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void update(Connection conn, int id, String name, String sku, String barcode,
                              double price, double cost, int stock, boolean isActive) {
        String sql = "UPDATE productvariant SET name=?,sku=?,barcode=?,price=?,cost=?,stock=?,isActive=?,updatedAt=NOW() WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);
            ps.setString(2, sku);
            ps.setString(3, barcode);
            ps.setDouble(4, price);
            ps.setDouble(5, cost);
            ps.setInt(6, stock);
            ps.setBoolean(7, isActive);
            ps.setInt(8, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static void delete(Connection conn, int id) {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM productvariant WHERE id=?")) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (Exception e) { e.printStackTrace(); }
    }
}
