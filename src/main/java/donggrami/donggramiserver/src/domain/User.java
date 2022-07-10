package donggrami.donggramiserver.src.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "user")
@Getter
@Setter
@DynamicInsert
public class User {
    public enum LoginType {
        KAKAO, GOOGLE, APPLE
    }

    public enum UserStatus {
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 30, nullable = false)
    private String nickname;

    @Enumerated(EnumType.STRING)
    private LoginType type;

    @Column(nullable = false)
    private int level;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("ACTIVE")
    private UserStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Timestamp created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Timestamp updated_at;
}