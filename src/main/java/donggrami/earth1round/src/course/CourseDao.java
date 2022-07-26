package donggrami.earth1round.src.course;

import donggrami.earth1round.src.course.model.PostCourseReq;
import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Place;
import donggrami.earth1round.src.domain.entity.User;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Repository
@NoArgsConstructor
public class CourseDao {
    private Timestamp created_at = new Timestamp(new Date().getTime());
    private Timestamp updated_at = new Timestamp(new Date().getTime());

    public Course insertCourse(User user, Place startPlace, Place endPlace, double distance) {
        Course courseEntity = Course.builder()
                .user(user)
                .start_place(startPlace)
                .end_place(endPlace)
                .distance(distance)
                .start_date(created_at)
                .end_date(null)
                .created_at(created_at)
                .updated_at(updated_at)
                .build();
        return courseEntity;
    }
}
