package donggrami.earth1round.src.course.model;

import donggrami.earth1round.src.domain.entity.Place;
import donggrami.earth1round.src.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetCourseListRes {
    public long course_id;
    public long user_id;
    public long start_place_id;
    public long end_place_id;
    public double distance;
    public Date start_date;
    public Date end_date;
}
