package jpabook.jpashop;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import jpabook.jpashop.domain.OrderStatus;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class jpaMain {
    public static void main(String args[]) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Member member = jpaMain.newMember(em);
            Order order = jpaMain.newOrder(em, member);
            jpaMain.newOrderItem(em, order);
            tx.commit();
        } catch (Exception e) {
            System.out.println(e);
            tx.rollback();
        } finally {
            System.out.println("======== 정상 종료 완료 ========");
            em.close();
        }
    }

    static Member newMember(EntityManager em) {
        Member member = new Member();
        em.persist(member);
        return member;
    }

    static Order newOrder(EntityManager em, Member member) {
        Order order = new Order();
        order.setStatus(OrderStatus.ORDER);
        order.setMember(member);
        order.setOrderDate(LocalDateTime.now());
        em.persist(order);
        return order;
    }

    static List<OrderItem>  newOrderItem(EntityManager em, Order order) {
        List<OrderItem> orderItems = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setOrderPrice(i + 1 * 100);
            orderItem.setCount(i + 1);
            orderItems.add(orderItem);
            em.persist(orderItem);
        }

        return orderItems;
    }
}
