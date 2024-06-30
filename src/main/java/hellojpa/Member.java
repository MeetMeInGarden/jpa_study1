package hellojpa;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity(name = "Member")
@Table
    (
        name = "MEMBER"
        , catalog = ""
        , schema = ""
        , uniqueConstraints = {@UniqueConstraint(name = "NAME_AGE_UNIQUE", columnNames = {"NAME", "AGE"})}
    )
public class Member {
    @Id // PK 매핑
    private Long id;

    @Column(name = "name", columnDefinition = "varchar(100) default 'EMPTY'") // DB 컬럼명 지정
    private String username;

    private Integer age;

    @Column(name = "money", precision = 15, scale = 3)
    private BigDecimal money;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.DATE)
    private Date createdDate;

    // 연월
    private LocalDate localDate;
    // 연월일
    private LocalDateTime localDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    @Lob
    private String description;

    @Transient
    private String tmpValue;

    public Member() {}
}
/*
create table Member (
    id bigint not null,
    name varchar(255),
    primary key (id)
);
 */