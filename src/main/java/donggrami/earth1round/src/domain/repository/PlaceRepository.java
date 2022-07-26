package donggrami.earth1round.src.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

import donggrami.earth1round.src.domain.entity.Place;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaceRepository  extends JpaRepository<Place, Long> {
    Place findByPlaceName(String placeName);
}
