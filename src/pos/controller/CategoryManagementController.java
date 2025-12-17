package pos.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pos.model.DBconnection;
import session.Category;
import pos.model.CategoryDAO;
import pos.util.AlertUtil;


public class CategoryManagementController implements Initializable {

    @FXML private TableView<Category> tableView;
    @FXML private TableColumn<Category, Integer> colId;
    @FXML private TableColumn<Category, String> colName;
    @FXML private TextField txtName;

    private Connection conn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBconnection.getConnection();

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("category"));

        loadTable();

        tableView.getSelectionModel().selectedItemProperty().addListener((obs, o, n) -> {
            if (n != null) txtName.setText(n.toString());
        });
    }

    private void loadTable() {
        tableView.setItems(CategoryDAO.getAllCategories(conn));
    }

    @FXML
    private void addCategory() {
        if (txtName.getText().isEmpty()) {
            AlertUtil.warning("Validation", "Category name is required.");
            return;
        }

        CategoryDAO.insertCategory(conn, txtName.getText());
        loadTable();
        txtName.clear();

        AlertUtil.info("Success", "Category added successfully.");
    }

    @FXML
    private void updateCategory() {
        Category c = tableView.getSelectionModel().getSelectedItem();
        if (c == null) {
            AlertUtil.warning("No Selection", "Please select a category.");
            return;
        }

        CategoryDAO.updateCategory(conn, c.getId(), txtName.getText());
        loadTable();

        AlertUtil.info("Updated", "Category updated successfully.");
    }

    @FXML
    private void deleteCategory() {
        Category c = tableView.getSelectionModel().getSelectedItem();
        if (c == null) {
            AlertUtil.warning("No Selection", "Please select a category.");
            return;
        }

        if (!AlertUtil.confirm("Confirm Delete", "Delete this category?")) return;

        CategoryDAO.deleteCategory(conn, c.getId());
        loadTable();

        AlertUtil.info("Deleted", "Category deleted successfully.");
    }

}
