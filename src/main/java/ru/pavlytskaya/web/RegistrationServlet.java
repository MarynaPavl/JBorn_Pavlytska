package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.service.UserDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class RegistrationServlet extends HttpServlet {
    private static AuthService authService;

    public RegistrationServlet() {
        this.authService = SpringContext.getContext().getBean(AuthService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserDTO user = authService.registration(firstName, lastName, email, password);
        if (user == null) {
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write("Hello " + user.getFirstName() +" "+ user.getLastName());
            HttpSession session = req.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("Name", user.getFirstName());
        }
    }
}
