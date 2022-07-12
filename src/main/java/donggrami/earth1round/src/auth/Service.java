package donggrami.earth1round.src.auth;

import donggrami.earth1round.config.BaseException;
import static donggrami.earth1round.config.BaseResponseStatus.*;

import donggrami.earth1round.src.auth.model.PostUserRes;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class Service {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final Dao dao;

    public User postUserRes;

    @Transactional
    public User createUser(Dao dao, HashMap userInfo) {
        return userRepository.save(dao.toEntity(userInfo));
    }

    public Long createUser(HashMap userInfo) throws BaseException {
        try{
            //이미 db에 정보가 등록된 회원인지 확인
            String email = userInfo.get("email").toString();
            User existence = userRepository.getByEmail("choiwy0906@gmail.com");
//            System.out.println("user_id : " + existence.getUser_id());
//            System.out.println("user_info : " + existence);

            //등록되어 있다면 해당 user_id 받아오기
            if(existence != null){
                postUserRes = existence;
                System.out.println(postUserRes);
                return new Long(postUserRes.getUser_id());
            }
            System.out.println(postUserRes);

            //등록되어 있지 않다면 insert하고 user_id 받아오기
            postUserRes = createUser(dao, userInfo);
//            System.out.println(postUserRes);
            return new Long(postUserRes.getUser_id());

        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
