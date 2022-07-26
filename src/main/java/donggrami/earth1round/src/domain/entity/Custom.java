package donggrami.earth1round.src.domain.entity;

import donggrami.earth1round.src.domain.entity.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Getter
@Entity
@Table(name = "Custom")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Custom {
    public enum CustomStatus {
        ACTIVE, INACTIVE, COMPLETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long custom_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("0")
    private int custom_num;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("'ACTIVE'")
    private CustomStatus status;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date created_at;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updated_at;

//    @Builder
//    public Character(int character_id, int user_id, int character_num, String status, Timestamp created_at, Timestamp updated_at){
//        this.character_id = character_id;
//        this.user_id = user_id;
//        this.character_num = character_num;
//        this.status = status;
//        this.created_at = created_at;
//        this.updated_at=updated_at;
//    }
}
