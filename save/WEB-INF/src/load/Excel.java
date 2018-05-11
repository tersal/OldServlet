package load;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.InputStream.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;

public class Excel extends HttpServlet {

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException {

    response.setContentType("text/html");
//    PrintWriter out = response.getWriter();
    String data = request.getParameter("data");
    int ID = Integer.parseInt(request.getParameter("id"));
    String IMSI = request.getParameter("imsi");
    String ICCID = request.getParameter("iccid");
    String IMEI = request.getParameter("imei");
    int i = 0, idc=0, Rx=0, Tx=0;
    int hour=0;
try {
    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
    Document doc = dBuilder.parse(new ByteArrayInputStream(data.getBytes("utf-8")));

    doc.getDocumentElement().normalize();
    NodeList nList = doc.getElementsByTagName("hour");

    Connection con = null;
    PreparedStatement st = null;
    ResultSet rs = null;

    String url = "jdbc:mysql://localhost:3306/Consumo";
    String user = "root";
    String password = "saavedra";

    try {
      con = DriverManager.getConnection(url, user, password);
      con.setAutoCommit(false);
      st = con.prepareStatement("INSERT INTO Drone("+
           "Id, IMSI, ICCID, IMEI) " +
           "SELECT * FROM (SELECT ?, ?, ?, ?) AS tmp " +
           "WHERE NOT EXISTS (" +
               "SELECT Id FROM Drone WHERE Id = ?" +
           ") LIMIT 1");
      st.setInt(1, ID);
      st.setString(2, IMSI);
      st.setString(3, ICCID);
      st.setString(4, IMEI);
      st.setInt(5, ID);
      st.executeUpdate();
      st = con.prepareStatement("SELECT Idh FROM Horas ORDER BY Idh DESC LIMIT 1");      
      rs = st.executeQuery();
      if (rs.next()) {
        idc = rs.getInt(1) + 1;
      } else {
        idc = 1;
      }
      st = con.prepareStatement("INSERT INTO Horas(" +
           "Zero, One, Two, Three, Four," +
           "Five, Six, Seven, Eight, Nine, Ten, Eleven, Twelve, Thirteen, Fourteen," +
           "Fifteen, Sixteen, Seventeen, Eighteen, Nineteen, Twenty, Twenty_one," +
           "Twenty_two, Twenty_three) VALUES (?, ?, ?, ?, ?," +
           "?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
      Element elemento;
      int counter;
      for(i=0; i<24;i++) 
      {
        counter=0;
        for (int temp=0; temp < nList.getLength(); temp++)
        {
          Node nNode = nList.item(temp);
          if(nNode.getNodeType() == Node.ELEMENT_NODE)
          {
            elemento = (Element) nNode;
            if (Integer.parseInt(elemento.getAttribute("id")) == i)
            {
              Rx += Integer.parseInt(elemento.getElementsByTagName("rx").item(0).getTextContent());
              Tx += Integer.parseInt(elemento.getElementsByTagName("tx").item(0).getTextContent());
              if (Integer.parseInt(elemento.getElementsByTagName("tx").item(0).getTextContent()) > 20000)
              {
                hour+=1;
              }
              counter = Integer.parseInt(elemento.getElementsByTagName("rx").item(0).getTextContent());
              counter += Integer.parseInt(elemento.getElementsByTagName("tx").item(0).getTextContent());
            }
          }        
        }
       st.setInt((i+1), counter);
      } 
      st.executeUpdate();
      st = con.prepareStatement("INSERT INTO Total(" +
           "Idh, ID_Drone, Fecha, ConsumoTx, ConsumoRx, Total, Tiempo) VALUES (?, ?, NOW(), ?, ?, ?, ?)");
      st.setInt(1, idc);
      st.setInt(2, ID);
      st.setInt(3, Tx);
      st.setInt(4, Rx);
      st.setInt(5, (Tx+Rx));
      st.setInt(6, hour);
      st.executeUpdate();
      con.commit();
    } catch (SQLException ex) {
      if (con != null) {
        try {
           con.rollback();
        } catch (SQLException ex1) {
           ex1.printStackTrace();
        }
      }
      ex.printStackTrace();
   } finally {
      try {
        if (st != null) {
          st.close();
        }
        if (con != null) {
          con.close();
        }
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
    }
} catch (Exception e) {
   e.printStackTrace();
}
  }
}
