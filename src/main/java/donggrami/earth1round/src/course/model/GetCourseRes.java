package donggrami.earth1round.src.course.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseRes {
    public long course_id;
    public String start_place_name;
    public String end_place_name;
    public double distance;
    public Date start_date;
}
