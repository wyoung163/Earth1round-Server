package donggrami.earth1round.src.google;

import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

@Repository
@NoArgsConstructor
public class UserDao {

//    private final UserRepository userRepository;

//    public UserDao(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    User signUp(HashMap<String, Object> userInfo) {
        Timestamp created_at = new Timestamp(new Date().getTime());
        Timestamp updated_at = new Timestamp(new Date().getTime());

        User userEntity = User.builder()
                .personalId(new BigDecimal(userInfo.get("personal_id").toString()).setScale(0, RoundingMode.FLOOR).longValue())
                .nickname(userInfo.get("name").toString())
                .type(User.LoginType.GOOGLE)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();

        return userEntity;
    }
}
