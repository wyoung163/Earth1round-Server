package donggrami.earth1round.src.auth;

import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

@Repository
@NoArgsConstructor
public class KakaoAuthDao {
    private Timestamp created_at = new Timestamp(new Date().getTime());
    private Timestamp updated_at = new Timestamp(new Date().getTime());

    public User toUserEntity(HashMap userInfo) {
        User userEntity = User.builder()
                .personalId(Long.valueOf(userInfo.get("personalId").toString()))
                .type(User.LoginType.KAKAO)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
        return userEntity;
    }

    public Profile toProfileEntity(HashMap userInfo, User postUserRes) {
        Profile profileEntity = Profile.builder()
                .user(postUserRes)
                .nickname(userInfo.get("nickname").toString())
                .profile_img(userInfo.get("profileImage").toString())
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
        return profileEntity;
    }
}
