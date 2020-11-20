package ru.pavlytskaya;

import ru.pavlytskaya.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import static ru.pavlytskaya.service.ServiceFactory.*;


public class Main {

    private static String request(String string) {
        Scanner s = new Scanner(System.in);
        System.out.println(string);
        return s.next();
    }

    public static void main(String[] args) {
        Main main = new Main();
        UserDTO userDTO = null;
        main.login(userDTO);

    }

    public void login(UserDTO userDTO) {
        AuthService authService = getAuthService();
        Main main = new Main();
        String s1 = request("If you want to log into an existing account, click 1, \n" +
                "If you want to register click 2");
        int n = Integer.parseInt(s1);

        if (n == 1) {
            String email = request("Enter email: ");
            String password = request("Enter password: ");

            userDTO = authService.auth(email, password);
            if (userDTO == null) {
                System.out.println("User is not found.");
                main.login(userDTO);
            } else {
                System.out.println(userDTO);
            }

        }
        if (n == 2) {
            String firstName = request("Pleas enter you name: ");
            String lastName = request("Enter you family name: ");
            String email = request("Enter email: ");
            String password = request("Enter password: ");

            userDTO = authService.registration(firstName, lastName, email, password);
            System.out.println(userDTO);
        }
        main.act(userDTO);
    }

    public void act(UserDTO userDTO) {
        Main main = new Main();
        String s2 = request("\nAccounts - click 1. \n" +
                "Assignment - click 2.\n" +
                "Exit - click 3.");
        int q = Integer.parseInt(s2);
        if (q == 1) {
            assert userDTO != null;
            main.account(userDTO);
            main.act(userDTO);
        }
        if (q == 2) {
            main.assignment();
            main.act(userDTO);
        }
        if (q == 3) {
            System.out.println("Good luck");
            return;
        }

    }

    public void account(UserDTO userDTO) {
        AccountService accountService = getAccountService();
        assert userDTO != null;
        List<AccountDTO> accountDTO = accountService.accountInformation(userDTO.getId());
        System.out.println(accountDTO);

        String s1 = request("\nIf you want to create or add an account, click 1, \n" +
                "If you want dilate, click 2");
        int m = Integer.parseInt(s1);
        if (m == 1) {
            String nameAccount = request("Account name: ");
            double balance = Double.parseDouble(request("Sum: "));
            String currency = request("Currency: ");
            long userID = Long.parseLong(request("Enter you id: "));
            List<AccountDTO> account = accountService.accountCreat(nameAccount, balance, currency, userID);
            System.out.println(account);
        }
        if (m == 2) {
            long id = Long.parseLong(request("Enter the account number to be deleted: "));
            int row = accountService.deleteAccount(id);
            if (row == 1) {
                System.out.println("Operation was successfully completed.");
            }
            if (row == 0) {
                System.out.println("Mistake!");
            }

        }
        System.out.println(accountDTO);
    }

    public void assignment() {
        TypeService typeService = getTypeService();
        InformationService informationService = getInformationService();
        System.out.println(typeService.typeInformation());
        String s2 = request("\nCreate transaction assignment - press 1,\n" +
                "Edit transaction assignment - press 2, \n" +
                "Get information about transactions on the assignment for the period of time - press 3, \n" +
                "Delete transaction assignment - press 4 \n");
        int p = Integer.parseInt(s2);
        if (p == 1) {
            String assignment = request("Assignment: ");
            TypeDTO typeDTO = typeService.typeCreat(assignment);
            System.out.println(typeDTO);
        }
        if (p == 2) {
            long id = Long.parseLong(request("Enter the assignment number to make changes: "));
            String assignment = request("Enter a new assignment: ");
            int row = typeService.editType(id, assignment);
            if (row == 1) {
                System.out.println("Operation was successfully completed.");
            }
            if (row == 0) {
                System.out.println("Mistake!");
            }

        }
        if (p == 3) {
            long assignmentId = Long.parseLong(request("Enter the assignment number on which you want to get information: "));
            LocalDate fromDate = LocalDate.parse(request("Enter from what time: "));
            LocalDate toDate = LocalDate.parse(request("Enter until what time: "));
            List<InformationDTO> informationDTOList = informationService.informationModels(assignmentId, fromDate, toDate);
            System.out.println(informationDTOList);
        }
        if (p == 4) {
            long id = Long.parseLong(request("Enter the assignment number to delete: "));
            int row = typeService.deleteType(id);
            if (row == 1) {
                System.out.println("Operation was successfully completed.");
            }
            if (row == 0) {
                System.out.println("Mistake!");
            }
        }
    }
}
