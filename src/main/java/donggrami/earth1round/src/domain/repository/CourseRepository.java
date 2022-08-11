package donggrami.earth1round.src.domain.repository;

import donggrami.earth1round.src.course.model.GetCourseListRes;
import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course findByUserAndStatus(User user, Course.CourseStatus status);
    List<Course> findByUser(User userId);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Course c SET c.status = :courseStatus WHERE c.course_id = :id")
    int updateStatus(@Param("courseStatus")Course.CourseStatus courseStatus, @Param("id")Long id);

//    @Modifying
    @Query(value = "SELECT c.course_id " +
            "FROM Course c WHERE c.status = 'COMPLETE' AND c.user = :userId ")
    List<Course> getCourseList(@Param("userId")User userId);

}
