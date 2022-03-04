package servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AllRequestsServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//        Map<String, Object> pageVariables = new HashMap<>();

//        pageVariables.put("value", getKeyParameter(request));

        response.getWriter().println(getKeyParameter(request));

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    public static String getKeyParameter(HttpServletRequest request) {
        String paramValue = request.getParameter("key");

        if (paramValue == null) return "";
        return paramValue;
    }
}
