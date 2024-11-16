package ru.kpfu.itis;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        boolean isAuthenticated = authenticateUser (login, password);
        UserManager.logAuthAttempt(login, isAuthenticated);

        if (isAuthenticated) {
            resp.sendRedirect("/weather");
        } else {
            resp.sendRedirect("/login?error=true");
        }
    }

    private boolean authenticateUser (String login, String password) {
        return UserManager.validateUser(login, password);
    }

}
