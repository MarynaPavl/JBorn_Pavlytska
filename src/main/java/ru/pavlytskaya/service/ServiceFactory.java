package ru.pavlytskaya.service;

import static ru.pavlytskaya.converter.ConvectorFactory.*;
import static ru.pavlytskaya.dao.DaoFactory.*;

public class ServiceFactory {
    private static AuthService authService;
    private static AccountService accountService;
    private static TypeService typeService;
    private static TransactionInformationService transactionInformationService;
    private static TransactionToCategoryService transactionToCategoryService;

    public static AuthService getAuthService() {
        if (authService == null) {
            authService = new AuthService(
                    getUserDao(),
                    getDigestService(),
                    getUserModelToUserDTOConverter()
            );
        }
        return authService;
    }

    private static DigestService digestService;

    public static DigestService getDigestService() {
        if (digestService == null) {
            digestService = new Md5DigestService();
        }
        return digestService;
    }

    public static AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(
                    getAccountDao(),
                    getAccountModelToAccountDTOConverter()
            );
        }
        return accountService;
    }

    public static TypeService getTypeService() {
        if (typeService == null) {
            typeService = new TypeService(
                    getTypeDao(),
                    getTypeTransactionModelToTypeDTOConverter()
            );
        }
        return typeService;
    }

    public static TransactionInformationService getInformationService() {
        if (transactionInformationService == null) {
            transactionInformationService = new TransactionInformationService(
                    getInformationDao(),
                    getTransactionInformationModelToInformationDTOConverter()
            );
        }
        return transactionInformationService;
    }
    public static TransactionToCategoryService getTransactionToCategoryService(){
        if(transactionToCategoryService == null){
            transactionToCategoryService = new TransactionToCategoryService(
                    getTransactionToCategoryDao(),
                    getTransactionToCategoryModelTransactionToCategoryDTOConverter()
            );
        }
        return transactionToCategoryService;
    }
}
