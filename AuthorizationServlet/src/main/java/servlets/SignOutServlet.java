package servlets;

import accounts.AccountService;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SignOutServlet extends HttpServlet {
    @Override
    public void doPost(@NotNull HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String sessionId = request.getSession().getId();

        if (AccountService.signOut(sessionId)) {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Signed Out");
            response.setStatus(HttpServletResponse.SC_OK);
        }
        else {
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().println("Unauthorized");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }
}
