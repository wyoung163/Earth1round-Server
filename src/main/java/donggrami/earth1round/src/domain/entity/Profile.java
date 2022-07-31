package donggrami.earth1round.src.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Profile")
@Getter
@Setter
@DynamicInsert
@NoArgsConstructor
public class Profile {
    public enum UserStatus {
        ACTIVE, INACTIVE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long profile_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 30)
    private String nickname;

    @Column(length = 255)
    private String profile_img;

    @Column(nullable = false)
    private int level;

    @PrePersist
    public void prePersist(){
        this.level = this.level == 0 ? 1 : this.level;
    }

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private User.UserStatus status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date created_at;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated_at;

    @Builder
    public Profile(Long profile_id, User user, String name, String nickname, String profile_img, int level, User.UserStatus status, Date created_at, Date updated_at) {
        this.profile_id = profile_id;
        this.user = user;
        this.name = name;
        this.nickname = nickname;
        this.profile_img = profile_img;
        this.level = level;
        this.status = status;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}