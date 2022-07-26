package donggrami.earth1round.src.course;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.course.model.PostCourseReq;
import donggrami.earth1round.src.course.model.PostCourseRes;
import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Place;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.CourseRepository;
import donggrami.earth1round.src.domain.repository.PlaceRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static donggrami.earth1round.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class CourseService {
    public final PlaceRepository placeRepository;
    public final CourseRepository courseRepository;
    public final UserRepository userRepository;
    private final CourseDao courseDao;
    private final JwtService jwtService;

    @Transactional
    public PostCourseRes createCourse(Long userIdByJwt, PostCourseReq postCourseReq) throws BaseException {
        try{
            User user = userRepository.getById(userIdByJwt);
            System.out.println(user.getUser_id());
            Place startPlace = placeRepository.findByPlaceName(postCourseReq.start_place_name);
            Place endPlace = placeRepository.findByPlaceName(postCourseReq.end_place_name);

            Course course = courseRepository.save(courseDao.insertCourse(user, startPlace, endPlace, postCourseReq.distance));
            return new PostCourseRes(course.getCourse_id(), startPlace.getPlace_id(), postCourseReq.start_place_name, endPlace.getPlace_id(), postCourseReq.end_place_name);
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
