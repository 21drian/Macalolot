/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package pos.sidebar;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import session.UserSession;

public class SBController implements Initializable {

    @FXML
    private AnchorPane sidebar;
    @FXML
    private ImageView profileImage;
    @FXML
    private Label cashierName;
    @FXML
    private Label roleType;
    @FXML
    private Button btnDashboard;
    @FXML
    private Button btnProduct;
    @FXML
    private Button btnSales;
    @FXML
    private Button btnReport;
    @FXML
    private Button btnLogout;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cashierName.setText(UserSession.getEmail());
        roleType.setText(UserSession.getRole());
        
        boolean isCashier = UserSession.getRole() != null
            && UserSession.getRole().equalsIgnoreCase("cashier");
        
//        if (UserSession.getRole() != null && UserSession.getRole().equalsIgnoreCase("cashier")) {
//            btnDashboard.setVisible(false);
//            btnDashboard.setManaged(false);
//        }
        
         if (isCashier) {
            // show ONLY transaction (sales)
            btnDashboard.setVisible(false); btnDashboard.setManaged(false);
            btnProduct.setVisible(false);   btnProduct.setManaged(false);
            btnReport.setVisible(false);    btnReport.setManaged(false);

            // rename Sales -> Transaction
            btnSales.setText("Transaction");
        } else {
            // admin / others: normal
            btnSales.setText("Sales");
        }
    }

    @FXML
    private void handleLogoutAction(ActionEvent event) throws IOException {
        if(event.getSource() == btnLogout){
            Parent parent = FXMLLoader.load(getClass().getResource("/pos/view/Login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

            Stage logoutStage = (Stage) btnLogout.getScene().getWindow();
            logoutStage.close();
        }
    }

    @FXML
    private void handleProductAction(ActionEvent event) throws IOException {
        if (event.getSource() == btnProduct) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pos/view/Product.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
    
    @FXML
    private void handleDashboardAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/pos/view/Dashboard.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    @FXML
    private void handleSalesAction(ActionEvent event) throws IOException {

        boolean isCashier = UserSession.getRole() != null
                && UserSession.getRole().equalsIgnoreCase("cashier");

        String fxml = isCashier
                ? "/pos/view/Transaction.fxml"   
                : "/pos/view/pos.fxml";

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }



    @FXML
    private void handleReportAction(javafx.event.ActionEvent event) throws java.io.IOException {
        javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/pos/view/Report.fxml"));
        javafx.scene.Parent root = loader.load();

        javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        stage.setScene(new javafx.scene.Scene(root));
        stage.show();
    }


}
