package pack;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;

public class EchoServlet extends HttpServlet {

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException {

    response.setContentType("text/html");
    PrintWriter out = response.getWriter();

    try {
      out.println("<!DOCTYPE html>");
      out.println("<html>");
      out.println("<head>");
      out.println("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
      out.println("<title>Echo Servlet</title>");
      out.println("</head>");

      out.println("<body>");
      out.println("<h2>You have enter</h2>");

      String data = request.getParameter("username");

      if (data == null || (data = htmlFilter(data.trim())).length() == 0) {
         out.println("<p>Name: MISSING</p>");
      } else {
         out.println("<p>Name: " + data +"</p>");
      }

      data = request.getParameter("password");

      if (data == null || (data = htmlFilter(data.trim())).length() ==0) {
        out.println("<p>Name: MISSING</p>");
      } else {
        out.println("<p>Name: " + data +"</p>");
      }

      data = request.getParameter("gender");

      if(data == null) {
        out.println("<p>Gender: MISSING</p>");
      } else if(data.equals("m")) {
        out.println("<p>Gender: Male</p>");
      } else {
        out.println("<p>Gender: Female</p>");
      }

      data = request.getParameter("age");

      if (data == null) {
        out.println("<p>Age: MISSING</p>");
      } else if(data.equals("1")) {
        out.println("<p>Age: &lt; 1 year old</p>");
      } else if(data.equals("99")) {
        out.println("<p>Age: 1 to 99 years old</p>");
      } else {
        out.println("<p>Age: &gt; 99 years old</p>");
      }

      String[] languages = request.getParameterValues("language");

      if (languages == null || languages.length == 0) {
        out.println("<p>Languages: NONE</p>");
      } else {
        out.println("<p>Languages: ");
        for (String language : languages) {
           if (language.equals("c")) {
             out.println("C/C++ ");
           } else if (language.equals("cs")) {
             out.println("C# ");
           } else if (language.equals("java")) {
             out.println("Java ");
           }
         }
         out.println("</p>");
      }

      data = request.getParameter("instruction");

      if (data == null
          || (data = htmlFilter(data.trim())).length() == 0
          || data.equals("Enter your instruction here...")) {
        out.println("<p>Instruction: NONE</p>");
      } else {
        out.println("<p>Instruction: " + data +"</p>");
      }

      data = request.getParameter("secret");
      out.println("<p>Secret: " + data + "</p>");

      Enumeration names = request.getParameterNames();
      out.println("<p>Request Parameter Names are: ");

      if (names.hasMoreElements()) {
       out.println(htmlFilter(names.nextElement().toString()));
      }
      do {
        out.println(", " + htmlFilter(names.nextElement().toString()));
      } while (names.hasMoreElements());
      out.println("</p>");

      out.println("<a href='form_input.html'>BACK</a>");
      out.println("</body></html>");
    } finally {
      out.close();
    }
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response)
     throws IOException, ServletException {
    doGet(request, response);
  }

  private static String htmlFilter(String message) {
    if (message == null) return null;
    int len = message.length();
    StringBuffer result = new StringBuffer(len + 20);
    char aChar;

    for (int i = 0; i < len; i++) {
      aChar = message.charAt(i);
      switch (aChar) {
        case '<': result.append("&lt;"); break;
        case '>': result.append("&gt;"); break;
        case '&': result.append("&amp;"); break;
        case '"': result.append("&quot;"); break;
        default: result.append(aChar);
      }
    }
    return (result.toString());
  }
}
