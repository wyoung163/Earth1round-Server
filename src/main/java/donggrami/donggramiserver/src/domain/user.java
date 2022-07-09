package donggrami.donggramiserver.src.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;



@Entity
@Table(name = "user")
@Getter
@Setter
public class user {
    public enum loginType {
        kakao, google, apple
    }

    public enum userStatus {
        active, inactive
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;
    @Column(nullable = false, length = 50)
    private String email;
    /*
    <자체 회원 가입 있으면 추가>
    @Column(length = 30, nullable = false)
    private String password;
     */
    @Column(length = 30, nullable = false)
    private String nickname;
    @Enumerated(EnumType.STRING)
    private loginType type; //kakao, google, apple
    @Column(length = 30, nullable = false)
    private String level;
    /*
    <int의 경우>
    @Column(nullable = false)
    private int level;
     */
    @Enumerated(EnumType.STRING)
    private userStatus status; //active, inactive
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Timestamp created_at;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Timestamp updated_at;

    /* character와 1:1 관계
    @OneToOne(mappedBy = "user_id")
    private character character;
     */

    /* course와 1:N 관계
    @OneToMany(mappedBy = "user_id") //반대쪽에 매핑되는 엔티티의 필드값
    private Set<course> courses; //List or Set
     */
}