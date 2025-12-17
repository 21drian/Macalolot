package pos.controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pos.model.*;

public class ModifierManagementController implements Initializable {

    @FXML private Label lblTitle;
    @FXML private TableView<ProductModifier> tableView;
    @FXML private TableColumn<ProductModifier,Integer> colId;
    @FXML private TableColumn<ProductModifier,String> colName;
    @FXML private TableColumn<ProductModifier,String> colType;
    @FXML private TableColumn<ProductModifier,Boolean> colRequired;

    @FXML private TextField txtName;
    @FXML private ComboBox<String> cmbType;
    @FXML private CheckBox chkRequired;

    private Connection conn;
    private int productId;

    public void setProduct(int productId, String productName){
        this.productId = productId;
        lblTitle.setText("Manage Modifiers - " + productName);
        reload();
    }

    @Override public void initialize(URL url, ResourceBundle rb) {
        conn = DBconnection.getConnection();
        cmbType.getItems().addAll("single", "multiple");

        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colRequired.setCellValueFactory(new PropertyValueFactory<>("required"));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, old, m) -> {
            if(m == null) return;
            txtName.setText(m.getName());
            cmbType.setValue(m.getType());
            chkRequired.setSelected(m.getRequired());
        });
    }

    private void reload(){
        if(productId <= 0) return;
        tableView.setItems(ModifierDAO.getByProduct(conn, productId));
    }

    @FXML private void addModifier(){
        ModifierDAO.insert(conn, productId, txtName.getText(), cmbType.getValue(), chkRequired.isSelected());
        reload();
    }

    @FXML private void updateModifier(){
        ProductModifier m = tableView.getSelectionModel().getSelectedItem();
        if(m == null) return;
        ModifierDAO.update(conn, m.getId(), txtName.getText(), cmbType.getValue(), chkRequired.isSelected());
        reload();
    }

    @FXML private void deleteModifier(){
        ProductModifier m = tableView.getSelectionModel().getSelectedItem();
        if(m == null) return;
        ModifierDAO.delete(conn, m.getId());
        reload();
    }

    @FXML private void openOptions() throws IOException {
        ProductModifier m = tableView.getSelectionModel().getSelectedItem();
        if(m == null) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pos/view/ModifierOptions.fxml"));
        Scene scene = new Scene(loader.load());

        ModifierOptionsController c = loader.getController();
        c.setModifier(m.getId(), m.getName());

        Stage stage = new Stage();
        stage.setTitle("Modifier Options");
        stage.setScene(scene);
        stage.show();
    }
}
