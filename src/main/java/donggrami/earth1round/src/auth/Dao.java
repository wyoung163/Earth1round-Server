package donggrami.earth1round.src.auth;

import donggrami.earth1round.src.domain.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;

@Repository
@NoArgsConstructor
public class Dao {
    private Timestamp created_at = new Timestamp(new java.util.Date().getTime());
    private Timestamp updated_at = new Timestamp(new java.util.Date().getTime());

    public User toEntity(HashMap userInfo) {
        User userEntity = User.builder()
                .email(userInfo.get("email").toString())
                .nickname(userInfo.get("nickname").toString())
                .type(User.LoginType.KAKAO)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
        return userEntity;
    }
}
