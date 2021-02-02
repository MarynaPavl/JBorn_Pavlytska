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
import java.util.List;

public class TypeInformationServlet extends HttpServlet {
    private TypeService typeService;

    public TypeInformationServlet(){
        this.typeService = SpringContext.getContext().getBean(TypeService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        List<TypeDTO> types = typeService.typeInformation();
        if (types.size() == 0){
            writer.write("There are no transaction types, create your own transaction types.");
        } else {
            writer.write(types.toString());
        }

    }
}
