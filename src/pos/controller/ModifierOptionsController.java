package pos.controller;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import pos.model.*;

public class ModifierOptionsController implements Initializable {

    @FXML private Label lblTitle;
    @FXML private TableView<ProductModifierOption> tableView;
    @FXML private TableColumn<ProductModifierOption,Integer> colId;
    @FXML private TableColumn<ProductModifierOption,String> colName;
    @FXML private TableColumn<ProductModifierOption,java.math.BigDecimal> colPrice;

    @FXML private TextField txtName, txtPrice;

    private Connection conn;
    private int modifierId;

    public void setModifier(int modifierId, String modifierName){
        this.modifierId = modifierId;
        lblTitle.setText("Options - " + modifierName);
        reload();
    }

    @Override public void initialize(URL url, ResourceBundle rb) {
        conn = DBconnection.getConnection();
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getSelectionModel().selectedItemProperty().addListener((o, old, opt) -> {
            if(opt == null) return;
            txtName.setText(opt.getName());
            txtPrice.setText(opt.getPrice() == null ? "0" : opt.getPrice().toString());
        });
    }

    private void reload(){
        if(modifierId <= 0) return;
        tableView.setItems(ModifierOptionDAO.getByModifier(conn, modifierId));
    }

    @FXML private void addOption(){
        ModifierOptionDAO.insert(conn, modifierId, txtName.getText(), parseD(txtPrice.getText()));
        reload();
    }

    @FXML private void updateOption(){
        ProductModifierOption opt = tableView.getSelectionModel().getSelectedItem();
        if(opt == null) return;
        ModifierOptionDAO.update(conn, opt.getId(), txtName.getText(), parseD(txtPrice.getText()));
        reload();
    }

    @FXML private void deleteOption(){
        ProductModifierOption opt = tableView.getSelectionModel().getSelectedItem();
        if(opt == null) return;
        ModifierOptionDAO.delete(conn, opt.getId());
        reload();
    }

    private double parseD(String s){ try { return Double.parseDouble(s.trim()); } catch(Exception e){ return 0; } }
}
