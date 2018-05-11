import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class Edit extends HttpServlet {

 @Override
 public void doPost(HttpServletRequest request, HttpServletResponse response)
  throws IOException, ServletException {

  response.setContentType("text/html");
  PrintWriter out = response.getWriter();

  if (request.getParameter("user").equals("fili") &&
      request.getParameter("pass").equals("jurguen")) {
  out.println("

}
