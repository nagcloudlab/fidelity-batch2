package com.example.repository;

import com.example.entity.Account;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.List;

public class JpaAccountRepository implements AccountRepository {

    EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("myPU");
    EntityManager em = emf.createEntityManager();

    @Override
    public List<Account> findAll() {
        return em.createQuery("SELECT a FROM Account a", Account.class).getResultList();
    }

    @Override
    public Account findByAccountNumber(String accountNumber) {
        return em.find(Account.class, accountNumber);
    }

    @Override
    public void updateAccount(Account account) {
        em.getTransaction().begin();
        em.merge(account);
        em.getTransaction().commit();
    }
}
