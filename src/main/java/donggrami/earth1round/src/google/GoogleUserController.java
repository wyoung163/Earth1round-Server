package donggrami.earth1round.src.google;

import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.google.model.GoogleUserRes;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class GoogleUserController {

    private GoogleUserService userService;

    public GoogleUserController(GoogleUserService googleOauthService) {
        this.userService = googleOauthService;
    }

    @ResponseBody
    @RequestMapping(value="/login/google", method = {RequestMethod.GET, RequestMethod.POST})
    public BaseResponse<GoogleUserRes> oauthLogin(@Valid @RequestParam(value = "code", required=false, defaultValue="") String code) throws ParseException {
        GoogleUserRes googleUserRes = userService.oauthLogin(code);
        return new BaseResponse<>(googleUserRes);
    }
}
