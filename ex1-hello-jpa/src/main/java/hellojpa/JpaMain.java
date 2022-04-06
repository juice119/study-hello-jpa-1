package hellojpa;


import javax.persistence.*;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
//        PersistenceContextStudy1.main(emf);
        emf.close();
    }

    static void helloJpa(EntityManagerFactory emf) {
        // 트랜잭션 단위 마다 em 을 만들어서 요청하기
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // INSERT
//            System.out.println("INSERT ---");
//            Member member = new Member();
//            member.setId(2L);
//            member.setName("test1");
//            em.persist(member);
//            System.out.println("INSERT MEMBER id = " + member.getId() + ", name = " + member.getName());
//            System.out.println("");

            // SELECT
            System.out.println("SELECT ---");
            Member member1 = em.find(Member.class, 1L);
            System.out.println("member id = 1 name = " + member1.getUserName());
            System.out.println();

            // SELECT BY QUERY
            System.out.println("SELECT BY QUERY ---");
            List<Member> resultList = em.createQuery("SELECT m FROM Member as m", Member.class).getResultList();
            for (Member resultMember : resultList) {
                System.out.println("SELECT BY QUERY  MEMBER id = " + resultMember.getId() + ", name = " + resultMember.getUserName());
            }
            System.out.println();

            // UPDATE
            System.out.println("UPDATE ---");
            Member beforeMember = em.find(Member.class, 1L);
            System.out.println("before hellojpa.Member Name: " + beforeMember.getUserName());

            String updateName = "hello spring";
            beforeMember.setUserName(updateName);
            Member updatedMember = em.find(Member.class, 1L);
            System.out.println("updated hellojpa.Member Name: " + updatedMember.getUserName());

            // DELETE
            //em.remove(em.find(Member.class, 1L));

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
    }
}
