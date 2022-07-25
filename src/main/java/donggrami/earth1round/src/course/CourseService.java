package donggrami.earth1round.src.course;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.course.model.PostCourseReq;
import donggrami.earth1round.src.course.model.PostCourseRes;
import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Place;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.PlaceRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static donggrami.earth1round.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class CourseService {
    public final PlaceRepository placeRepository;
    public final UserRepository userRepository;
    private final CourseDao courseDao;
    private final JwtService jwtService;
    public PostCourseRes createCourse(Long userIdByJwt, PostCourseReq postCourseReq) throws BaseException {
        try{
            Place startPlaceId = placeRepository.getByPlaceName(postCourseReq.start_place_name);
            Place endPlaceId = placeRepository.getByPlaceName(postCourseReq.end_place_name);
            User userId = userRepository.getById(userIdByJwt);
            Course courseId = courseDao.insertCourse(userId, startPlaceId, endPlaceId, postCourseReq);
            return new PostCourseRes(courseId, startPlaceId, postCourseReq.start_place_name, endPlaceId, postCourseReq.end_place_name);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
