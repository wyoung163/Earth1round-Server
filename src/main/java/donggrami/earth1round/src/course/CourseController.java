package donggrami.earth1round.src.course;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.course.model.PostCourseReq;
import donggrami.earth1round.src.course.model.PostCourseRes;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private final CourseService courseService;
    @Autowired
    private final JwtService jwtService;


    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostCourseRes> createPost(@RequestBody PostCourseReq postCourseReq) {
        if(postCourseReq.start_place_name == null){
            return new BaseResponse<>(POST_COURSES_EMPTY_STARTPLACE);
        }
        if(postCourseReq.end_place_name == null){
            return new BaseResponse<>(POST_COURSES_EMPTY_ENDPLACE);
        }
        if(postCourseReq.distance < 0){
            return new BaseResponse<>(POST_COURSES_WRONG_DISTANCE);
        }

        try{
            Long userIdByJwt = jwtService.getUserId();
            PostCourseRes postPostRes = courseService.createCourse(userIdByJwt, postCourseReq);
            return new BaseResponse<>(postPostRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
