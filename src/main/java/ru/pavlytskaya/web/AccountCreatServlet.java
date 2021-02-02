package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

public class AccountCreatServlet extends HttpServlet {
    private static AccountService accountService;
    public AccountCreatServlet(){
        this.accountService = SpringContext.getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        String nameAccount = req.getParameter("nameAccount");
        BigDecimal balance = new BigDecimal(req.getParameter("balance"));
        String currency = req.getParameter("currency");
        Long userID = (Long)session.getAttribute("userId");

        List<AccountDTO> account = accountService.accountCreat(nameAccount, balance, currency, userID);
        if(account == null){
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write("New account was creat successfully: " + account.toString() +"\n");
            List<AccountDTO> accounts = accountService.accountInformation(userID);
            writer.write(session.getAttribute("Name").toString() +
                    " we provide an updated list of your accounts: " + accounts.toString());
        }
    }
}
