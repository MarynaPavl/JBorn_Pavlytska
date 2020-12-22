package ru.pavlytskaya.converter;

import ru.pavlytskaya.dao.*;
import ru.pavlytskaya.service.*;

public class ConvectorFactory {
    public static Converter<UserModel, UserDTO> userModelToUserDTOConverter;

    public static Converter<UserModel, UserDTO> getUserModelToUserDTOConverter() {
        if (userModelToUserDTOConverter == null) {
            userModelToUserDTOConverter = new UserModelToUserDTOConverter();
        }
        return userModelToUserDTOConverter;
    }

    public static Converter<AccountModel, AccountDTO> accountModelToAccountDTOConverter;

    public static Converter<AccountModel, AccountDTO> getAccountModelToAccountDTOConverter() {
        if (accountModelToAccountDTOConverter == null) {
            accountModelToAccountDTOConverter = new AccountModelToAccountDTOConverter();
        }
        return accountModelToAccountDTOConverter;
    }

    public static Converter<TypeTransactionModel, TypeDTO> typeTransactionModelToTypeDTOConverter;

    public static Converter<TypeTransactionModel, TypeDTO> getTypeTransactionModelToTypeDTOConverter() {
        if (typeTransactionModelToTypeDTOConverter == null) {
            typeTransactionModelToTypeDTOConverter = new TypeTransactionModelToTypeDTOConverter();
        }
        return typeTransactionModelToTypeDTOConverter;
    }

    public static Converter<TransactionInformationModel, TransactionInformationDTO> transactionInformationModelToInformationDTOConverter;

    public static Converter<TransactionInformationModel, TransactionInformationDTO> getTransactionInformationModelToInformationDTOConverter() {
        if (transactionInformationModelToInformationDTOConverter == null) {
            transactionInformationModelToInformationDTOConverter = new TransactionInformationModelToInformationDTOConverter();
        }
        return transactionInformationModelToInformationDTOConverter;
    }

    public static Converter<TransactionToCategoryModel, TransactionToCategoryDTO> transactionToCategoryModelTransactionToCategoryDTOConverter;

    public static Converter<TransactionToCategoryModel, TransactionToCategoryDTO> getTransactionToCategoryModelTransactionToCategoryDTOConverter(){
        if(transactionToCategoryModelTransactionToCategoryDTOConverter == null){
          transactionToCategoryModelTransactionToCategoryDTOConverter = new TransactionToCategoryModelToTransactionToCategoryDTOConverter();
        }
        return transactionToCategoryModelTransactionToCategoryDTOConverter;
    }
}
