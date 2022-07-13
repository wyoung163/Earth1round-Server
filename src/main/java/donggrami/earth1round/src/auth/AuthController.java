package donggrami.earth1round.src.auth;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.auth.model.PostTokenReq;
import donggrami.earth1round.src.auth.model.PostTokenRes;
import donggrami.earth1round.utils.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@RestController
public class AuthController {
    @Autowired
    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
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

    // test
//    @ResponseBody
//    @PostMapping("/login")
//    public BaseResponse<LoginRes> login(@RequestBody LoginReq loginReq) {
//        String access_token = jwtService.createAccessToken(loginReq.getUser_id());
//        String refresh_token = jwtService.createRefreshToken(loginReq.getUser_id());
//
//        LoginRes loginRes = new LoginRes(access_token, refresh_token);
//        return new BaseResponse<>(loginRes);
//    }
}
