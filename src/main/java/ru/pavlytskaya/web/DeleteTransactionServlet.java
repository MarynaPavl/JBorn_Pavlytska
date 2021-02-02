package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.TransactionInformationService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DeleteTransactionServlet extends HttpServlet {
    private TransactionInformationService transactionService;

    public DeleteTransactionServlet(){
        this.transactionService = SpringContext.getContext().getBean(TransactionInformationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");

        Long transactionId = Long.valueOf(req.getParameter("transactionId"));

        transactionService.deleteTransaction(transactionId);
        if(transactionId == null){
            writer.write("Access denied!");
            writer.write(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write("Operation was successfully completed!\n");
        }

    }
}
