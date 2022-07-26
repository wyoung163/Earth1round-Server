package donggrami.earth1round.src.domain.entity;

import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Custom")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@DynamicInsert
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

    @Builder
    public Custom(Long custom_id, User user, int custom_num, CustomStatus status, Date created_at, Date updated_at){
        this.custom_id = custom_id;
        this.user = user;
        this.custom_num = custom_num;
        this.status = status;
        this.created_at = created_at;
        this.updated_at=updated_at;
    }
}
