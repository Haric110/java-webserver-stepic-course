package servlets;


import accounts.AccountService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

public final class SignUpServlet extends HttpServlet {
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String
                login = request.getParameter("login"),
                password = request.getParameter("password");

        if (Objects.equals(login, "") || Objects.equals(password, "")
                || login == null || password == null) {
            response.setContentType("text/http;charset=UTF-8");
            response.getWriter().println("Bad request");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        if (AccountService.signUp(login, password)) {
            response.setContentType("text/http;charset=UTF-8");
            response.getWriter().println(String.format("A new user has been registered: %s", login));
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setContentType("text/http;charset=UTF-8");
            response.getWriter().println(String.format("This user is already registered: %s", login));
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
