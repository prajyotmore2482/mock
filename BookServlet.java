import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/books")
public class BookServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>Book List</title></head><body>");
        out.println("<h2>Book List</h2>");
        
        // JDBC URL, username, and password of MySQL server
        String url = "jdbc:mysql://localhost:3306/ebookshop";
        String user = "root";
        String password = "student";
        
        try {
            // Load JDBC driver
            Class.forName("com.mysql.jdbc.Driver");
            // Connect to the database
            Connection conn = DriverManager.getConnection(url, user, password);
            // Create a statement
            Statement stmt = conn.createStatement();
            // Execute SQL query
            ResultSet rs = stmt.executeQuery("SELECT * FROM books");
            // Display the result set
            out.println("<table border='1'><tr><th>ID</th><th>Title</th><th>Author</th><th>Price</th><th>Quantity</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getInt("book_id") + "</td><td>" + rs.getString("book_title") + "</td><td>"
                        + rs.getString("book_author") + "</td><td>" + rs.getDouble("book_price") + "</td><td>"
                        + rs.getInt("quantity") + "</td></tr>");
            }
            out.println("</table>");
            // Close resources
            rs.close();
            stmt.close();
            conn.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        
        out.println("</body></html>");
    }
}

