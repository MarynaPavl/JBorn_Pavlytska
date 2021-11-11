package ru.pavlytskaya.repository;

import lombok.RequiredArgsConstructor;
import ru.pavlytskaya.entity.TypeTransactionModel;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class TypeTransactionModelRepositoryImpl implements TypeTransactionModelRepositoryCustom {

    private final EntityManager em;


    @Override
    public List<TypeTransactionModel> findByFilter(TypeTransactionModelFilter filter) {
        String query = "select u from TypeTransactionModel u where 1 = 1";

        Map<String, Object> params = new HashMap<>();
        if(filter.getAssignmentLike() != null){
            query += " and assignment like :assignmentLike";
            params.put("assignmentLike", filter.getAssignmentLike());
        }
        TypedQuery<TypeTransactionModel> typedQuery = em.createQuery(query, TypeTransactionModel.class);
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            typedQuery.setParameter(entry.getKey(), entry.getValue());
        }

        return typedQuery.getResultList();
    }
}
