package donggrami.earth1round.src.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "User")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
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
    @ColumnDefault("1")
    private int level;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("ACTIVE")
    private UserStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated_at;

    @Builder
    public User(Long user_id, String email, String nickname, LoginType type, int level, UserStatus status, Timestamp created_at, Timestamp updated_at) {
        this.user_id = user_id;
        this.email = email;
        this.nickname = nickname;
        this.type = type;
        this.level = level;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}