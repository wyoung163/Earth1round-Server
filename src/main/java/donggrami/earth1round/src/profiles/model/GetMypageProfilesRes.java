package donggrami.earth1round.src.profiles.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetMypageProfilesRes {
    private String name;
    private String nickname;
    private String profileImg;
}
