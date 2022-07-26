package donggrami.earth1round.src.custom;

import donggrami.earth1round.src.domain.entity.Custom;
import donggrami.earth1round.src.domain.entity.User;
import lombok.NoArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
@NoArgsConstructor
public class CustomDao {
    private Timestamp created_at = new Timestamp(new Date().getTime());
    private Timestamp updated_at = new Timestamp(new Date().getTime());

    public Custom toEntity(User user) throws ParseException {
        return Custom.builder()
                .user(user)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
    }
}
