package servlets;

import accounts.AccountService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class SignInServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String
                login = request.getParameter("login"),
                password = request.getParameter("password"),
                sessionId = request.getSession().getId();

        if (AccountService.signIn(sessionId, login, password)) {
            response.setContentType("text/http;charset=UTF-8");
//            response.setContentLength(128);
            response.getWriter().println("Authorized");
        }
    }
}
