package pos.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pos.model.*;

public class VariantManagementController implements Initializable {

    @FXML private Label lblTitle;
    @FXML private TableView<ProductVariant> tableView;
    @FXML private TableColumn<ProductVariant,Integer> colId;
    @FXML private TableColumn<ProductVariant,String> colName;
    @FXML private TableColumn<ProductVariant,String> colSku;
    @FXML private TableColumn<ProductVariant,String> colBarcode;
    @FXML private TableColumn<ProductVariant,java.math.BigDecimal> colPrice;
    @FXML private TableColumn<ProductVariant,Integer> colStock;
    @FXML private TableColumn<ProductVariant,Boolean> colActive;

    @FXML private TextField txtName, txtSku, txtBarcode, txtPrice, txtCost, txtStock;
    @FXML private CheckBox chkActive;

    private Connection conn;
    private int productId;

    public void setProduct(int productId, String productName) {
        this.productId = productId;
        lblTitle.setText("Manage Variants - " + productName);
        reload();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBconnection.getConnection();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colSku.setCellValueFactory(new PropertyValueFactory<>("sku"));
        colBarcode.setCellValueFactory(new PropertyValueFactory<>("barcode"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        colActive.setCellValueFactory(new PropertyValueFactory<>("isActive"));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, old, v) -> {
            if (v == null) return;
            txtName.setText(v.getName());
            txtSku.setText(v.getSku());
            txtBarcode.setText(v.getBarcode());
            txtPrice.setText(v.getPrice() == null ? "0" : v.getPrice().toString());
            txtCost.setText(v.getCost() == null ? "0" : v.getCost().toString());
            txtStock.setText(String.valueOf(v.getStock()));
            chkActive.setSelected(v.getIsActive());
        });
    }

    private void reload() {
        if (productId <= 0) return;
        tableView.setItems(VariantDAO.getByProduct(conn, productId));
    }

    @FXML private void addVariant() {
        VariantDAO.insert(conn, productId,
                txtName.getText(),
                txtSku.getText(),
                txtBarcode.getText(),
                parseD(txtPrice.getText()),
                parseD(txtCost.getText()),
                parseI(txtStock.getText()),
                chkActive.isSelected()
        );
        reload();
    }

    @FXML private void updateVariant() {
        ProductVariant v = tableView.getSelectionModel().getSelectedItem();
        if (v == null) return;
        VariantDAO.update(conn, v.getId(),
                txtName.getText(),
                txtSku.getText(),
                txtBarcode.getText(),
                parseD(txtPrice.getText()),
                parseD(txtCost.getText()),
                parseI(txtStock.getText()),
                chkActive.isSelected()
        );
        reload();
    }

    @FXML private void deleteVariant() {
        ProductVariant v = tableView.getSelectionModel().getSelectedItem();
        if (v == null) return;
        VariantDAO.delete(conn, v.getId());
        reload();
    }

    private int parseI(String s){ try { return Integer.parseInt(s.trim()); } catch(Exception e){ return 0; } }
    private double parseD(String s){ try { return Double.parseDouble(s.trim()); } catch(Exception e){ return 0; } }
}
