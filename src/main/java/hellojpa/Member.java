package hellojpa;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Member")
public class Member {
    @Id // PK 매핑
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(100) default 'EMPTY'") // DB 컬럼명 지정
    private String username;

    public Member() {}
}
/*
create table Member (
    id bigint not null,
    name varchar(255),
    primary key (id)
);
 */