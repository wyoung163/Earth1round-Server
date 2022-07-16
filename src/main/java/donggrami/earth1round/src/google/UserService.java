package donggrami.earth1round.src.google;

import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.src.google.model.GoogleUser;
import donggrami.earth1round.src.google.model.OAuthToken;
import donggrami.earth1round.utils.jwt.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;

@Service
public class UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;
    private final OAuthService oauthService;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, OAuthService oauthService, JwtService jwtService) {
        this.userRepository = userRepository;
        this.oauthService = oauthService;
        this.jwtService = jwtService;
    }

    public String oauthLogin(String code) {
        ResponseEntity<String> accessTokenResponse = oauthService.createPostRequest(code);
        OAuthToken oAuthToken = oauthService.getAccessToken(accessTokenResponse);
        logger.info("Access Token: {}", oAuthToken.getAccessToken());

        ResponseEntity<String> userInfoResponse = oauthService.createGetRequest(oAuthToken);
        GoogleUser googleUser = oauthService.getUserInfo(userInfoResponse);
        logger.info("Google User Name: {}", googleUser.getName());

        if (isJoinedUser(googleUser)==null) {
            signUp(googleUser);
        }
        User user = userRepository.getByEmail(googleUser.getEmail());
        return jwtService.createAccessToken(user.getUser_id());
    }

    private User isJoinedUser(GoogleUser googleUser) {
        User users = userRepository.getByEmail(googleUser.getEmail());
        logger.info("Joined User: {}", users);
        return users;
    }

    private void signUp(GoogleUser googleUser) {
        Timestamp created_at = new Timestamp(new Date().getTime());
        Timestamp updated_at = new Timestamp(new Date().getTime());

        User userEntity = User.builder()
                .email(googleUser.getEmail())
                .nickname(googleUser.getName())
                .type(User.LoginType.GOOGLE)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();

        userRepository.save(userEntity);
    }
}