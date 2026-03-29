package main;
import java.sql.*;



public class AttendanceDAO {

    static final String URL = "jdbc:mysql://localhost:3306/smart_campus";
    static final String USER = "root";
    static final String PASS = "Harshith@1106.";

    public static void markAttendance(int sid, int cid, String date, String status) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO attendance(student_id, course_id, faculty_id, date, status) VALUES (?, ?, ?, ?, ?)")) {

            ps.setInt(1, sid);
            ps.setInt(2, cid);
            ps.setInt(3, 1);  
            ps.setString(4, date);
            ps.setString(5, status);

            ps.executeUpdate();
            System.out.println("Attendance marked successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 
