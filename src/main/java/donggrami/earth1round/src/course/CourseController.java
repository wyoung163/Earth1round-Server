package donggrami.earth1round.src.course;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.course.model.GetCourseRes;
import donggrami.earth1round.src.course.model.PostCourseReq;
import donggrami.earth1round.src.course.model.PostCourseRes;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class CourseController {
    @Autowired
    private final CourseService courseService;
    @Autowired
    private final JwtService jwtService;

    @ResponseBody
    @PostMapping("/courses")
    public BaseResponse<PostCourseRes> createCourse(@RequestBody PostCourseReq postCourseReq) {
        if(postCourseReq.start_place_name.isEmpty()){
            return new BaseResponse<>(POST_COURSES_EMPTY_STARTPLACE);
        }
        if(postCourseReq.end_place_name.isEmpty()){
            return new BaseResponse<>(POST_COURSES_EMPTY_ENDPLACE);
        }
        if(postCourseReq.distance < 0){
            return new BaseResponse<>(POST_COURSES_WRONG_DISTANCE);
        }

        try{
            Long userIdByJwt = jwtService.getUserId();
            PostCourseRes postCourseRes = courseService.createCourse(userIdByJwt, postCourseReq);
            return new BaseResponse<>(postCourseRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @GetMapping("/course")
    public BaseResponse<GetCourseRes> showCourse() {
        try{
            Long userIdByJwt = jwtService.getUserId();
            GetCourseRes getCourseRes = courseService.getCourse(userIdByJwt);
            return new BaseResponse<>(getCourseRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
