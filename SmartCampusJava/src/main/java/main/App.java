package main;
import java.sql.*;


public class App {
    public static void main(String[] args) {

        String url = "jdbc:mysql://localhost:3306/smart_campus";
        String user = "root";
        String password = "Harshith@1106.";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement()) {

            // Get next student_id
            ResultSet rs = stmt.executeQuery("SELECT MAX(student_id) FROM student");
            int nextId = 101;

            if (rs.next() && rs.getInt(1) != 0) {
                nextId = rs.getInt(1) + 1; 
            }

            String sql = "INSERT INTO student VALUES (" +
                    nextId + ", 'Demo Student', 'CSE', 3, 'demo@gmail.com')";

            stmt.executeUpdate(sql);
            System.out.println("✅ Student inserted with ID: " + nextId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
