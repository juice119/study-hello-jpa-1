package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class PersistenceContextStudy1 {
    private static final Long id = 15L;

    static void main(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            // 1. 비영속성 상태 -  new
            System.out.println("비영속 상태 -----");
            Member member = new Member();
            member.setId(id);
            member.setName("hello Spring is new");
            System.out.println("beforeMember Name: " + member.getName());

            // 2. 영속성 상태 - Managed
            System.out.println("영속 상태 -----");
            em.persist(member);
            member.setName("hello Spring is Managed");

            /**
             * SELECT 쿼리가 두번 발생하지 않는 이유 1차 캐시에 저장된 member 가져오기 떄문
             */
            Member updatedMember = em.find(Member.class, id);
            System.out.println("updatedMember Name: " + updatedMember.getName());

            System.out.println("COMMIT ============================");
            tx.commit();
        } catch (Exception e) {
            System.out.println("에러 발생");
            tx.rollback();
        } finally {
            tx.begin();
            em.remove(em.find(Member.class, id));
            tx.commit();
            em.close();
        }

    }
}
