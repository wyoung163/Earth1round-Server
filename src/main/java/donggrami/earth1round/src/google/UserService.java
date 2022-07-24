package donggrami.earth1round.src.google;

import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.src.google.model.GoogleUserRes;
import donggrami.earth1round.src.google.model.OAuthToken;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final OAuthService oauthService;
    private final UserDao userDao;
    private final JwtService jwtService;

//    public UserService(UserRepository userRepository, OAuthService oauthService, JwtService jwtService) {
//        this.userRepository = userRepository;
//        this.oauthService = oauthService;
//        this.jwtService = jwtService;
//    }

    public GoogleUserRes oauthLogin(String code) throws ParseException {
        ResponseEntity<String> accessTokenResponse = oauthService.createPostRequest(code);
        OAuthToken oAuthToken = oauthService.getAccessToken(accessTokenResponse);
        logger.info("Access Token: {}", oAuthToken.getAccessToken());

        ResponseEntity<String> userInfoResponse = oauthService.createGetRequest(oAuthToken);
        HashMap<String, Object> userInfo = oauthService.getUserInfo(userInfoResponse);
        logger.info("Google User Name: {}", userInfo.get("personal_id").toString());

        if(isJoinedUser(userInfo) == null){
            userRepository.save(userDao.signUp(userInfo));
        }

        User user = userRepository.getByPersonalId(Long.parseLong(userInfo.get("personal_id").toString()));
        String access_token = jwtService.createAccessToken(user.getUser_id());
        String refresh_token = jwtService.createRefreshToken(user.getUser_id());
        return new GoogleUserRes(access_token, refresh_token, user.getUser_id());
    }

    private User isJoinedUser(HashMap<String, Object> userInfo) {
        User users = userRepository.getByPersonalId(Long.parseLong(userInfo.get("personal_id").toString()));
        logger.info("Joined User: {}", users);
        return users;
    }

//    private void signUp(HashMap<String, Object> userInfo) {
//        Timestamp created_at = new Timestamp(new Date().getTime());
//        Timestamp updated_at = new Timestamp(new Date().getTime());
//
//        User userEntity = User.builder()
//                .personalId(Long.parseLong(userInfo.get("personal_id").toString()))
//                .nickname(userInfo.get("name").toString())
//                .type(User.LoginType.GOOGLE)
//                .created_at(created_at)
//                .updated_at(updated_at)
//                .build();
//
//        userRepository.save(userEntity);
//    }
}