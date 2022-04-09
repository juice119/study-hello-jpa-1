package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.OrderItem;
import junit.framework.TestCase;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.ArrayList;
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
        if(this.tx instanceof EntityTransaction && this.tx != null) {
            System.out.println("============ TX.ROLLBACK 실행 ============");
            this.tx.rollback();
        }
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


    public void test_양방향관계에서_주인이_데이터_설정시_변경된다() {
        // given
        String name = "테스터123";
        String changeName = "변경된 테스터";
        Member member = JpaShopService.newMember(this.em, name);
        Order order = JpaShopService.newOrder(this.em, member);

        Member changedMember = JpaShopService.newMember(this.em, changeName);
        Order findOrder = em.find(Order.class, order.getId());
        findOrder.setMember(changedMember);
        this.tx.commit();

        // when
        Order changedFindOrder = em.find(Order.class, order.getId());

        // then
        assertEquals(changedFindOrder.getMember().getName(), changeName);
        this.tx = null;
    }


    public void test_양방향관계에서_주인이_아닌_영속석_데이터_설정시_변경되지_않는다() {
        // given
        String name = "테스터123";
        String changeName = "변경된 테스터";
        Member defaultMember = JpaShopService.newMember(this.em, name);
        Member changeTargetMember = JpaShopService.newMember(this.em, changeName);
        Order defaultOrder = JpaShopService.newOrder(this.em, defaultMember);
        Order changeTargetOrder = JpaShopService.newOrder(this.em, defaultMember);
        this.tx.commit();

        // when
        this.tx.begin();
        Member findChangeTargetMember = em.find(Member.class, changeTargetMember.getId());
        ArrayList<Order> orders = new ArrayList<>();
        orders.add(changeTargetOrder);
        findChangeTargetMember.setOrders(orders);
        this.tx.commit();
        this.tx = null;

        Order resultOrder = em.find(Order.class, changeTargetOrder.getId());

        //왜 같이 나오지....
    }
}