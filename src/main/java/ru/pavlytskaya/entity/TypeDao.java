package ru.pavlytskaya.entity;

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

        return em.createNamedQuery("Type.List", TypeTransactionModel.class).getResultList();
    }

    @Transactional
    public TypeTransactionModel creatType(String assignment) {

        TypeTransactionModel type = new TypeTransactionModel();
        type.setAssignment(assignment);

        em.persist(type);

        return type;
    }

}
