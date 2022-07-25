package donggrami.earth1round.src.google;

import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.google.model.GoogleUserRes;
import donggrami.earth1round.src.google.model.TokenResponse;
import donggrami.earth1round.utils.jwt.JwtService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserService userService;
    private JwtService jwtService;
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserController(UserService googleOauthService) {
        this.userService = googleOauthService;
    }

    @GetMapping("/login/google")
    public BaseResponse<GoogleUserRes> oauthLogin(@RequestParam(value = "code",required=false,defaultValue="") String code) throws ParseException {
        GoogleUserRes googleUserRes = userService.oauthLogin(code);
        return new BaseResponse<>(googleUserRes);
    }
}
