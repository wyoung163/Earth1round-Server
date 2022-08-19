package donggrami.earth1round.src.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.secret.Secret;
import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Custom;
import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.CourseRepository;
import donggrami.earth1round.src.domain.repository.CustomRepository;
import donggrami.earth1round.src.domain.repository.ProfileRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.src.google.model.GoogleUserRes;
import donggrami.earth1round.src.google.model.GoogleOAuthTokenRes;
import donggrami.earth1round.utils.jwt.JwtService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;

import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static donggrami.earth1round.config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@Configuration
public class GoogleUserService {
    private Logger logger = LoggerFactory.getLogger(GoogleUserService.class);

    private static final String CLIENT_ID = Secret.GOOGLE_CLIENT_ID;
    private static final String CLIENT_SECRET = Secret.GOOGLE_CLIENT_SECRET;
    private static final String REDIRECT_URI = Secret.GOOGLE_REDIRECT;
    private static final String GRANT_TYPE = "authorization_code";

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;
    private final CourseRepository courseRepository;
    private final CustomRepository customRepository;
    private final GoogleUserDao userDao;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    public GoogleUserService(UserRepository userRepository, ProfileRepository profileRepository, CourseRepository courseRepository, CustomRepository customRepository, GoogleUserDao userDao, JwtService jwtService, RestTemplateBuilder restTemplate) {
        this.userRepository = userRepository;
        this.profileRepository = profileRepository;
        this.courseRepository = courseRepository;
        this.customRepository = customRepository;
        this.userDao = userDao;
        this.jwtService = jwtService;
        this.objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.restTemplate = restTemplate.build();
    }

    public GoogleUserRes oauthLogin(String code) throws ParseException {
        ResponseEntity<String> accessTokenResponse = createPostRequest(code);
        GoogleOAuthTokenRes oAuthToken = getAccessToken(accessTokenResponse);
        logger.info("Access Token: {}", oAuthToken.getAccessToken());

        ResponseEntity<String> userInfoResponse = createGetRequest(oAuthToken);
        HashMap<String, Object> userInfo = getUserInfo(userInfoResponse);
        logger.info("Google User Name: {}", userInfo.get("personal_id").toString());

        if (isJoinedUser(userInfo) == null) {
            User newUser = userRepository.save(userDao.insertUser(userInfo));
            profileRepository.save(userDao.insertProfile(userInfo, newUser));
        }

        User user = userRepository.getByPersonalId(new BigDecimal(userInfo.get("personal_id").toString()).setScale(0, RoundingMode.FLOOR).longValue());
        String access_token = jwtService.createAccessToken(user.getUser_id());
        String refresh_token = jwtService.createRefreshToken(user.getUser_id());
        return new GoogleUserRes(access_token, refresh_token, user.getUser_id());
    }

    private User isJoinedUser(HashMap<String, Object> userInfo) {
        User users = userRepository.getByPersonalId(new BigDecimal(userInfo.get("personal_id").toString()).setScale(0, RoundingMode.FLOOR).longValue());
        logger.info("Joined User: {}", users);
        return users;
    }

    public void googleWithdrawal(Long user_id){
        try{

            //Get user
            Long personal_id = userRepository.getPersonalIdByUserId(user_id);
            User user = userRepository.findByPersonalId(personal_id);
            //Settings
            if (user == null){
                throw new BaseException(RESPONSE_ERROR, HttpStatus.BAD_REQUEST);
            }
            List<Course> courseList = courseRepository.findByUser(user);
            Optional<Custom> customList = customRepository.findByUser(user);
            Optional<Profile> profileList = profileRepository.findByUser(user);
            //Data delete
            for (Course course : courseList) {
                courseRepository.delete(course);
            }
            logger.warn("course ok");
            customList.ifPresent(customRepository::delete);
            logger.warn("custom ok");
            profileList.ifPresent(profileRepository::delete);
            logger.warn("profile ok");
            userRepository.delete(user);
            logger.warn("user ok");

        } catch (Exception exception) {
            throw new BaseException(RESPONSE_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    //OAuthService 였던것
    public ResponseEntity<String> createPostRequest(String code) {
        String url = "https://oauth2.googleapis.com/token";

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("redirect_uri", REDIRECT_URI);
        params.add("grant_type", GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(params, headers);
        ResponseEntity<String> result = restTemplate.exchange(URLDecoder.decode(url, StandardCharsets.UTF_8), HttpMethod.POST, httpEntity, String.class);

        return result;
    }

    public GoogleOAuthTokenRes getAccessToken(ResponseEntity<String> response) {
        GoogleOAuthTokenRes oAuthToken = null;
        try {
  //          logger.info("getAccessToken1"+response.toString());
            oAuthToken = objectMapper.readValue(response.getBody(), GoogleOAuthTokenRes.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oAuthToken;
    }

    public ResponseEntity<String> createGetRequest(GoogleOAuthTokenRes oAuthToken) {
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);
   //     logger.warn(request.toString());

        return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    }

    public HashMap<String, Object> getUserInfo(ResponseEntity<String> userInfoResponse) throws ParseException {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(userInfoResponse.getBody());
   //     logger.info("UserService getUserInfo "+jsonObject.toString());

        userInfo.put("name", jsonObject.get("name"));
        userInfo.put("personal_id", jsonObject.get("id"));
        userInfo.put("picture", jsonObject.get("picture"));
        logger.info(userInfo.toString());

        return userInfo;
    }
}