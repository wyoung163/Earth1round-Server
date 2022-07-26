package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByUserAndStatus(User user, Course.CourseStatus status);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Course c SET c.status = :courseStatus WHERE c.course_id = :id")
    int updateStatus(@Param("courseStatus")Course.CourseStatus courseStatus, @Param("id")Long id);
}
