package donggrami.earth1round.src.character;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Character {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int character_id;
    private int user_id;

    private int character_num;

    @Column(columnDefinition = "VARCHAR(10) NOT NULL")
    private String status;

    @Temporal(value = TemporalType.TIMESTAMP)
    private Timestamp created_at;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Timestamp updated_at;

    @Builder
    public Character(int character_id, int user_id, int character_num, String status, Timestamp created_at, Timestamp updated_at){
        this.character_id = character_id;
        this.user_id = user_id;
        this.character_num = character_num;
        this.status = status;
        this.created_at = created_at;
        this.updated_at=updated_at;
    }
}
