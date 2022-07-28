package donggrami.earth1round.src.place;

import donggrami.earth1round.src.domain.entity.Place;
import donggrami.earth1round.src.place.model.PostPlaceReq;
import lombok.NoArgsConstructor;
import org.locationtech.jts.io.ParseException;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
@NoArgsConstructor
public class PlaceDao {
    private Timestamp created_at = new Timestamp(new Date().getTime());
    private Timestamp updated_at = new Timestamp(new Date().getTime());

    public Place toEntity(PostPlaceReq postPlaceReq) throws ParseException {
        return Place.builder()
                .placeName(postPlaceReq.getPlace_name())
                .latitude(postPlaceReq.getLatitude())
                .longitude(postPlaceReq.getLongitude())
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
    }
}
