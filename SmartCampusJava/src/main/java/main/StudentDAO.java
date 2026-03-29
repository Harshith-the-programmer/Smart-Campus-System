package main;
import java.sql.*;

public class StudentDAO {

    static final String URL = "jdbc:mysql://localhost:3306/smart_campus";
    static final String USER = "root";
    static final String PASS = "Harshith@1106.";

    public static void insertStudent(String name, String dept, int year, String email) {

        System.out.println("StudentDAO CALLED");

        String sql = "INSERT INTO student (student_name, department, semester, email) VALUES (?, ?, ?, ?)";

        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(sql)) {

            // ✅ Added line
            System.out.println("Connected DB: " + con.getCatalog());

            ps.setString(1, name);
            ps.setString(2, dept);
            ps.setInt(3, year);
            ps.setString(4, email);

            int rows = ps.executeUpdate();
            System.out.println("Rows inserted: " + rows);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 