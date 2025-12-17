package pos.controller;

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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import session.UserSession;

public class PosController implements Initializable {

    private Button logoutButton;
    private Button productButton;
    @FXML
    private Label cashierName;
    @FXML
    private Label roleType;
    @FXML
    private AnchorPane sidebarContainer;
    @FXML
    private AnchorPane contentArea;

    @FXML
    private AnchorPane rootPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        loadSidebar();
        cashierName.setText( UserSession.getEmail() );
        roleType.setText( UserSession.getRole() );
        loadTransaction();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pos/view/pos_content.fxml"));
            AnchorPane posContent = loader.load();
            contentArea.getChildren().setAll(posContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        

    }

    private void loadSidebar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pos/sidebar/sidebar.fxml"));
            AnchorPane sidebar = loader.load();
            sidebarContainer.getChildren().setAll(sidebar);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void loadTransaction() {
        try {
            AnchorPane transactionView = FXMLLoader.load(getClass().getResource("/pos/view/Transaction.fxml"));
            contentArea.getChildren().setAll(transactionView);

            AnchorPane.setTopAnchor(transactionView, 0.0);
            AnchorPane.setBottomAnchor(transactionView, 0.0);
            AnchorPane.setLeftAnchor(transactionView, 0.0);
            AnchorPane.setRightAnchor(transactionView, 0.0);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void handleLogoutButton(ActionEvent event) throws IOException {
        if (event.getSource() == logoutButton) {
            Parent parent = FXMLLoader.load(getClass().getResource("/pos/view/Login.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.show();

            Stage logoutStage = (Stage) logoutButton.getScene().getWindow();
            logoutStage.close();
        }
    }

    private void handleProductAction(ActionEvent event) throws IOException {
        if (event.getSource() == productButton) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/pos/view/Product.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
