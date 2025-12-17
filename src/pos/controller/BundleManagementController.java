package pos.controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pos.model.*;

public class BundleManagementController implements Initializable {

    @FXML private Label lblTitle;
    @FXML private TableView<BundleItem> tableView;
    @FXML private TableColumn<BundleItem,Integer> colId;
    @FXML private TableColumn<BundleItem,String> colItemName;
    @FXML private TableColumn<BundleItem,Integer> colQty;

    @FXML private ComboBox<ProductPick> cmbProducts;
    @FXML private TextField txtQty;

    private Connection conn;
    private int bundleProductId;

    public void setBundleProduct(int productId, String productName){
        this.bundleProductId = productId;
        lblTitle.setText("Bundle Items - " + productName);
        loadProducts();
        reload();
    }

    @Override public void initialize(URL url, ResourceBundle rb) {
        conn = DBconnection.getConnection();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colItemName.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, old, bi) -> {
            if(bi != null) txtQty.setText(String.valueOf(bi.getQuantity()));
        });
    }

    private void loadProducts(){
        ObservableList<ProductPick> list = FXCollections.observableArrayList();
        String sql = "SELECT id, name FROM product WHERE isActive=1 ORDER BY name";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(new ProductPick(rs.getInt("id"), rs.getString("name")));
        } catch(Exception e){ e.printStackTrace(); }
        cmbProducts.setItems(list);
    }

    private void reload(){
        tableView.setItems(BundleDAO.getItems(conn, bundleProductId));
    }

    @FXML private void addItem(){
        ProductPick p = cmbProducts.getSelectionModel().getSelectedItem();
        if(p == null) return;
        BundleDAO.insert(conn, bundleProductId, p.id, parseI(txtQty.getText()));
        reload();
    }

    @FXML private void updateQty(){
        BundleItem bi = tableView.getSelectionModel().getSelectedItem();
        if(bi == null) return;
        BundleDAO.update(conn, bi.getId(), parseI(txtQty.getText()));
        reload();
    }

    @FXML private void deleteItem(){
        BundleItem bi = tableView.getSelectionModel().getSelectedItem();
        if(bi == null) return;
        BundleDAO.delete(conn, bi.getId());
        reload();
    }

    private int parseI(String s){ try { return Integer.parseInt(s.trim()); } catch(Exception e){ return 1; } }

    public static class ProductPick {
        public final int id; public final String name;
        public ProductPick(int id, String name){ this.id=id; this.name=name; }
        @Override public String toString(){ return name; }
    }
}
