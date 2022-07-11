package donggrami.earth1round.src.character;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {
    public enum CharacterStatus {
        ACTIVE, INACTIVE, COMPLETE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long character_id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ColumnDefault("0")
    private int character_num;

    @Enumerated(EnumType.STRING)
    @ColumnDefault("ACTIVE")
    private CharacterStatus status;

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Timestamp created_at;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Timestamp updated_at;

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
