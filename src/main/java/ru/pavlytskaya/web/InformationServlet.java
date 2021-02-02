package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.TransactionInformationDTO;
import ru.pavlytskaya.service.TransactionInformationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;

public class InformationServlet extends HttpServlet {
    private TransactionInformationService informationService;

    public InformationServlet(){
        this.informationService = SpringContext.getContext().getBean(TransactionInformationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        Long assignmentId = Long.valueOf(req.getParameter("assignmentId"));
        LocalDate fromDate = LocalDate.parse(req.getParameter("fromDate"));
        LocalDate toData = LocalDate.parse(req.getParameter("toData"));
        List<TransactionInformationDTO> information = informationService.informationModels(assignmentId,fromDate,toData);
        if(information.size() == 0){
            writer.write("No transactions were made during this period.");
        } else {
            writer.write(information.toString());
        }
    }
}
