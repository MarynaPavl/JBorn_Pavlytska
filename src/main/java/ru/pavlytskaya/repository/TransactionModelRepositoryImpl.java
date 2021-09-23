package ru.pavlytskaya.repository;

import lombok.RequiredArgsConstructor;
import ru.pavlytskaya.entity.TransactionInformationModel;

import javax.persistence.EntityManager;
import java.util.List;

@RequiredArgsConstructor
public class TransactionModelRepositoryImpl implements TransactionModelRepositoryCustom {
    private EntityManager em;

    @Override
    public List<TransactionInformationModel> findByFilter(TransactionModelFilter transactionFilter) {
        String query = "select u from TransactionInformationModel u where 1 = 1";
        String typeQuery = "select u from TypeTransactionModel u where 1 = 1";

        if(transactionFilter.getAssignmentLike() != null){
            typeQuery += " assignment like :assignmentLike";
        }
        if(transactionFilter.getFromData() != null){
            query+="";
        }
        if(transactionFilter.getToData() != null){
            query+="";
        }
        return null;
    }
}
