package ru.pavlytskaya;

import ru.pavlytskaya.service.*;

import java.util.List;
import java.util.Scanner;


public class Main {

    private static String request(String string) {
        Scanner s = new Scanner(System.in);
        System.out.println(string);
        return s.next();
    }

    public static void main(String[] args) {
        AuthService authService = new AuthService();
        Main main = new Main();
        UserDTO userDTO = null;
        String s1 = request("Если вы хотите войти в имеющийся аккаунт нажмите 1, \n" +
                "Если вы хотите заригистрироваться нажмите 2");
        int n = Integer.parseInt(s1);

        if (n == 1) {
            String email = request("Введите email: ");
            String password = request("Введите password: ");

            userDTO = authService.auth(email, password);
            if (userDTO == null) {
                System.out.println("Пользователь не найден.");
            } else {
                System.out.println(userDTO);
            }

        }
        if (n == 2) {
            String firstName = request("Введите Ваше имя: ");
            String lastName = request("Введите Вашу фамилию: ");
            String email = request("Введите email: ");
            String password = request("Введите password: ");

            userDTO = authService.registration(firstName, lastName, email, password);
            System.out.println(userDTO);
        }
        main.act(userDTO);
    }

    public void act(UserDTO userDTO) {
        Main main = new Main();
        String s2 = request("Счета - нажмити 1. \n" +
                "Назначения - нажмите 2.\n" +
                "Выйти - нажмите 3.");
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
            System.out.println("Всего хорошего!");
            return;
        }

    }

    public void account(UserDTO userDTO) {
        AccountService accountService = new AccountService();
        assert userDTO != null;
        List<AccountDTO> accountDTO = accountService.accountInformation(userDTO.getId());
        System.out.println(accountDTO);

        String s1 = request("Если вы хотите создать или добавить счет, нажмите 1, \n" +
                "Если вы хотите удалить счет, нажмите 2");
        int m = Integer.parseInt(s1);
        if (m == 1) {
            String nameAccount = request("Название счета: ");
            double balance = Double.parseDouble(request("Сумма: "));
            String currency = request("Валюта: ");
            long userID = Long.parseLong(request("Введити ваш id: "));
            List<AccountDTO> account = accountService.accountCreat(nameAccount, balance, currency, userID);
            System.out.println(account);
        }
        if (m == 2) {
            long id = Long.parseLong(request("Введите номер счета для удаления: "));
            int row = accountService.deleteAccount(id);
            if (row == 1) {
                System.out.println("Операция прошла успешно.");
            }
            if (row == 0) {
                System.out.println("Ошибка!");
            }

        }
        System.out.println(accountDTO);
    }

    public void assignment() {
        TypeService typeService = new TypeService();
        String s2 = request("Создать назначение транзакции - нажмите 1," +
                "Редактировать назначение транзакции - нажмите 2," +
                "Удалить назначение транзакции - нажмите 3");
        int p = Integer.parseInt(s2);
        if (p == 1) {
            String assignment = request("Назначение: ");
            TypeDTO typeDTO = typeService.typeCreat(assignment);
            System.out.println(typeDTO);
        }
        if (p == 2) {
            long id = Long.parseLong(request("Введите номер назначения для внесения изменений: "));
            String assignment = request("Введите новое назначение: ");
            int row = typeService.editType(id, assignment);
            if (row == 1) {
                System.out.println("Операция прошла успешно.");
            }
            if (row == 0) {
                System.out.println("Ошибка!");
            }

        }
        if (p == 3) {
            long id = Long.parseLong(request("Введите номер назначения для удаления: "));
            int row = typeService.deleteAccount(id);
            if (row == 1) {
                System.out.println("Операция прошла успешно.");
            }
            if (row == 0) {
                System.out.println("Ошибка!");
            }
        }
    }
}
