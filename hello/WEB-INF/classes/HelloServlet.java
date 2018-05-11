import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class HelloServlet extends HttpServlet {

   @Override
   public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

     response.setContentType("text/html");

     PrintWriter out = response.getWriter();

     try {
       out.println("<html>");
       out.println("<head><title>Hello, World</title></head>");
       out.println("<body>");
       out.println("<h1>Hello, world!</h1>");

       out.println("<p>Request URI: " + request.getRequestURI() + "</p>");
       out.println("<p>Protocol: " + request.getProtocol() + "</p>");
       out.println("<p>PathInfo: " + request.getPathInfo() + "</p>");
       out.println("<p>Remote Address: " + request.getRemoteAddr() + "</p>");

       out.println("<p>A Random Number: <strong>" + Math.random() + "</strong></p>");
       out.println("</body></html>");
     } finally {
        out.close();
     }
  }
}
