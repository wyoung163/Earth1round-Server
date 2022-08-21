package donggrami.earth1round.src.profiles;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.auth.google.GoogleUserService;
import donggrami.earth1round.src.profiles.model.PatchProfilesReq;
import donggrami.earth1round.src.profiles.model.GetMypageProfilesRes;
import donggrami.earth1round.src.profiles.model.GetRoomProfilesRes;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profiles")
public class ProfilesController {

    @Autowired
    private final ProfilesService userService;
    @Autowired
    private final JwtService jwtService;

    private Logger logger = LoggerFactory.getLogger(GoogleUserService.class);

    @PatchMapping("/nickname")
    public BaseResponse<String> patchNickname(@Valid @RequestBody PatchProfilesReq patchUserReq) {
        Long user_id = jwtService.getUserId();
        userService.modifyNickname(user_id, patchUserReq);
        String result = "유저 닉네임을 수정하였습니다.";
        return new BaseResponse<>(result);
    }
    @GetMapping("/mypage")
    public BaseResponse<GetMypageProfilesRes> getMypageProfile(){
        try {
            Long user_id = jwtService.getUserId();
            GetMypageProfilesRes getProfileRes = userService.getMypageProfile(user_id);

            return new BaseResponse<>(getProfileRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    @GetMapping("/room")
    public BaseResponse<GetRoomProfilesRes> getRoomProfile(){
        try {
            Long user_id = jwtService.getUserId();
            GetRoomProfilesRes getRoomProfileRes = userService.getRoomProfile(user_id);

            return new BaseResponse<>(getRoomProfileRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
