package main;
import java.sql.*;


public class FeedbackDAO {

    static final String URL = "jdbc:mysql://localhost:3306/smart_campus";
    static final String USER = "root";
    static final String PASS = "Harshith@1106.";

    public static void submitFeedback(int sid, int cid, int rating, String comments) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = con.prepareStatement(
                     "INSERT INTO feedback(student_id, faculty_id, course_id, rating, comments) VALUES (?, ?, ?, ?, ?)")) {

            ps.setInt(1, sid);
            ps.setInt(2, 1); 
            ps.setInt(3, cid);
            ps.setInt(4, rating);
            ps.setString(5, comments);

            ps.executeUpdate();
            System.out.println("Feedback submitted successfully");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 