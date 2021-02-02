package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.TransactionToCategoryDTO;
import ru.pavlytskaya.service.TransactionToCategoryService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TransactionToCategoryInsertServlet extends HttpServlet {
    private TransactionToCategoryService toCategoryService;

    public TransactionToCategoryInsertServlet(){
        this.toCategoryService = SpringContext.getContext().getBean(TransactionToCategoryService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        Long transactionId = Long.valueOf(req.getParameter("transactionId"));
        Long typeId = Long.valueOf(req.getParameter("typeId"));

        TransactionToCategoryDTO toCategoryDTO = toCategoryService.transactionToCategoryInsert(transactionId, typeId);
        if(toCategoryDTO == null){
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write(toCategoryDTO.toString());
        }
    }
}
