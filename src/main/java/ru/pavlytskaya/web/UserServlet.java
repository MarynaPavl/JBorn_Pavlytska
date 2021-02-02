package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.AuthService;
import ru.pavlytskaya.service.UserDTO;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

public class UserServlet extends HttpServlet {
    private static AuthService authService;

    public UserServlet() {
        this.authService = SpringContext.getContext().getBean(AuthService.class);
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        HttpSession session = req.getSession();
        Long userId =(Long)session.getAttribute("userId");
        if(userId == null){
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            UserDTO user = authService.getUserById(userId);
            writer.write(user.getFirstName() + " " + user.getLastName());
        }
    }
}
