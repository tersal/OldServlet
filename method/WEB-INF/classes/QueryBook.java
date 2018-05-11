import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class QueryBook extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
       throws ServletException, IOException {

   response.setContentType("text/html");
   PrintWriter out = response.getWriter();

   Connection con = null;
   Statement sta = null;

   try {
     con = DriverManager.getConnection("jdbc:mysql://localhost:3306/library",
           request.getParameter("username"),  request.getParameter("password"));
     sta = con.createStatement();

     String statement = "SELECT Name, Title FROM Authors, Books WHERE Name = "
                        + "'" + request.getParameter("author") + "'"
                        + "AND Authors.Id = Books.AuthorId";

     out.println("<!DOCTYPE html>");
     out.println("<html>");
     out.println("<head><title>Query Response</title></head>");
     out.println("<h3>Thank you for your query</h3>");
     out.println("<br />");

     ResultSet rset = sta.executeQuery(statement);
     int count = 0;

     while(rset.next()) {
       out.println("<p>" + rset.getString("Name") + ", "
                   + rset.getString("Title") + "</p>");
       count++;
     }
     out.println("<p>=======" + count + " records found =======</p>");
     out.println("</body></html>");
   } catch (SQLException ex) {
     ex.printStackTrace();
   } finally {
     out.close();
     try {
        if (sta != null) sta.close();
        if (con != null) con.close();
     } catch (SQLException ex) {
        ex.printStackTrace();
     }
   }
 }
}
