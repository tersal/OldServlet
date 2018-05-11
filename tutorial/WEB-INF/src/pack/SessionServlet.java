package pack;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Date;

public class SessionServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException {

    response.setContentType("text/html;charset=UTF-8");

    PrintWriter out = response.getWriter();

    HttpSession session = request.getSession();
    Integer accessCount;

    synchronized(session) {
      accessCount = (Integer)session.getAttribute("accessCount");
      if ( accessCount == null) {
         accessCount = 0;
      } else {
         accessCount = new Integer(accessCount + 1);
      }
      session.setAttribute("accessCount", accessCount);
     }

     try {
       out.println("<!DOCTYPE html>");
       out.println("<html>");
       out.println("<head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />");
       out.println("<title>Session Test Servlet</title>");
       out.println("<h2>You have access this site " + accessCount + " times in this session.</h2>");
       out.println("<p>(Session D is " + session.getId() + ")</p>");

       out.println("<p>(Session creation time is " +
              new Date(session.getCreationTime()) + ")</p>");
       out.println("<p>(Session last access time is " +
              new Date(session.getLastAccessedTime()) + ")</p>");
       out.println("<p>(Session mas inactive interval is " +
              session.getMaxInactiveInterval() + " seconds)</p>");

       out.println("<p><a href='" + request.getRequestURI() + "'>Refresh</a>");
       out.println("<p><a href='" + response.encodeURL(request.getRequestURI()) +
                    "'>Refresh with URL rewriting</a>");
       out.println("</body></html>");
     } finally {
       out.close();
     }
   }
}
