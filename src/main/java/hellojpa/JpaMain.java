package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // "hello" 는 persistence.xml 에서 <persistence-unit name="hello"> 로 설정한 값이다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        // 트랜잭션
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        /*
         * 실제 비즈니스 code 작성
         */


        try {
            // 1. INSERT
            // Member member = new Member();
            // member.setId(1L);
            // member.setName("HelloA");

            // INSERT 실행
            // em.persist(member);

            // 2. 회원 단 건 조회
            Member findMember = em.find(Member.class, 2L);
            System.out.println("findMember = " + findMember.getId());
            System.out.println("findMember = " + findMember.getName());

            // 3. DELETE
            // 찾은 객체를 넣어주면 된다.
            // em.remove(findMember);

            // 4. UPDATE
            // findMember.setName(findMember.getName() + " 수정한 이름");

            // DML 이 정상적으로 끝나면 commit 을 해줘야 한다.
            // tx.commit();

            // JPQL로 전체 회원 검색
            // 대상이 table 이 아니라 객체(Entity)이다.
            List<Member> findMembers = em.createQuery("select m from Member as m", Member.class).getResultList();
            for (Member member : findMembers) {
                System.out.println("member.id = " + member.getId());
                System.out.println("member.name = " + member.getName());
            }

            // WHERE 절도 넣을 수 있다.
            // List<Member> findMembers = em.createQuery("select m from Member as m where m.name = xxxx", Member.class).getResultList();

            // 페이징
            // 예를 들어 DB를 oracle에서 MySQL로 바꿔도 아래 코드는 일절 수정할 필요가 없다.
            // JPA 가 알아서 dialect 를 맞춰서 쿼리를 날려준다.(매우 편리)
            List<Member> findMembers2 = em.createQuery("select m from Member as m", Member.class)
                                        .setFirstResult(1)
                                        .setMaxResults(10) // 1번 부터 10개 가져와, .setFirstResult(5).setMaxResults(8) ==> 5번 부터 8개 가져와
                                        .getResultList();


            // JPQL로 ID가 2 이상인 회원만 검색

            // JPQL로 이름이 같은 회원만 검색

        } catch (Exception e) {
            tx.rollback();
        } finally {
            /**
             * [EntityManager close]
             * - EntityManager 가 DataBase Connection 을 물고 동작하므로
             * - 사용이 끝나면 반드시 닫아줘야 한다.(DataBase Connection 반환)
             */
            em.close();
        }
        /**
         * [EntityManagerFactory close]
         * - 전체 Application 이 끝나면(= WAS가 내려갈 때) EntityManagerFactory 도 닫아준다.
         */
        emf.close();
    }
}