package dron;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class List extends HttpServlet {

 @Override
 public void doGet(HttpServletRequest request, HttpServletResponse response)
   throws IOException, ServletException {

  response.setContentType("text/html");
  PrintWriter out = response.getWriter();

  Connection con = null;
  Statement st = null;
  ResultSet rs = null;

  try {

   con = DriverManager.getConnection(
      "jdbc:mysql://localhost:3306/Drones", "default", "default");
   st = con.createStatement();
   String query="";
   if (request.getParameter("select").equals("one")) {
     query = "SELECT * FROM Drones WHERE ID =" + request.getParameter("inicial");
   }
   if (request.getParameter("select").equals("many")) {
     query = "SELECT * FROM Drones WHERE ID>=" + request.getParameter("inicial") + 
             " AND ID<=" + request.getParameter("final");
   }

   rs = st.executeQuery(query);

   out.println("<!DOCTYPE html>");
   out.println("<html><head><title>Resultados de Busqueda</title></head><body>");
   out.println("<h3>Resultados de la busqueda</h3>");
   out.println("<table border='1'>");
   while(rs.next()) {
    out.println("<tr>");
    out.println("<td>" + rs.getInt("ID") + "</td>"
                + "<td>" + rs.getString("IP") + "</td>"
                + "<td>" + rs.getString("IMSI") + "</td>");
    out.println("</tr>");
   }
   out.println("</table>");
   out.println("</body></html>");
  } catch(SQLException ex) {
    ex.printStackTrace();
  } finally {
    out.close();
    try {
     if(rs != null) rs.close();
     if(st != null) st.close();
     if(con != null) con.close();
    } catch (SQLException ex) {
      ex.printStackTrace();
    }
  }
 }
}
