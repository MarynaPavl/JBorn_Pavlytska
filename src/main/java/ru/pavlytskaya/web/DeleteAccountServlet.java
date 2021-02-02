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
import java.util.List;

public class DeleteAccountServlet extends HttpServlet {
    private static AccountService accountService;

    public DeleteAccountServlet(){
        this.accountService = SpringContext.getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        resp.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession();

        Long accountId = Long.valueOf(req.getParameter("accountId"));
        Long userID = (Long)session.getAttribute("userId");

        accountService.deleteAccount(accountId);
        if (accountId == null){
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        } else {
            writer.write("Operation was successfully completed!\n");
            List<AccountDTO> accounts = accountService.accountInformation(userID);
            writer.write(session.getAttribute("Name").toString() +
                    " we provide an updated list of your accounts: \n" + accounts.toString());
        }

    }
}
