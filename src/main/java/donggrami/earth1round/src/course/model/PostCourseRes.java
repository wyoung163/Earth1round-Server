package donggrami.earth1round.src.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCourseRes {
    public long course_id;
    public long start_place_id;
    public String start_place_name;
    public long end_place_id;
    public String end_place_name;
}
