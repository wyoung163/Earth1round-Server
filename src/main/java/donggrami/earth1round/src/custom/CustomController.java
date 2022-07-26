package donggrami.earth1round.src.custom;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.custom.model.GetCustomRes;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/custom")
public class CustomController {
    @Autowired
    private final CustomService customService;
    @Autowired
    private final JwtService jwtService;

    /**
     * Get Custom Number API
     * [GET] /custom
     * @return BaseResponse<GetCustomRes>
     */
    @GetMapping("")
    public BaseResponse<GetCustomRes> getCustom() {
        try {
            Long user_id = jwtService.getUserId();
            GetCustomRes getCustomRes = customService.retrieveCustom(user_id);

            return new BaseResponse<>(getCustomRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
