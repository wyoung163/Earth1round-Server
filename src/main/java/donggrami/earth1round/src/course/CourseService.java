package donggrami.earth1round.src.course;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.course.model.GetCourseRes;
import donggrami.earth1round.src.course.model.PatchCourseRes;
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

import static donggrami.earth1round.config.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CourseService {
    public final PlaceRepository placeRepository;
    public final CourseRepository courseRepository;
    public final UserRepository userRepository;
    private final CourseDao courseDao;
    private final JwtService jwtService;

    //active 상태의 현재 코스 확인
    public Course getPresentCourse(User user, Course.CourseStatus status) {
        return courseRepository.findByUserAndStatus(user, status);
    }

    //코스 저장하기(active 상태의 코스가 이미 존재한다면 기존 것 inactive로 변경하고 새로 저장)
    @Transactional
    public PostCourseRes createCourse(Long userIdByJwt, PostCourseReq postCourseReq) throws BaseException {
        try{
            User user = userRepository.getById(userIdByJwt);

            //해당 유저가 이미 진행 중인 코스가 있는지 확인
            Course presentCourse = courseRepository.findByUserAndStatus(user, Course.CourseStatus.ACTIVE);

            //해당 유저가 선택한 코스가 아직 완료되지 않은 상태일 때 새로운 코스로 변경하기를 원한다면
            if(presentCourse != null) {
                //기존 코스 status를 INACTIVE로 변경
                int updatedCourse = courseRepository.updateStatus(Course.CourseStatus.INACTIVE, presentCourse.getCourse_id());
                System.out.println(updatedCourse);

                //새로운 코스 할당
                Place startPlace = placeRepository.getById(postCourseReq.start_place_id);
                Place endPlace = placeRepository.getById(postCourseReq.end_place_id);

                Course course = courseRepository.save(courseDao.insertCourse(user, startPlace, endPlace, postCourseReq.distance));
                return new PostCourseRes(course.getCourse_id(), startPlace.getPlace_id(), startPlace.getPlaceName(), endPlace.getPlace_id(), endPlace.getPlaceName());
            } else {
                Place startPlace = placeRepository.getById(postCourseReq.start_place_id);
                Place endPlace = placeRepository.getById(postCourseReq.end_place_id);

                Course course = courseRepository.save(courseDao.insertCourse(user, startPlace, endPlace, postCourseReq.distance));
                return new PostCourseRes(course.getCourse_id(), startPlace.getPlace_id(), startPlace.getPlaceName(), endPlace.getPlace_id(), endPlace.getPlaceName());
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //현재 진행 중인 코스 불러오기
    @Transactional
    public GetCourseRes getCourse(Long userIdByJwt) throws BaseException {
        User user = userRepository.getById(userIdByJwt);

        //해당 유저의 진행 중인 코스 불러오기
        Course presentCourse = getPresentCourse(user, Course.CourseStatus.ACTIVE);

        if(presentCourse == null){
            throw new BaseException(GET_COURSE_EMPTY);
        };

        try{
            Long startPlaceId = presentCourse.getStart_place().getPlace_id();
            Place startPlace = placeRepository.getById(startPlaceId);
            Long endPlaceId = presentCourse.getEnd_place().getPlace_id();
            Place endPlace = placeRepository.getById(endPlaceId);

            return new GetCourseRes(
                    presentCourse.getCourse_id(),
                    startPlace.getPlace_id(),
                    startPlace.getPlaceName(),
                    endPlace.getPlace_id(),
                    endPlace.getPlaceName(),
                    presentCourse.getDistance(),
                    presentCourse.getStart_date());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //코스 완료하기
    @Transactional
    public PatchCourseRes patchCourse(Long userIdByJwt) throws BaseException {
        User user = userRepository.getById(userIdByJwt);

        //해당 유저의 진행 중인 코스 불러오기
        Course presentCourse = getPresentCourse(user, Course.CourseStatus.ACTIVE);

        if(presentCourse == null){
            throw new BaseException(GET_COURSE_EMPTY);
        };

        try{
            //COMPLETE로 STATUS 변경
            int updatedCourse = courseRepository.updateStatus(Course.CourseStatus.COMPLETE, presentCourse.getCourse_id());
            return new PatchCourseRes(presentCourse.getCourse_id());
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
