package com.example.techtask.service.impl;

import com.example.techtask.model.User;
import com.example.techtask.service.UserService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
//1.Реализация интерфейса UserService
@Service
public class UserServiceImpl implements UserService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public User findUser() {
        // Возвращаем пользователя с максимальной общей суммой товаров, доставленных в 2003
        return entityManager.createQuery(
                        "SELECT u FROM User u " +
                                "JOIN u.orders o " +
                                "WHERE YEAR(o.createdAt) = 2003 AND o.orderStatus = 'DELIVERED' " +
                                "GROUP BY u.id " +
                                "ORDER BY SUM(o.price * o.quantity) DESC",
                        User.class)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<User> findUsers() {
        // Возвращаем пользователей у которых есть оплаченные заказы в 2010.
        return entityManager.createQuery(
                        "SELECT DISTINCT u FROM User u " +
                                "JOIN u.orders o " +
                                "WHERE YEAR(o.createdAt) = 2010 AND o.orderStatus = 'PAID'",
                        User.class)
                .getResultList();
    }
}
