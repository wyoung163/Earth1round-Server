package donggrami.earth1round.src.profiles;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.ProfileRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.src.auth.google.GoogleUserService;
import donggrami.earth1round.src.profiles.model.PatchProfilesReq;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Optional;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class ProfilesService {

    @Autowired
    private final ProfileRepository profileRepository;
    @Autowired
    private final UserRepository userRepository;

    private Logger logger = LoggerFactory.getLogger(GoogleUserService.class);

    public void modifyNickname(Long user_id, PatchProfilesReq patchProfilesReq) throws BaseException {
        try {
            Optional<User> user = userRepository.findById(user_id);
            Optional<Profile> profile = profileRepository.findByUser(user.get());
            if (profile.isEmpty()) {
                throw new BaseException(EMPTY_USER, HttpStatus.BAD_REQUEST);
            }
            else{
                Profile new_profile = profile.get();
                new_profile.setNickname(patchProfilesReq.getNew_nickname());
                new_profile.setUpdated_at(new Timestamp(new Date().getTime()));
                profileRepository.save(new_profile);
                if(!Objects.equals(profileRepository.findByUser(user.get()).get().getNickname(), patchProfilesReq.getNew_nickname())){
                    throw new BaseException(PATCH_USERS_INVALID_NICKNAME, HttpStatus.BAD_REQUEST);
                }
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
