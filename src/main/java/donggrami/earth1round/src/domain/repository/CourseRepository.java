package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
