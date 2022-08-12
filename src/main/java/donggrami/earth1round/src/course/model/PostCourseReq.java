package donggrami.earth1round.src.course.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class PostCourseReq {
    @Positive
    @NotNull
    public Long start_place_id;

    @Positive
    @NotNull
    public Long end_place_id;

    @Positive
    @NotNull
    public double distance;
}
