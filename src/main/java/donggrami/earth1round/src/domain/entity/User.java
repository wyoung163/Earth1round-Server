package donggrami.earth1round.src.domain.entity;

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

    @Column(name = "personal_id")
    private Long personalId;

    @Enumerated(EnumType.STRING)
    private LoginType type;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private UserStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated_at;

    @Builder
    public User(Long user_id, Long personalId, LoginType type, UserStatus status, Timestamp created_at, Timestamp updated_at) {
        this.user_id = user_id;
        this.personalId = personalId;
        this.type = type;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}