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
import java.math.BigDecimal;
import java.time.LocalDate;

public class TransactionCreatServlet extends HttpServlet {
    private TransactionInformationService transactionService;

    public TransactionCreatServlet() {
        this.transactionService = SpringContext.getContext().getBean(TransactionInformationService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");

        Integer accountFrom = Integer.valueOf(req.getParameter("accountFrom"));
        Integer accountTo = Integer.valueOf(req.getParameter("accountTo"));
        BigDecimal sum = new BigDecimal(req.getParameter("sum"));
        LocalDate data = LocalDate.parse(req.getParameter("data"));

        TransactionInformationDTO newTransaction =
                transactionService.transactionInsert(accountFrom, accountTo, sum, data);
        if (newTransaction == null) {
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write("New transaction was creat successfuly: " + newTransaction.toString());
        }
    }
}
