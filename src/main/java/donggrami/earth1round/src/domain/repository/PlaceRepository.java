package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.domain.entity.Place;
import donggrami.earth1round.src.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository  extends JpaRepository<Place, Long> {
    Place getByPlaceName(String placeName);
}
