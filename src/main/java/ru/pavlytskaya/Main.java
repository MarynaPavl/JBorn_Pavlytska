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
        String s = request("If you want to log into an existing account, click 1, \n" +
                "If you want to register click 2");
        int n = Integer.parseInt(s);

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
        String s = request("\nAccounts - click 1. \n" +
                "Transaction - click 2.\n" +
                "Assignment - click 3.\n" +
                "Exit - click 4.");
        int q = Integer.parseInt(s);
        if (q == 1) {
            assert userDTO != null;
            main.account(userDTO);
            main.act(userDTO);
        }
        if (q == 2){
            main.transaction();
            main.act(userDTO);
        }
        if (q == 3) {
            main.assignment();
            main.act(userDTO);
        }
        if (q == 4) {
            System.out.println("Good luck");
            return;
        }

    }

    public void account(UserDTO userDTO) {
        AccountService accountService = getAccountService();
        assert userDTO != null;
        List<AccountDTO> accountDTO = accountService.accountInformation(userDTO.getId());
        System.out.println(accountDTO);

        String s = request("\nIf you want to create or add an account, click 1, \n" +
                "If you want dilate, click 2");
        int m = Integer.parseInt(s);
        if (m == 1) {
            String nameAccount = request("Account name: ");
            double balance = Double.parseDouble(request("Sum: "));
            String currency = request("Currency: ");
            long userID = userDTO.getId();
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

    }
    public void transaction(){
        TransactionInformationService transactionInformationService = getInformationService();
        TypeService typeService = getTypeService();
        String s = request("Create transaction - press 1, \n" +
                "Delete transaction - press 2, \n" +
                "Get information about transactions on the assignment for the period of time - press 3, \n");
        int p = Integer.parseInt(s);
        if(p == 1){
            Integer accountFrom = Integer.valueOf(request("Number account from or 0:"));
            Integer accountTo = Integer.valueOf(request("Number account to or 0:"));
            double sum = Double.parseDouble(request("Sum: "));
            LocalDate data = LocalDate.parse(request("Data yyyy-mm-dd: "));
            TransactionInformationDTO transactionInformationDTO = transactionInformationService.transactionInsert(accountFrom,  accountTo, sum, data);
            System.out.println(typeService.typeInformation());
            String q = request("Add these assignments to a transaction or create a new assignment." +
                    "creat - 1, add these - 2");
            int a = Integer.parseInt(q);
            if(a == 1){
                Main main = new Main();
                main.assignment();
                System.out.println(typeService.typeInformation());
            }
            Long transactionId = transactionInformationDTO.getId();
            Long typeId = Long.valueOf(request("Id assignment: "));
            TransactionToCategoryService toCategoryService = getTransactionToCategoryService();
            toCategoryService.transactionToCategoryInsert(transactionId, typeId);
            System.out.println(transactionInformationDTO);
        }
        if(p == 2){
            long id = Long.parseLong(request("Enter the transaction number to delete: "));
            int row = transactionInformationService.deleteTransaction(id);
            if (row == 1) {
                System.out.println("Operation was successfully completed.");
            }
            if (row == 0) {
                System.out.println("Mistake!");
            }
        }
        if(p == 3){
            System.out.println(typeService.typeInformation());
            long assignmentId = Long.parseLong(request("Enter the assignment number on which you want to get information: "));
            LocalDate fromDate = LocalDate.parse(request("Enter from what time yyyy-mm-dd: "));
            LocalDate toDate = LocalDate.parse(request("Enter until what time yyyy-mm-dd: "));
            List<TransactionInformationDTO> transactionInformationDTOList = transactionInformationService.informationModels(assignmentId, fromDate, toDate);
            System.out.println(transactionInformationDTOList);
        }

    }

    public void assignment() {
        TypeService typeService = getTypeService();
        System.out.println(typeService.typeInformation());

        String assignment = request("Assignment: ");
        TypeDTO typeDTO = typeService.typeCreat(assignment);
        System.out.println(typeDTO);

    }
}
