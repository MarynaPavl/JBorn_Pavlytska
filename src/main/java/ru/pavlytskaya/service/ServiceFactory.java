package ru.pavlytskaya.service;

import ru.pavlytskaya.comverter.AccountModelToAccountDTOConverter;
import ru.pavlytskaya.comverter.TransactionInformationModelToInformationDTOConverter;
import ru.pavlytskaya.comverter.TypeTransactionModelToTypeDTOConverter;
import ru.pavlytskaya.comverter.UserModelToUserDTOConverter;
import ru.pavlytskaya.dao.AccountDao;
import ru.pavlytskaya.dao.InformationDao;
import ru.pavlytskaya.dao.TypeDao;
import ru.pavlytskaya.dao.UserDao;

public class ServiceFactory {
    private static AuthService authService;
    private static AccountService accountService;
    private static TypeService typeService;
    private static InformationService informationService;

    public static AuthService getAuthService() {
        if (authService == null) {
            authService = new AuthService(
                    new UserDao(),
                    new Md5DigestService(),
                    new UserModelToUserDTOConverter()
            );
        }
        return authService;
    }

    public static AccountService getAccountService() {
        if (accountService == null) {
            accountService = new AccountService(
                    new AccountDao(),
                    new AccountModelToAccountDTOConverter()
            );
        }
        return accountService;
    }

    public static TypeService getTypeService() {
        if (typeService == null) {
            typeService = new TypeService(
                    new TypeDao(),
                    new TypeTransactionModelToTypeDTOConverter()
            );
        }
        return typeService;
    }

    public static InformationService getInformationService() {
        if (informationService == null) {
            informationService = new InformationService(
                    new InformationDao(),
                    new TransactionInformationModelToInformationDTOConverter()
            );
        }
        return informationService;
    }
}
