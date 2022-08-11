package donggrami.earth1round.src.auth;

import donggrami.earth1round.config.BaseException;
import static donggrami.earth1round.config.BaseResponseStatus.*;

import donggrami.earth1round.src.auth.model.PostUserRes;
import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.ProfileRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class KakaoAuthService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final ProfileRepository profileRepository;

    @Autowired
    private final KakaoAuthDao dao;

    @Autowired
    private final JwtService jwtService;

    public User postUserRes;

    public User createUser(KakaoAuthDao dao, HashMap userInfo) {
        return userRepository.save(dao.toUserEntity(userInfo));
    }

    @Transactional
    public Profile createProfile(KakaoAuthDao dao, HashMap userInfo, User postUserRes) {
        return profileRepository.save(dao.toProfileEntity(userInfo, postUserRes));
    }

    public PostUserRes createUser(HashMap userInfo) throws BaseException {
        try{
            //이미 db에 정보가 등록된 회원인지 확인
            Long persoanlId = Long.valueOf(userInfo.get("personalId").toString());
            System.out.println(persoanlId);
            User existence = userRepository.findByPersonalId(persoanlId);
            System.out.println(existence);

            //유저 등록되어 있다면 해당 user_id 받아오기
            if(existence != null){
                postUserRes = existence;
                System.out.println(postUserRes);
                Long user_id = postUserRes.getUser_id();
                //jwt 발급
                String access_token = jwtService.createAccessToken(user_id);
                String refresh_token = jwtService.createRefreshToken(user_id);
                return new PostUserRes(access_token, refresh_token, user_id);
            }
            System.out.println("what");

            //유저 등록되어 있지 않다면 insert하고 user_id 받아오기
            postUserRes = createUser(dao, userInfo);
            Long user_id = postUserRes.getUser_id();

            userInfo.put("userId", user_id);

            //프로필 등록
            Profile profile = createProfile(dao, userInfo, postUserRes);

            try{
                //jwt 발급
                String access_token = jwtService.createAccessToken(user_id);
                String refresh_token = jwtService.createRefreshToken(user_id);
                return new PostUserRes(access_token, refresh_token, user_id);
            } catch (Exception exception) {
                throw new BaseException(DATABASE_ERROR, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
