package donggrami.earth1round.src.auth.google;

import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.auth.google.model.GoogleUserRes;
import donggrami.earth1round.utils.jwt.JwtService;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GoogleUserController {

    private GoogleUserService userService;
    private final JwtService jwtService;

    public GoogleUserController(GoogleUserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @ResponseBody
    @RequestMapping(value="/login/google", method = {RequestMethod.GET, RequestMethod.POST})
    public BaseResponse<GoogleUserRes> oauthLogin(@Valid @RequestParam(value = "code", required=false, defaultValue="") String code) throws ParseException {
        GoogleUserRes googleUserRes = userService.oauthLogin(code);
        return new BaseResponse<>(googleUserRes);
    }


    //Moved to AuthController
//    @ResponseBody
//    @DeleteMapping(value="/withdrawal")
//    public BaseResponse<String> withdrawal() throws ParseException {
//        Long userIdByJwt = jwtService.getUserId();
//        userService.googleWithdrawal(userIdByJwt);
//        String result = "회원 탈퇴가 완료되었습니다.";
//        return new BaseResponse<>(result);
//    }
}
