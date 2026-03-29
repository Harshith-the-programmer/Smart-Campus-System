package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginUI extends Application {

    @Override
    public void start(Stage stage) {

        Label title = new Label("Login");

        TextField username = new TextField();
        username.setPromptText("Username");

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        ComboBox<String> role = new ComboBox<>();
        role.getItems().addAll("Admin", "Faculty", "Student");
        role.setPromptText("Select Role");

        Button loginBtn = new Button("Login");

        loginBtn.setOnAction(e -> {

            if (role.getValue() == null) {
                new Alert(Alert.AlertType.ERROR, "Select role").show();
                return;
            }

            // simple demo login
            if (username.getText().equals("admin") && password.getText().equals("123")) {

                try {
                    new MainUI(role.getValue()).start(new Stage());
                    stage.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Invalid login").show();
            }
        });

        VBox root = new VBox(10, title, username, password, role, loginBtn);
        root.setStyle("-fx-padding:20; -fx-alignment:center;");

        stage.setScene(new Scene(root, 300, 300));
        stage.setTitle("Login");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}