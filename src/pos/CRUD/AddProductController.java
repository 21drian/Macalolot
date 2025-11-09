
package pos.CRUD;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.sql.Connection;
import pos.model.CategoryDAO;
import pos.model.DBconnection;
import session.Category;

/**
 * FXML Controller class
 *
 * @author Devbyte
 */
public class AddProductController implements Initializable {

    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField txtName;
    @FXML
    private TextArea txtDescription;
    @FXML
    private ComboBox<Category> cmbCategory;
    @FXML
    private ComboBox<?> cmbSupplier;
    @FXML
    private TextField txtSKU;
    @FXML
    private TextField txtBarcode;
    @FXML
    private ComboBox<String> cmbInventoryTracking;
    @FXML
    private TextField txtBaseUnit;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtInitialStock;
    @FXML
    private TextField txtReorderLevel;
    @FXML
    private ComboBox<String> cmbProductType;
    @FXML
    private Button btnUploadImage;
    @FXML
    private Label lblImageName;
    @FXML
    private Button btnSubmit;
    
    private Connection conn;
    
//    cmbCategory

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn = DBconnection.getConnection();
        
        cmbInventoryTracking.getItems().addAll("track_stock", "no_track");
        cmbProductType.getItems().addAll("simple", "variable");
        
        cmbCategory.setItems(CategoryDAO.getAllCategories(conn));
        
        Category selectedCategory = cmbCategory.getSelectionModel().getSelectedItem();
        if(selectedCategory != null){
            int categoryId = selectedCategory.getId();
            System.out.println("Selected Category ID: " + categoryId);
        }
    }    

    @FXML
    private void handleProductSubmit(ActionEvent event) {
        if(event.getSource() == btnSubmit){
            System.out.println("Submitted");
        }
    }
    
    
}
