package main;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.*;

public class MainUI extends Application {

    private String role;

    public MainUI(String role) {
        this.role = role;
    }

    public MainUI() {}

    @Override
    public void start(Stage stage) {

        Connection con = null;

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/smart_campus",
                    "root",
                    "Harshith@1106."
            );
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* ---------- TITLE ---------- */
        Label title = new Label("🎓 Smart Campus System");
        title.setStyle("-fx-font-size:22px; -fx-text-fill:#2c3e50; -fx-font-weight:bold;");

        /* ---------- STUDENT ---------- */
        Label studentLabel = new Label("Add Student");

        TextField name = new TextField();
        name.setPromptText("Name");

        TextField dept = new TextField();
        dept.setPromptText("Department");

        TextField year = new TextField();
        year.setPromptText("Year");

        TextField email = new TextField();
        email.setPromptText("Email");

        Button addStudentBtn = new Button("Add Student");

        addStudentBtn.setOnAction(e -> {
            try {
                StudentDAO.insertStudent(
                        name.getText(),
                        dept.getText(),
                        Integer.parseInt(year.getText()),
                        email.getText()
                );
                new Alert(Alert.AlertType.INFORMATION, "Student Added").show();

                name.clear(); dept.clear(); year.clear(); email.clear();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Invalid Input").show();
            }
        });

        Button viewBtn = new Button("View Students");

        viewBtn.setOnAction(e -> {
            try {
                new ViewDataUI().start(new Stage());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        VBox studentBox = new VBox(8, studentLabel, name, dept, year, email, addStudentBtn, viewBtn);
        studentBox.setStyle("-fx-background-color:white; -fx-padding:15; -fx-background-radius:10; -fx-effect: dropshadow(gaussian, lightgray, 5, 0, 0, 2);");

        /* ---------- ATTENDANCE ---------- */
        Label attendanceLabel = new Label("Mark Attendance");

        ComboBox<Integer> sid = new ComboBox<>();
        sid.setPromptText("Student");

        ComboBox<Integer> cid = new ComboBox<>();
        cid.setPromptText("Course");

        TextField date = new TextField();
        date.setPromptText("YYYY-MM-DD");

        ComboBox<String> status = new ComboBox<>();
        status.getItems().addAll("Present", "Absent");
        status.setPromptText("Status");

        Button markBtn = new Button("Mark");

        try {
            Statement stmt = con.createStatement();

            ResultSet rs1 = stmt.executeQuery("SELECT student_id FROM student");
            while (rs1.next()) sid.getItems().add(rs1.getInt(1));

            ResultSet rs2 = stmt.executeQuery("SELECT course_id FROM course");
            while (rs2.next()) cid.getItems().add(rs2.getInt(1));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        markBtn.setOnAction(e -> {
            try {
                AttendanceDAO.markAttendance(
                        sid.getValue(),
                        cid.getValue(),
                        date.getText(),
                        status.getValue()
                );
                new Alert(Alert.AlertType.INFORMATION, "Attendance Marked").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        });

        VBox attendanceBox = new VBox(8, attendanceLabel, sid, cid, date, status, markBtn);
        attendanceBox.setStyle("-fx-background-color:white; -fx-padding:15; -fx-background-radius:10; -fx-effect: dropshadow(gaussian, lightgray, 5, 0, 0, 2);");

        /* ---------- FEEDBACK ---------- */
        Label feedbackLabel = new Label("Submit Feedback");

        ComboBox<Integer> fsid = new ComboBox<>();
        fsid.setPromptText("Student");

        ComboBox<Integer> fcid = new ComboBox<>();
        fcid.setPromptText("Course");

        TextField rating = new TextField();
        rating.setPromptText("1-5");

        TextField comments = new TextField();
        comments.setPromptText("Comments");

        Button feedbackBtn = new Button("Submit");

        try {
            Statement stmt = con.createStatement();

            ResultSet rs1 = stmt.executeQuery("SELECT student_id FROM student");
            while (rs1.next()) fsid.getItems().add(rs1.getInt(1));

            ResultSet rs2 = stmt.executeQuery("SELECT course_id FROM course");
            while (rs2.next()) fcid.getItems().add(rs2.getInt(1));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        feedbackBtn.setOnAction(e -> {
            try {
                FeedbackDAO.submitFeedback(
                        fsid.getValue(),
                        fcid.getValue(),
                        Integer.parseInt(rating.getText()),
                        comments.getText()
                );
                new Alert(Alert.AlertType.INFORMATION, "Feedback Submitted").show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Error").show();
            }
        });

        VBox feedbackBox = new VBox(8, feedbackLabel, fsid, fcid, rating, comments, feedbackBtn);
        feedbackBox.setStyle("-fx-background-color:white; -fx-padding:15; -fx-background-radius:10; -fx-effect: dropshadow(gaussian, lightgray, 5, 0, 0, 2);");

        /* ---------- ROOT ---------- */
        VBox root = new VBox(20);
        root.setAlignment(Pos.TOP_CENTER);
        root.setStyle("-fx-background-color:#f5f7fa; -fx-padding:25;");

        root.getChildren().add(title);

        if ("Admin".equals(role)) {
            root.getChildren().add(studentBox);
        }
        else if ("Faculty".equals(role)) {
            root.getChildren().add(attendanceBox);
        }
        else if ("Student".equals(role)) {
            root.getChildren().add(feedbackBox);
        }

        Scene scene = new Scene(root, 450, 550);
        stage.setScene(scene);
        stage.setTitle("Smart Campus");
        stage.show();
    }
}