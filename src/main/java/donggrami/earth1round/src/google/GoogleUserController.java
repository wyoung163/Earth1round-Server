package donggrami.earth1round.src.google;

import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.google.model.GoogleUserRes;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class GoogleUserController {

    private GoogleUserService userService;

    public GoogleUserController(GoogleUserService googleOauthService) {
        this.userService = googleOauthService;
    }

    @GetMapping("/google")
    public BaseResponse<GoogleUserRes> oauthLogin(@RequestParam(value = "code",required=false,defaultValue="") String code) throws ParseException {
        GoogleUserRes googleUserRes = userService.oauthLogin(code);
        return new BaseResponse<>(googleUserRes);
    }
}
