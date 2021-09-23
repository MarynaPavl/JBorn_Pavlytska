package ru.pavlytskaya;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.pavlytskaya.service.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static ru.pavlytskaya.ConsoleHelper.*;


@AllArgsConstructor
@Component
public class TerminalView implements CommandLineRunner {
    private static final ConsoleHelper consoleHelper = new ConsoleHelper();

    private final AuthService auth;
    private final AccountService account;
    private final TransactionInformationService transaction;
    private final TypeService type;


    @Override
    public void run(String... args) throws Exception {
        UserDTO userDTO = new UserDTO();
        writeMessage("If you want to log into an existing account, click 1, \n" +
                "If you want to register click 2");
        int numberOperation = readInt();

        if (numberOperation == 1) {
            ConsoleHelper.writeMessage("Enter email: ");
            String email = readString();

            ConsoleHelper.writeMessage("Enter password: ");
            String password = readString();

            userDTO = auth.auth(email);
            if (userDTO != null) {
                ConsoleHelper.infoPrint(userDTO);
            } else {
                ConsoleHelper.writeMessage("User is not found.");
            }
        }
        if (numberOperation == 2) {
            ConsoleHelper.writeMessage("Pleas enter you name: ");
            String firstName = readString();

            ConsoleHelper.writeMessage("Enter you family name: ");
            String lastName = readString();

            ConsoleHelper.writeMessage("Enter email: ");
            String email = readString();

            ConsoleHelper.writeMessage("Enter password: ");
            String password = readString();

            userDTO = auth.registration(firstName, lastName, email, password);
            ConsoleHelper.infoPrint(userDTO);
        }
        act(userDTO);
    }

    public void act(UserDTO userDTO) throws IOException {
        ConsoleHelper.writeMessage("\nAccounts - click 1. \n" +
                "Transaction - click 2.\n" +
                "Assignment - click 3.\n" +
                "Exit - click 4.");
        int act = readInt();
        if (act == 1) {
            account(userDTO);
        }
        if (act == 2) {
            transaction();
        }
        if (act == 3) {
            assignment();
        }
        if (act == 4) {
            ConsoleHelper.writeMessage("Good luck");
            return;
        }
        act(userDTO);
    }

    public void account(UserDTO userDTO) throws IOException {
        List<AccountDTO> accountDTO = account.accountInformation(userDTO.getId());
        ConsoleHelper.infoPrint(accountDTO);

        ConsoleHelper.writeMessage("If you want to add an account, click 1, \n" +
                "If you want dilate, click 2");
        int act = readInt();
        if (act == 1) {
            ConsoleHelper.writeMessage("Account name: ");
            String nameAccount = readString();

            ConsoleHelper.writeMessage("Sum: ");
            BigDecimal balance = new BigDecimal(readInt());

            ConsoleHelper.writeMessage("Currency: ");
            String currency = readString();
            long id = userDTO.getId();
            account.accountCreat(nameAccount, balance, currency, id);
        }
        if (act == 2) {
            ConsoleHelper.writeMessage("Enter the account number to be deleted: ");
            long id = readInt();
            account.deleteAccount(id);
            ConsoleHelper.infoPrint(accountDTO);
        }
    }

    public void transaction() throws IOException {
        ConsoleHelper.writeMessage("Create transaction - press 1, \n" +
                "Delete transaction - press 2, \n" +
                "Get information about transactions on the assignment for the period of time - press 3");
        int act = readInt();
        if (act == 1) {
            ConsoleHelper.writeMessage("Number account from or 0:");
            long accountFrom = readInt();

            ConsoleHelper.writeMessage("Number account to or 0:");
            long accountTo = readInt();

            ConsoleHelper.writeMessage("Sum: ");
            BigDecimal balance = new BigDecimal(readInt());

            ConsoleHelper.writeMessage("Data yyyy-mm-dd: ");
            LocalDate data = LocalDate.parse(readString());

            ConsoleHelper.writeMessage("Find assignment by first letters");
            String str = readString();
            List<TypeDTO> typeDTO = type.typeInformation(str);
            ConsoleHelper.infoPrint(typeDTO);
            Set<Long> idSet = new HashSet<>();
            Set<Long> assignmentIdSet = assignmentIdSet(idSet);
            TransactionInformationDTO transactionInformationDTO = transaction.transactionInsert(accountFrom, accountTo, balance, data, assignmentIdSet);
            ConsoleHelper.infoPrint(transactionInformationDTO);
        }
        if (act == 2) {
            ConsoleHelper.writeMessage("Enter the transaction number to delete: ");
            long id = readInt();
            transaction.deleteTransaction(id);
        }
        if (act == 3) {
            ConsoleHelper.writeMessage("Find assignment by first letters");
            String str = readString();
            List<TypeDTO> typeDTOS = type.typeInformation(str);
            ConsoleHelper.infoPrint(typeDTOS);

            ConsoleHelper.writeMessage("Enter the assignment  on which you want to get information: ");
            String type = readString();

            ConsoleHelper.writeMessage("Enter from what time yyyy-mm-dd: ");
            LocalDate fromDate = LocalDate.parse(readString());

            ConsoleHelper.writeMessage("Enter until what time yyyy-mm-dd: ");
            LocalDate toDate = LocalDate.parse(readString());

            List<TransactionInformationDTO> transactionInformation = transaction.informationModels(type, fromDate, toDate);
            ConsoleHelper.infoPrint(transactionInformation);
        }
    }

    public Set<Long> assignmentIdSet(Set<Long> idSet) throws IOException {
        ConsoleHelper.writeMessage("Add these assignments to a transaction or create a new assignment." +
                "creat - 1, add these - 2");
        int act = readInt();
        if (act == 1) {
            assignment();
        }
        if (act == 2) {
            ConsoleHelper.writeMessage("Id assignment: ");
            long typeId = readInt();
            idSet.add(typeId);
            ConsoleHelper.writeMessage("Add more - 1, no - 2");
            int a = readInt();
            if (a == 1) {
                assignmentIdSet(idSet);
            }
        }
        return idSet;
    }

    public void assignment() throws IOException {
        ConsoleHelper.writeMessage("Create type - press 1, \n" +
                "Delete type - press 2, \n");
        int act = readInt();
        if (act == 1) {
            ConsoleHelper.writeMessage("Assignment: ");
            String assignment = readString();
            type.typeCreat(assignment);

        }
        if (act == 2) {
            ConsoleHelper.writeMessage("Assignment id: ");
            long id = readInt();
            type.delete(id);
        }
    }
}