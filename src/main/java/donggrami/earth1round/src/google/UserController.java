package donggrami.earth1round.src.google;

import donggrami.earth1round.src.google.model.TokenResponse;
import donggrami.earth1round.utils.jwt.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    private UserService userService;
    private JwtService jwtService;

    public UserController(UserService googleOauthService) {
        this.userService = googleOauthService;
    }

    @GetMapping("/login/google")
    public ResponseEntity<TokenResponse> oauthLogin(String code) {
        String token = userService.oauthLogin(code);
        return new ResponseEntity(new TokenResponse(token, "bearer"), HttpStatus.OK);
    }

    @GetMapping("/refresh/google")
    public ResponseEntity<TokenResponse> refreshToken (@RequestBody String refreshToken) {

        Long ID = jwtService.getUserIdWithRefreshToken(refreshToken);
        String token = jwtService.createRefreshToken(ID);

        return new ResponseEntity(new TokenResponse(token, "bearer"), HttpStatus.OK);
    }

}
