package servlets;

import accounts.AccountService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public class SignInServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String
                login = request.getParameter("login"),
                password = request.getParameter("password"),
                sessionId = request.getSession().getId();

        if (Objects.equals(login, "") || Objects.equals(password, "")
                || login == null || password == null) {
            response.setContentType("text/http;charset=UTF-8");
            response.getWriter().println("Bad request");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (AccountService.signIn(sessionId, login, password)) {
            response.setContentType("text/http;charset=UTF-8");
            response.getWriter().println("Authorized: " + login);
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setContentType("text/http;charset=UTF-8");
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
