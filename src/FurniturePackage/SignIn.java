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

public class SignIn extends Application {
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

        // Right side with sign up form
        VBox rightPanel = new VBox(25);
        rightPanel.setPadding(new Insets(50));
        rightPanel.setAlignment(Pos.CENTER);
        rightPanel.setPrefWidth(500);
        rightPanel.setStyle("-fx-background-color: white;");

        Label lblTitle = new Label("Create Account");
        lblTitle.getStyleClass().add("form-title");

        Label lblSubtitle = new Label("Sign up to get started");
        lblSubtitle.getStyleClass().add("form-subtitle");

        // Username field with icon
        HBox usernameBox = new HBox(10);
        usernameBox.setAlignment(Pos.CENTER_LEFT);
        TextField txtUsername = new TextField();
        txtUsername.setPromptText("Username");
        txtUsername.getStyleClass().add("modern-field");
        usernameBox.getChildren().add(txtUsername);

        // Email field with icon
        HBox emailBox = new HBox(10);
        emailBox.setAlignment(Pos.CENTER_LEFT);
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Email");
        txtEmail.getStyleClass().add("modern-field");
        emailBox.getChildren().add(txtEmail);

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
        Button btnSignIn = new Button("Sign Up");
        btnSignIn.getStyleClass().add("primary-button");

        HBox linkBox = new HBox(10);
        linkBox.setAlignment(Pos.CENTER);
        Label haveAccountLabel = new Label("Already have an account?");
        haveAccountLabel.getStyleClass().add("text-muted");
        Hyperlink loginLink = new Hyperlink("Login");
        loginLink.getStyleClass().add("accent-link");
        linkBox.getChildren().addAll(haveAccountLabel, loginLink);

        // Maintain existing functionality
        txtUsername.textProperty().addListener((observable, oldValue, newValue) -> {
            lblFeedback.setText("Input: " + newValue);
        });

        loginLink.setOnAction(e -> {
            System.out.println("Login link clicked...");
            openLoginPage(primaryStage);
        });

        btnSignIn.setOnAction(e -> {
            System.out.println("Sign In button clicked...");
            String username = txtUsername.getText();
            String email = txtEmail.getText();
            String password = txtpwd.getText();

            // Validate input
            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                lblFeedback.setText("Please fill in all fields");
                lblFeedback.setTextFill(Color.RED);
                return;
            }

            // Validate email format
            if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                lblFeedback.setText("Please enter a valid email address");
                lblFeedback.setTextFill(Color.RED);
                return;
            }

            // Check if username or email already exists
            DatabaseConnection dbConnection = new DatabaseConnection();
            if (dbConnection.usernameExists(username)) {
                lblFeedback.setText("Username already exists");
                lblFeedback.setTextFill(Color.RED);
                dbConnection.closeConnection();
                return;
            }

            if (dbConnection.emailExists(email)) {
                lblFeedback.setText("Email already exists");
                lblFeedback.setTextFill(Color.RED);
                dbConnection.closeConnection();
                return;
            }

            // Register user
            boolean isRegistered = dbConnection.registerUser(username, email, password);
            dbConnection.closeConnection();

            if (isRegistered) {
                lblFeedback.setText("Registration successful!");
                lblFeedback.setTextFill(Color.GREEN);

                // Wait for a moment before redirecting to login page
                new Thread(() -> {
                    try {
                        Thread.sleep(2000);
                        javafx.application.Platform.runLater(() -> openLoginPage(primaryStage));
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).start();
            } else {
                lblFeedback.setText("Registration failed. Please try again.");
                lblFeedback.setTextFill(Color.RED);
            }
        });

        rightPanel.getChildren().addAll(
                lblTitle,
                lblSubtitle,
                usernameBox,
                emailBox,
                passwordBox,
                lblFeedback,
                btnSignIn,
                linkBox
        );

        // Add panels to main container
        mainContainer.setLeft(leftPanel);
        mainContainer.setCenter(rightPanel);

        Scene scene = new Scene(mainContainer, 1000, 600);
        scene.getStylesheets().add(getClass().getResource("logsig.css").toExternalForm());

        primaryStage.setTitle("Sign Up");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void openLoginPage(Stage primaryStage) {
        login login = new login();
        login.start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
