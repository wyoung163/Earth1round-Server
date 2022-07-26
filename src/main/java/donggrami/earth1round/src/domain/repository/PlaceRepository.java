package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.place.model.GetPlacesRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import donggrami.earth1round.src.domain.entity.Place;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository  extends JpaRepository<Place, Long> {
    @Query(value = "select new donggrami.earth1round.src.place.model.GetPlacesRes(p.place_id, p.place_name," +
            " p.latitude, p.longitude) from Place p where p.status = 'ACTIVE'")
    List<GetPlacesRes> findPlaces();
    Place findByPlaceName(String placeName);
}
