package donggrami.earth1round.src.course;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.course.model.GetCourseListRes;
import donggrami.earth1round.src.course.model.GetCourseRes;
import donggrami.earth1round.src.course.model.PostCourseReq;
import donggrami.earth1round.src.course.model.PostCourseRes;
import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.google.GoogleUserService;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class CourseController {
    @Autowired
    private final CourseService courseService;
    @Autowired
    private final JwtService jwtService;

    private Logger logger = LoggerFactory.getLogger(GoogleUserService.class);

    @ResponseBody
    @PostMapping("/courses")
    public BaseResponse<PostCourseRes> createCourse(@RequestBody PostCourseReq postCourseReq) {
        if(postCourseReq.start_place_id == null){
            return new BaseResponse<>(POST_COURSES_EMPTY_STARTPLACE);
        }
        if(postCourseReq.end_place_id == null){
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

    //완료한 이전 코스 목록 불러오기
    @ResponseBody
    @GetMapping("/courses")
    public BaseResponse<List<GetCourseListRes>> showCourseList() {
        try{
            Long userIdByJwt = jwtService.getUserId();
            List<GetCourseListRes> getCourseRes = courseService.getCourseList(userIdByJwt);
            return new BaseResponse<>(getCourseRes);
        } catch(BaseException exception){
            return new BaseResponse<>((exception.getStatus()));
        }
    }
}
