package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        // "hello" 는 persistence.xml 에서 <persistence-unit name="hello"> 로 설정한 값이다.
        // EntityManagerFactory 는 application 전체에서 DB 당 1개씩만 있어야 한다.
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        // DataBase Connection 1개를 받았다고 생각하면 된다.
        EntityManager em = emf.createEntityManager();
        // EntityManager 는 Thread 간 공유 절대 X
        // 사용자 요청이 올 때마다 계속 [ 생성 - 사용 - clsoe() ] 해줘야 한다.

        EntityTransaction tx = em.getTransaction(); // 트랜잭션
        tx.begin();                                 // 트랜잭션 시작

        // 실제 비즈니스 code 작성
        try {
            // ============= 시작 1. INSERT =============
//             Member member = new Member();
//             member.setId(2L);
//             member.setName("HelloB");

            // INSERT 실행
            // em.persist(member);
            // ============= 끝 1. INSERT =============

            // ============= 시작 2. 회원 단 건 조회 & UPDATE =============
            // 단 건 조회
            // Member findMember = em.find(Member.class, 2L); // 2L = PK
            // System.out.println("findMember = " + findMember.getId());
            // System.out.println("findMember = " + findMember.getName());

            // UPDATE
//            findMember.setName(findMember.getName() + " 수정한 이름");
            // ============= 끝 2. 회원 단 건 조회 & UPDATE =============
            /**
             * em.persist, em.remove 같이 별도의 jpa 관련 함수를 호출하지 않아도 된다.
             * why?
             * - JPA 를 통해서 Entity 를 가져오면 해당 Entity 는 JPA 가 관리를 한다.
             * - Entity 의 값이 변경 됐는지 안 됐는지 트랜잭션이 커밋되는 시점에 JPA 가 체크를 한다.
             * - 변경됐으면 트랜잭션이 커밋되기 직전에 UPDATE 쿼리를 만들어 날려버린 후 트랜잭션 커밋을 해버린다.
             */

            // ============= 시작 3. DELETE =============
            // (id = 3) 회원 단 건 조회 후 해당 객체를 넣어주면 된다.
//            Member removeMember = em.find(Member.class, 3L);
//            System.out.println("findMember = " + removeMember.getId());
//            System.out.println("findMember = " + removeMember.getName());
//
//            em.remove(removeMember);
            // ============= 끝 3. DELETE =============

            // ============= commit =============
            // 비즈니스 로직이 정상적으로 완료 되면 트랜잭션 커밋
             tx.commit();
            // ============= commit =============

// =================================================================================================================================================================
// ============================================================================= JPQL ==============================================================================
// =================================================================================================================================================================
            // ============= 시작 1. JPQL로 전체 회원 검색 =============
            // 대상이 table 이 아니라 객체(Entity)이다.
//            List<Member> findMembers = em.createQuery("select m from Member as m", Member.class)
//                                         .getResultList();
//            for (Member member : findMembers) {
//                System.out.println("member.id = " + member.getId() + ", member.name = " + member.getName());
//            }

            // WHERE 절도 넣을 수 있다.
            // List<Member> findMembers = em.createQuery("select m from Member as m where m.name = xxxx", Member.class).getResultList();
            // ============= 끝 1. JPQL로 전체 회원 검색 =============

            // ============= 시작 2. JPQL로 페이징 =============
            // 예를 들어 DB를 H2에서 Oracle로 바꿔도 아래 코드는 일절 수정할 필요가 없다.
            // JPA 가 알아서 dialect 를 맞춰서 쿼리를 날려준다.(매우 편리, persistence.xml 에서 dialect 설정만 바꿔주면 된다.)
//            List<Member> findMembers2 = em.createQuery("select m from Member as m", Member.class)
//                                        .setFirstResult(0)
//                                        .setMaxResults(10) // 0번 부터 10개 가져와, .setFirstResult(5).setMaxResults(8) ==> 5번 부터 8개 가져와
//                                        .getResultList();
//            for (Member member2 : findMembers2) {
//                System.out.println("member2.id = " + member2.getId() + ", member2.name = " + member2.getName());
//            }
            // ============= 끝 2. JPQL로 페이징 =============
        } catch (Exception e) {
            tx.rollback(); // Exception 발생 시 트랜잭션 롤백
        } finally {
            /**
             * [EntityManager close]
             * - EntityManager 가 DataBase Connection 을 물고 동작하므로
             * - 사용이 끝나면 반드시 닫아줘야 한다.(DataBase Connection 반환), 그래서 finally 구문에 작성하는 것이다.
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