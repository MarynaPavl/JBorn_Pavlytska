package ru.pavlytskaya.web;

import ru.pavlytskaya.SpringContext;
import ru.pavlytskaya.service.AccountDTO;
import ru.pavlytskaya.service.AccountService;
import ru.pavlytskaya.service.UserDTO;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class AccountInformationServlet extends HttpServlet {
    private static AccountService accountService;

    public AccountInformationServlet(){
        this.accountService = SpringContext.getContext().getBean(AccountService.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();

        HttpSession session = req.getSession();
        Long userId =(Long)session.getAttribute("userId");
        String user = (String) session.getAttribute("Name");
        if(userId == null){
            writer.write("Access denied!");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }else {
            List<AccountDTO> accounts = accountService.accountInformation(userId);
            writer.write( accounts.toString());
        }
    }
}
