package donggrami.earth1round.src.auth.google;

import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

@Repository
@NoArgsConstructor
public class GoogleUserDao {

    private Logger logger = LoggerFactory.getLogger(GoogleUserService.class);

    public User insertUser(HashMap<String, Object> userInfo) {
        Timestamp created_at = new Timestamp(new Date().getTime());
        Timestamp updated_at = new Timestamp(new Date().getTime());

        User userEntity = User.builder()
                .personalId(new BigDecimal(userInfo.get("personal_id").toString()).setScale(0, RoundingMode.FLOOR).longValue())
                .type(User.LoginType.GOOGLE)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();

        return userEntity;
    }

    public Profile insertProfile(HashMap<String, Object> userInfo, User user) {
        Timestamp created_at = new Timestamp(new Date().getTime());
        Timestamp updated_at = new Timestamp(new Date().getTime());

        logger.warn("googleuserdaoinsertprofile?");
        Profile profileEntity = Profile.builder()
                .user(user)
                .name(userInfo.get("name").toString())
                .profile_img(userInfo.get("picture").toString())
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
        return profileEntity;
    }
}
