package pos.controller;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pos.model.DBconnection;
import pos.model.Supplier;
import pos.util.AlertUtil;

public class SupplierManagementController implements Initializable {

    @FXML private TableView<Supplier> tableView;
    @FXML private TableColumn<Supplier, Integer> colId;
    @FXML private TableColumn<Supplier, String> colName;
    @FXML private TableColumn<Supplier, String> colEmail;
    @FXML private TableColumn<Supplier, String> colPhone;

    @FXML private TextField txtName, txtEmail, txtPhone, txtAddress;

    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBconnection.getConnection();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));

        loadTable();

        tableView.getSelectionModel().selectedItemProperty().addListener((o, old, s) -> {
            if (s != null) {
                txtName.setText(s.getName());
                txtEmail.setText(s.getEmail());
                txtPhone.setText(s.getPhone());
                txtAddress.setText(s.getAddress());
            }
        });
    }

    private void loadTable() {
        ObservableList<Supplier> list = FXCollections.observableArrayList();
        try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM supplier");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(new Supplier(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getString("address")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }

        tableView.setItems(list);
    }

    @FXML
    private void addSupplier() {
        try (PreparedStatement ps =
             conn.prepareStatement("INSERT INTO supplier (name,email,phone,address) VALUES (?,?,?,?)")) {
            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtPhone.getText());
            ps.setString(4, txtAddress.getText());
            ps.executeUpdate();
            loadTable();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void updateSupplier() {
        Supplier s = tableView.getSelectionModel().getSelectedItem();
        if (s == null) return;

        try (PreparedStatement ps =
             conn.prepareStatement("UPDATE supplier SET name=?,email=?,phone=?,address=? WHERE id=?")) {
            ps.setString(1, txtName.getText());
            ps.setString(2, txtEmail.getText());
            ps.setString(3, txtPhone.getText());
            ps.setString(4, txtAddress.getText());
            ps.setInt(5, s.getId());
            ps.executeUpdate();
            loadTable();
        } catch (Exception e) { e.printStackTrace(); }
    }

    @FXML
    private void deleteSupplier() {
        Supplier s = tableView.getSelectionModel().getSelectedItem();
        if (s == null) {
            AlertUtil.warning("No Selection", "Please select a supplier.");
            return;
        }

        if (!AlertUtil.confirm("Confirm Delete", "Delete supplier " + s.getName() + "?")) return;

        try (PreparedStatement ps =
             conn.prepareStatement("DELETE FROM supplier WHERE id=?")) {
            ps.setInt(1, s.getId());
            ps.executeUpdate();
            loadTable();

            AlertUtil.info("Deleted", "Supplier deleted successfully.");
        } catch (Exception e) {
            AlertUtil.error("Error", e.getMessage());
        }
    }

}
