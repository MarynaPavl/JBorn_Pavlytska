package ru.pavlytskaya.dao;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class TypeDao {
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public List<TypeTransactionModel> typeInformation() {

        return em.createQuery("select t from TypeTransactionModel t", TypeTransactionModel.class).getResultList();
    }

    @Transactional
    public TypeTransactionModel creatType(String assignment) {

        TypeTransactionModel type = new TypeTransactionModel();
        type.setAssignment(assignment);

        em.persist(type);

        return type;
    }

}
