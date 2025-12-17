package pos.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class SaleDAO {

    public static void checkout(Connection conn, List<CartItem> cart) throws Exception {
        if (cart == null || cart.isEmpty()) throw new Exception("Cart is empty.");

        conn.setAutoCommit(false);
        try {
            for (CartItem item : cart) {

                // check tracking + stock
                String q1 = "SELECT currentStock, inventoryTracking FROM product WHERE id=?";
                try (PreparedStatement ps = conn.prepareStatement(q1)) {
                    ps.setInt(1, item.getProductId());
                    try (ResultSet rs = ps.executeQuery()) {
                        if (!rs.next()) throw new Exception("Product not found: " + item.getProductId());

                        String tracking = rs.getString("inventoryTracking");
                        int currentStock = rs.getInt("currentStock");

                        if ("track_stock".equalsIgnoreCase(tracking) && currentStock < item.getQuantity()) {
                            throw new Exception("Not enough stock for: " + item.getName());
                        }
                    }
                }

                // insert sale row
                String ins = "INSERT INTO sale (productId, quantity, total, createdAt, updatedAt) VALUES (?,?,?,NOW(),NOW())";
                try (PreparedStatement ps = conn.prepareStatement(ins)) {
                    ps.setInt(1, item.getProductId());
                    ps.setInt(2, item.getQuantity());
                    ps.setBigDecimal(3, item.getLineTotal());
                    ps.executeUpdate();
                }

                // deduct stock if tracking
                String upd = "UPDATE product SET currentStock=currentStock-? WHERE id=? AND inventoryTracking='track_stock'";
                try (PreparedStatement ps = conn.prepareStatement(upd)) {
                    ps.setInt(1, item.getQuantity());
                    ps.setInt(2, item.getProductId());
                    ps.executeUpdate();
                }
            }

            conn.commit();
        } catch (Exception e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }
}
