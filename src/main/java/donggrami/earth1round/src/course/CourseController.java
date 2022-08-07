package donggrami.earth1round.src.course;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.course.model.*;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
public class CourseController {
    @Autowired
    private final CourseService courseService;
    @Autowired
    private final JwtService jwtService;

    /**
     * Post Course API
     * [POST] /courses
     *
     * @return BaseResponse<PostCourseRes>
     */
    @ResponseBody
    @PostMapping("/courses")
    public BaseResponse<PostCourseRes> createCourse(@RequestBody PostCourseReq postCourseReq) {
        if (postCourseReq.start_place_id == null) {
            throw new BaseException(POST_COURSES_EMPTY_STARTPLACE, HttpStatus.BAD_REQUEST);
        }

        if (postCourseReq.end_place_id == null) {
            throw new BaseException(POST_COURSES_EMPTY_ENDPLACE, HttpStatus.BAD_REQUEST);
        }

        if (postCourseReq.distance < 0) {
            throw new BaseException(POST_COURSES_WRONG_DISTANCE, HttpStatus.BAD_REQUEST);
        }

        Long userIdByJwt = jwtService.getUserId();
        PostCourseRes postCourseRes = courseService.createCourse(userIdByJwt, postCourseReq);
        return new BaseResponse<>(postCourseRes);
    }

    /**
     * Get Course API
     * [GET] /course
     *
     * @return BaseResponse<GetCourseRes>
     */
    @ResponseBody
    @GetMapping("/course")
    public BaseResponse<GetCourseRes> showCourse() {
        Long userIdByJwt = jwtService.getUserId();
        GetCourseRes getCourseRes = courseService.getCourse(userIdByJwt);
        return new BaseResponse<>(getCourseRes);
    }

    /**
     * Patch Course API
     * [PATCH] /course
     *
     * @return BaseResponse<PatchCourseRes>
     */
    @ResponseBody
    @PatchMapping("/course")
    public BaseResponse<PatchCourseRes> completeCourse() {
        Long userIdByJwt = jwtService.getUserId();
        PatchCourseRes patchCourseRes = courseService.patchCourse(userIdByJwt);
        return new BaseResponse<>(patchCourseRes);

    }
}
