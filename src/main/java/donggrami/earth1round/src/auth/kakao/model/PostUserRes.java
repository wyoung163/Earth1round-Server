package donggrami.earth1round.src.auth.kakao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserRes {
    private String access_token;
    private String refresh_token;
    private Long user_id;
}