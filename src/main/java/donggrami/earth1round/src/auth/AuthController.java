package donggrami.earth1round.src.auth;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.auth.google.GoogleUserService;
import donggrami.earth1round.src.auth.kakao.model.DeleteUserRes;
import donggrami.earth1round.src.auth.kakao.model.PostTokenReq;
import donggrami.earth1round.src.auth.kakao.model.PostTokenRes;
import donggrami.earth1round.utils.jwt.JwtService;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static donggrami.earth1round.config.BaseResponseStatus.POST_EMPTY_REFRESH_TOKEN;

@RestController
public class AuthController {

    private AuthService service;
    private final JwtService jwtService;

    public AuthController(AuthService authservice, JwtService jwtService) {
        this.service = authservice;
        this.jwtService = jwtService;
    }

    /**
     * 회원탈퇴 API
     * [DELETE] /unlink
     * @return BaseResponse<DeleteUserRes>
     */
    @ResponseBody
    @DeleteMapping(value="/unlink")
    public BaseResponse<String> withdrawal(HttpServletRequest req) throws ParseException {
            //카카오 토큰 확인을 위한 세션 정보 가져오기
            String access_Token = req.getAttribute("kakaoAccessToken").toString();
            if(!access_Token.isEmpty()) {
                String isUnlinked = unlinkKakaoAccess(access_Token);
            }

            Long userIdByJwt = jwtService.getUserId();
            service.userWithdrawal(userIdByJwt);
            String result = "회원 탈퇴가 완료되었습니다.";
            return new BaseResponse<>(result);
    }

    //카카오 계정 연결 해지
    public String unlinkKakaoAccess(String access_Token) {
        String reqURL = "https://kapi.kakao.com/v1/user/unlink";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            // 요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println(result);

            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }

        return "success";
    }

    /**
     * Access token 재발행 API
     * [POST] /re-issue
     * @return BaseResponse<PostTokenRes>
     */
    @ResponseBody
    @PostMapping("/re-issue")
    public BaseResponse<PostTokenRes> reIssueToken(@RequestBody PostTokenReq postTokenReq) {
        try {
            if (postTokenReq.getRefresh_token() == null) {
                return new BaseResponse<>(POST_EMPTY_REFRESH_TOKEN);
            }

            jwtService.isValidRefreshToken(postTokenReq.getRefresh_token());
            Long user_id = jwtService.getUserIdWithRefreshToken(postTokenReq.getRefresh_token());
            PostTokenRes postTokenRes = new PostTokenRes(jwtService.createAccessToken(user_id));

            return new BaseResponse<>(postTokenRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
