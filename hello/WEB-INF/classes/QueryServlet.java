import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class QueryServlet extends HttpServlet {

   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response)
               throws ServletException, IOException {

     response.setContentType("text/html");
     PrintWriter out = response.getWriter();

     Connection con = null;
     Statement st = null;

     try {

      con = DriverManager.getConnection(
         "jdbc:mysql://localhost:3306/ebookshop", "default", "default");
      st = con.createStatement();
      String sqlStr = "SELECT * FROM books WHERE author = "
                    + "'" + request.getParameter("author") + "'"
                    + "AND qty > 0 ORDER BY PRICE DESC";

      out.println("<!DOCTYPE html>");
      out.println("<html><head><title>Query Response</title></head><body>");
      out.println("<h3>Thank you for your query.</h3>");
      out.println("<p>Your query was: " + sqlStr +"</p>");

      ResultSet rset = st.executeQuery(sqlStr);
      int count = 0;

      while(rset.next()) {
        out.println("<p>" + rset.getString("author")
                   + ", " + rset.getString("title")
                   + ", $" + rset.getDouble("price") + "</p>");
        count++;
       }
       out.println("<p>==== "+ count + " records found =====</p>");
       out.println("</body></html>");
     } catch (SQLException ex) {
       ex.printStackTrace();
     } finally {
       out.close();
       try {
         if (st != null) st.close();
         if (con != null) con.close();
       } catch (SQLException ex) {
         ex.printStackTrace();
       }
    }
  }
}
