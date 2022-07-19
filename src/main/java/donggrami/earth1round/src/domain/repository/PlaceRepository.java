package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.domain.entity.Place;
import donggrami.earth1round.src.place.model.GetPlacesRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {
//    @Query(value = "select p.place_id as place_id, p.place_name as place_name, p.location as location " +
//            "from Place p where p.status = 'ACTIVE'")
    @Query(value = "select new donggrami.earth1round.src.place.model.GetPlacesRes(p.place_id, p.place_name, p.location)" +
            " from Place p where p.status = 'ACTIVE'")
    List<GetPlacesRes> findPlaceByStatus();


}
