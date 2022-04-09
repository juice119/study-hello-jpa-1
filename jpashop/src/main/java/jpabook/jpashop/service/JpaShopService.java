package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JpaShopService {

    public static Member newMember(EntityManager em, String name) {
        Member member = new Member();
        member.setName(name);
        em.persist(member);
        return member;
    }

    public static Order newOrder(EntityManager em, Member member) {
        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        em.persist(order);
        return order;
    }

    public static List<OrderItem> newOrderItem(EntityManager em, Order order) {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();

        for (int i = 0; i < 3; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setOrderPrice((i + 1) * 100);
            orderItem.setCount(i + 1);
            orderItems.add(orderItem);
            em.persist(orderItem);
        }

        return orderItems;
    }
}
