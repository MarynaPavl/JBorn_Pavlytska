package ru.pavlytskaya;

import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;
import java.util.Scanner;


public class Main{

    private static String requestString(String string) {
        Scanner s = new Scanner(System.in);
        System.out.println(string);
        return s.nextLine();
    }

    public static void main(String[] args) {
        Main m = new Main();
        try (Connection conn = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "postgres")) {
        long userID = 0;
        long accountID = 0;
            System.out.println("What operation would you like to perform?" + "\n" +
                    " If you your hav account, press 1!" + "\n" +
                    " If you want register, press 2!");
            String s = requestString("Press number");
            int n = Integer.parseInt(s);

            if (n == 1) {
                String email = requestString("Email: ");
                String password = requestString("Password: ");

                userID = m.login(conn, email, DigestUtils.md5Hex(password));
                m.listOfAccount(conn, userID);
            }

            if (n == 2) {
                String first = requestString("First name: ");
                String last = requestString("Last name: ");
                String email = requestString("Email: ");
                String password = requestString("Password: ");
                userID = m.createUser(conn, first, last, email, password);
                m.listOfAccount(conn, userID);
            }
            System.out.println("If you want to add account? press 1. " + "\n" +
                               "If you want to delete account no press 2");
            String st = requestString("Press number");
            int e = Integer.parseInt(st);

            if (e == 1) {
                String name_account = requestString("Name_account: ");
                double balance = Double.parseDouble(requestString("Balance"));
                String currency = requestString("Currency");
                m.creatAccount(conn, name_account, balance, currency, userID);
                m.listOfAccount(conn, userID);
            }
            if (e == 2){
                String string = requestString("What number account you want delete?");
                accountID = Long.parseLong(string);
                m.deleteAccount(conn, accountID);
            }

        } catch (SQLException throwable) {
            System.out.println("Failed request");
            throwable.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private long createUser(Connection conn, String first, String last, String email, String password) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("INSERT INTO service_users (first_name, last_name, email_address, password) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        long userID = 0;
        stat.setString(1, first);
        stat.setString(2, last);
        stat.setString(3, email);
        stat.setString(4, DigestUtils.md5Hex(password));
        stat.executeUpdate();

        ResultSet rs = stat.getGeneratedKeys();
        if(rs.next()){
           userID = rs.getLong("id");
        }
        stat.close();
        return userID;
    }



    private long login(Connection conn, String email, String password) throws Exception {
        PreparedStatement stat = conn.prepareStatement("select *from service_users where email_address = ? and password = ?");
        stat.setString(1, email);
        stat.setString(2, password);

        ResultSet rs = stat.executeQuery();
        long userID = 0;
        if (rs.next()) {
            System.out.println("Hello " + rs.getString("first_name") + " " + rs.getString("last_name"));
            userID = rs.getLong("id");

        } else {
            throw new Exception("Access denied!");
        }

        stat.close();
        return userID;
    }

    private void listOfAccount(Connection conn, long userID) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("select * from account where user_id = ?");
        stat.setLong(1, userID);
        ResultSet rs = stat.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getLong("id") + ": " +
                               rs.getString("name_account") + " - " +
                               rs.getBigDecimal("balance") + " " +
                               rs.getString("currency"));
        }
        stat.close();
    }
    private long creatAccount(Connection conn, String name_account, double balance, String currency, long user_id) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("INSERT into account (name_account, balance, currency, user_id) values (?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);
        stat.setString(1, name_account);
        stat.setDouble(2, balance);
        stat.setString(3, currency);
        stat.setLong(4, user_id);
        stat.executeUpdate();

        ResultSet rs = stat.getGeneratedKeys();
        long accountID = 0;
        if (rs.next()) {
            accountID = rs.getLong("id");
        }
        stat.close();
        return accountID;
    }
    private void deleteAccount(Connection conn, long accountID) throws SQLException {
        PreparedStatement stat = conn.prepareStatement("DELETE from account where id = ?");
        stat.setLong(1, accountID);
        stat.executeUpdate();
        System.out
                .println("Record Deleted successfully from database.");
        stat.close();
    }
}
