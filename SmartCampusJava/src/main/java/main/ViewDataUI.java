package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class ViewDataUI extends Application {

    @Override
    public void start(Stage stage) {

        TableView<String> table = new TableView<>();

        TableColumn<String, String> col = new TableColumn<>("Students");
        col.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue()));

        table.getColumns().add(col);

        Button loadBtn = new Button("Load Students");

        loadBtn.setOnAction(e -> {
            table.getItems().clear();

            try (Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/smart_campus",
                    "root",
                    "Harshith@1106.");
                 Statement stmt = con.createStatement()) {

                ResultSet rs = stmt.executeQuery("SELECT * FROM student");

                while (rs.next()) {
                    String row =
                            rs.getInt("student_id") + " | " +
                            rs.getString("student_name") + " | " +
                            rs.getString("department") + " | " +
                            rs.getInt("semester");

                    table.getItems().add(row);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox root = new VBox(10, loadBtn, table);
        root.setStyle("-fx-padding:20;");

        stage.setScene(new Scene(root, 500, 400));
        stage.setTitle("View Students");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}