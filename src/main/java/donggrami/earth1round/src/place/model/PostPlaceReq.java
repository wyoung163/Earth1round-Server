package donggrami.earth1round.src.place.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostPlaceReq {
    private String place_name;
    private Double latitude;
    private Double longitude;
}
