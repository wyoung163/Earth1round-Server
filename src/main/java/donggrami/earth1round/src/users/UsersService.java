package donggrami.earth1round.src.users;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.domain.entity.Custom;
import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.ProfileRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.src.google.GoogleUserService;
import donggrami.earth1round.src.users.model.PatchUsersReq;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class UsersService {

    @Autowired
    private final ProfileRepository profileRepository;
    @Autowired
    private final UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(GoogleUserService.class);

    public void modifyNickname(Long user_id, PatchUsersReq patchUserReq) throws BaseException {
        try {
            Optional<User> user = userRepository.findById(user_id);
            Optional<Profile> profile = profileRepository.findByUser(user.get());
            if (profile.isEmpty()) {
                throw new BaseException(EMPTY_USER);
            }
            else{
                Profile new_profile = profile.get();
                new_profile.setNickname(patchUserReq.getNew_nickname());
                new_profile.setUpdated_at(new Timestamp(new Date().getTime()));
                profileRepository.save(new_profile);
                if(!Objects.equals(profileRepository.findByUser(user.get()).get().getNickname(), patchUserReq.getNew_nickname())){
                    throw new BaseException(PATCH_USERS_INVALID_NICKNAME);
                }
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
