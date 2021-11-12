package ru.pavlytskaya.repository;

import lombok.RequiredArgsConstructor;
import ru.pavlytskaya.entity.TransactionInformationModel;
import ru.pavlytskaya.entity.TypeTransactionModel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.*;

@RequiredArgsConstructor
public class TransactionModelRepositoryImpl implements TransactionModelRepositoryCustom {
    private final EntityManager em;
    private final TypeTransactionModelRepositoryImpl typeTransactionModelRepository;

    @Override
    public List<TransactionInformationModel> findByFilter(TransactionModelFilter transactionFilter) {
        String query = "select u from TransactionInformationModel u ";

        Map<String, Object> params = new HashMap<>();
        if (transactionFilter.getAssignmentLike() != null) {
            TypeTransactionModelFilter filter = new TypeTransactionModelFilter();
            TypeTransactionModelFilter typeTransactionModelFilter = filter.setAssignmentLike(transactionFilter.getAssignmentLike());
            List<TypeTransactionModel> list = typeTransactionModelRepository.findByFilter(typeTransactionModelFilter);
            for (TypeTransactionModel typeTransactionModel : list) {
                long id = typeTransactionModel.getId();
                query += " join u.types a where a.id=:id";
                params.put("id", id);
            }
        }

        if (transactionFilter.getFromData() != null) {
            query += " and :fromData < u.data";
            params.put("fromData", transactionFilter.getFromData());
        }

        if (transactionFilter.getToData() != null) {
            query += " and :toData > u.data";
            params.put("toData", transactionFilter.getToData());
        }
        TypedQuery<TransactionInformationModel> typedQuery = em.createQuery(query, TransactionInformationModel.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }

        return typedQuery.getResultList();
    }
}











