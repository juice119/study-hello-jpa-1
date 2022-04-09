package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaShopServiceTest extends TestCase {
    protected static EntityManagerFactory emf;
    protected static EntityManager em;
    protected static EntityTransaction tx;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        if (this.emf == null) {
            this.emf = Persistence.createEntityManagerFactory("hello");
        }
        this.em = this.emf.createEntityManager();
        this.tx = em.getTransaction();
        this.tx.begin();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        this.em.close();
        this.tx.rollback();
    }

    public void testNewMember() {
        // given
        String name = "테스터123";

        // when
        Member member = JpaShopService.newMember(this.em, name);
        Member selectMember = em.find(Member.class, member.getId());

        // then
        assertEquals(selectMember.getName(), name);
    }

    public void testNewOrder() {
        // given
        String name = "테스터123";

        // when
        Member member = JpaShopService.newMember(this.em, name);
        Order order = JpaShopService.newOrder(this.em, member);
        Order findOrder = em.find(Order.class, order.getId());
        Member findMember = findOrder.getMember();

        // then
        assertEquals(member.getId(), findMember.getId());
    }

    public void testNewOrderItem() {
        // given
        String name = "테스터123";

        // when
        Member member = JpaShopService.newMember(this.em, name);
        Order order = JpaShopService.newOrder(this.em, member);
        List<OrderItem> orderItems = JpaShopService.newOrderItem(em, order);

        OrderItem findOrderItem = em.find(OrderItem.class, orderItems.get(0).getId());

        // then
        assertEquals(findOrderItem.getId(), orderItems.get(0).getId());
    }
}