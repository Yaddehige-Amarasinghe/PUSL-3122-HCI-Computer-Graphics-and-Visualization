package FurniturePackage;
import javax.swing.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.effect.DropShadow;

public class login extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Main container with modern styling
        BorderPane mainContainer = new BorderPane();

        // Left side with brand image/logo
        VBox leftPanel = new VBox(20);
        leftPanel.setPrefWidth(500);
        leftPanel.setStyle("-fx-background-color: #2c3e50;");
        leftPanel.setAlignment(Pos.CENTER);

        // Brand logo or image could go here
        Label brandName = new Label("Furniture Hub");
        brandName.getStyleClass().add("brand-label");

        Text tagline = new Text("Design your perfect space");
        tagline.getStyleClass().add("tagline");

        leftPanel.getChildren().addAll(brandName, tagline);

        // Right side with login form
        VBox rightPanel = new VBox(25);
        rightPanel.setPadding(new Insets(50));
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPrefWidth(500);
        rightPanel.setStyle("-fx-background-color: white;");

        Label lblTitle = new Label("Welcome Back");
        lblTitle.getStyleClass().add("form-title");

        Label lblSubtitle = new Label("Login to your account");
        lblSubtitle.getStyleClass().add("form-subtitle");

        // Username field with icon
        HBox usernameBox = new HBox(10);
        usernameBox.setAlignment(Pos.CENTER_LEFT);
        TextField txtInput = new TextField();
        txtInput.setPromptText("Username");
        txtInput.getStyleClass().add("modern-field");
        usernameBox.getChildren().add(txtInput);

        // Password field with icon
        HBox passwordBox = new HBox(10);
        passwordBox.setAlignment(Pos.CENTER_LEFT);
        PasswordField txtpwd = new PasswordField();
        txtpwd.setPromptText("Password");
        txtpwd.getStyleClass().add("modern-field");
        passwordBox.getChildren().add(txtpwd);

        Label lblFeedback = new Label("Welcome to the FurniMaker!");
        lblFeedback.getStyleClass().add("feedback-label");

        // Buttons with modern styling
        Button btnLogin = new Button("Login");
        btnLogin.getStyleClass().add("primary-button");

        HBox linkBox = new HBox(10);
        linkBox.setAlignment(Pos.CENTER);
        Label noAccountLabel = new Label("Don't have an account?");
        noAccountLabel.getStyleClass().add("text-muted");
        Hyperlink signInLink = new Hyperlink("Sign Up");
        signInLink.getStyleClass().add("accent-link");
        linkBox.getChildren().addAll(noAccountLabel, signInLink);

        // Maintain existing functionality
        txtInput.textProperty().addListener((observable, oldValue, newValue) -> {
            lblFeedback.setText("Input: " + newValue);
        });

        btnLogin.setOnAction(e -> {
            System.out.println("Login button clicked...");
            String username = txtInput.getText();
            String password = txtpwd.getText();

            // Validate input
            if (username.isEmpty() || password.isEmpty()) {
                lblFeedback.setText("Please enter both username and password");
                lblFeedback.setTextFill(Color.RED);
                return;
            }

            // Authenticate user
            DatabaseConnection dbConnection = new DatabaseConnection();
            boolean isAuthenticated = dbConnection.authenticateUser(username, password);
            dbConnection.closeConnection();

            if (isAuthenticated) {
                lblFeedback.setText("Login successful!");
                lblFeedback.setTextFill(Color.GREEN);
                opendash(primaryStage, username);
            } else {
                lblFeedback.setText("Invalid username or password");
                lblFeedback.setTextFill(Color.RED);
            }
        });

        signInLink.setOnAction(e -> {
            System.out.println("Sign In link clicked...");
            openSignInPage(primaryStage);
        });

        rightPanel.getChildren().addAll(
                lblTitle,
                lblSubtitle,
                usernameBox,
                passwordBox,
                lblFeedback,
                btnLogin,
                linkBox
        );

        // Add panels to main container
        mainContainer.setLeft(leftPanel);
        mainContainer.setCenter(rightPanel);

        Scene scene = new Scene(mainContainer, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("logsig.css").toExternalForm());

        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openSignInPage(Stage primaryStage) {
        SignIn signIn = new SignIn();
        signIn.start(primaryStage);
    }

    private void opendash(Stage primaryStage, String username) {
        DashboardApp Dash = new DashboardApp();
        Dash.setUsername(username);
        Dash.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
