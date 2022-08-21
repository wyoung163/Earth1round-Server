package donggrami.earth1round.src.auth;

import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.auth.google.GoogleUserService;
import donggrami.earth1round.utils.jwt.JwtService;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private GoogleUserService googleUserService;
    private final JwtService jwtService;

    public AuthController(GoogleUserService googleUserService, JwtService jwtService) {
        this.googleUserService = googleUserService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @DeleteMapping(value="/unlink")
    public BaseResponse<String> withdrawal() throws ParseException {
        Long userIdByJwt = jwtService.getUserId();
        googleUserService.googleWithdrawal(userIdByJwt);
        String result = "회원 탈퇴가 완료되었습니다.";
        return new BaseResponse<>(result);
    }
}
