package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.TypeDTO;
import ru.pavlytskaya.service.TypeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TypeCreatServlet extends HttpServlet {
    private TypeService typeService;

    public TypeCreatServlet() {
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");

        String assignment = req.getParameter("assignment");
        TypeDTO newType = typeService.typeCreat(assignment);
        if (assignment == null) {
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write("New type was creat successfully: " +
                    assignment.toString() + "\n" + typeService.typeInformation());
        }
    }
}
