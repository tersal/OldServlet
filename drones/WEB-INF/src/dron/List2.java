package dron;

import java.sql.Statement;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.*;
import java.util.*;

public class List2 extends HttpServlet {
 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {

  response.setContentType("text/html");
  PrintWriter out = response.getWriter();

  Connection con = null;
  ResultSet rs = null;
  Statement st = null;

  try {

   con = DriverManager.getConnection("jdbc:mysql://localhost:3306/Drones",
      "default", "default");
   st = con.createStatement();
   String query = "";
   if(request.getParameter("select").equals("one")) {
     query = "SELECT * FROM Drones WHERE ID=" + request.getParameter("inicial");
   } else {
     query = "SELECT * FROM Drones WHERE ID>=" + request.getParameter("inicial") +
                    " AND ID<=" + request.getParameter("final");
   }

   rs = st.executeQuery(query);
   out.println("<h4>Resultados de Busqueda</h4>");
   out.println("<table border='1'>");
   while(rs.next()) {
    out.println("<tr>");
    out.println("<td>" + rs.getInt("ID") + "</td>"
               +"<td>" + rs.getString("IP") + "</td>"
               +"<td>" + rs.getString("IMSI") + "</td>"
               +"<td>" + rs.getString("DESCRIPCION") + "</td>");
    out.println("</tr>");
   }
   out.println("</table>");
  } catch (SQLException ex) {
   ex.printStackTrace();
  } finally {
   out.close();
   try {
    if(rs != null) rs.close();
    if(st != null) st.close();
    if(con != null) con.close();
   } catch(SQLException ex) {
    ex.printStackTrace();
   }
  }
 }
}
