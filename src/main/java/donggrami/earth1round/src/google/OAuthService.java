package donggrami.earth1round.src.google;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import donggrami.earth1round.config.secret.Secret;
import donggrami.earth1round.src.google.model.OAuthToken;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
@Configuration
public class OAuthService {

    private final ObjectMapper objectMapper;
    private final RestTemplate restTemplate;

    private static final String CLIENT_ID = Secret.GOOGLE_CLIENT_ID;
    private static final String CLIENT_SECRET = Secret.GOOGLE_CLIENT_SECRET;
    private static final String REDIRECT_URI = Secret.GOOGLE_REDIRECT;
    private static final String GRANT_TYPE = "authorization_code";

    public OAuthService(RestTemplateBuilder restTemplate) {
        this.objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
        this.restTemplate = restTemplate.build();
    }

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

        return restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
    }

    public OAuthToken getAccessToken(ResponseEntity<String> response) {
        OAuthToken oAuthToken = null;
        try {
            oAuthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return oAuthToken;
    }

    public ResponseEntity<String> createGetRequest(OAuthToken oAuthToken) {
        String url = "https://www.googleapis.com/oauth2/v1/userinfo";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + oAuthToken.getAccessToken());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity(headers);

        return restTemplate.exchange(url, HttpMethod.GET, request, String.class);
    }

    public HashMap<String, Object> getUserInfo(ResponseEntity<String> userInfoResponse) throws ParseException {
        HashMap<String, Object> userInfo = new HashMap<String, Object>();

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(userInfoResponse.getBody());
        JSONArray detail = (JSONArray) jsonObject.get("data");

        if (jsonObject.get("code").equals("4000")) {
            JSONObject result = (JSONObject) detail.get(0);
            JSONObject support = (JSONObject) ((JSONArray)result.get("support")).get(0);

            userInfo.put("personal_id", support.get("sub"));
            userInfo.put("name", support.get("given_name"));
        }

//        String restCall = userInfoResponse.getBody();
//        JSONObject jsonObject = new JSONObject(userInfoResponse);
//        Long sub = Long.parseLong(jsonObject.getString("sub"));
//        String name = jsonObject.getString("given_name"); //or name
//
//        userInfo.put("personal_id", sub);
//        userInfo.put("name", name);

        //user = objectMapper.readValue(userInfoResponse.getBody(), User.class);

        return userInfo;
    }
}