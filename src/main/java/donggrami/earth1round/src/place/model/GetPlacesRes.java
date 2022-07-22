package donggrami.earth1round.src.place.model;

import lombok.*;
import org.springframework.data.geo.Point;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetPlacesRes {
    private Long place_id;
    private String place_name;
    private double latitude;
    private double longitude;
}
