package com.example.techtask.service.impl;

import com.example.techtask.model.Order;
import com.example.techtask.model.enumiration.UserStatus;
import com.example.techtask.service.OrderService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.List;
//1.Реализация интерфейса OrderService
@Service
public class OrderServiceImpl implements OrderService {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order findOrder() {
        // Возвращаеем самый новый заказ, в котором больше одного предмета
        return entityManager.createQuery(
                        "SELECT o FROM Order o " +
                                "WHERE o.quantity > 1 " +
                                "ORDER BY o.createdAt DESC",
                        Order.class)
                .setMaxResults(1)
                .getSingleResult();
    }

    @Override
    public List<Order> findOrders() {
        // Возвращаем заказы от активных пользователей, отсортированные по дате создания
        return entityManager.createQuery(
                        "SELECT o FROM Order o " +
                                "JOIN User u ON o.userId = u.id " +
                                "WHERE u.userStatus = :status " +
                                "ORDER BY o.createdAt ASC",
                        Order.class)
                .setParameter("status", UserStatus.ACTIVE)
                .getResultList();
    }
}
