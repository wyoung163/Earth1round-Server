package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.domain.entity.Custom;
import donggrami.earth1round.src.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomRepository extends JpaRepository<Custom, Long> {
    Optional<Custom> findByUser(User user);
}
