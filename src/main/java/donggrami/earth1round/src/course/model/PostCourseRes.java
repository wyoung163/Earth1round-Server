package donggrami.earth1round.src.course.model;

import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCourseRes {
    public Course course_id;
    public Place start_place_id;
    public String start_place_name;
    public Place end_place_id;
    public String end_place_name;
}
