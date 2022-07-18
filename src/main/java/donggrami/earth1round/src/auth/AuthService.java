package donggrami.earth1round.src.auth;

import donggrami.earth1round.config.BaseException;
import static donggrami.earth1round.config.BaseResponseStatus.*;

import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.auth.model.PostTokenRes;
import donggrami.earth1round.src.auth.model.PostUserRes;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.UserRepository;
import donggrami.earth1round.utils.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class AuthService {
    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthDao dao;

    @Autowired
    private final JwtService jwtService;

    public User postUserRes;


    @Transactional
    public User createUser(AuthDao dao, HashMap userInfo) {
//        User user = userRepository.save(dao.toEntity(userInfo));
        return userRepository.save(dao.toEntity(userInfo));
    }

    public PostUserRes createUser(HashMap userInfo) throws BaseException {
        try{
            //이미 db에 정보가 등록된 회원인지 확인
            String email = userInfo.get("email").toString();
            User existence = userRepository.getByEmail(email);


            //등록되어 있다면 해당 user_id 받아오기
            if(existence != null){
                postUserRes = existence;
//                System.out.println(postUserRes);
                Long user_id = postUserRes.getUser_id();
                //jwt 발급
                String access_token = jwtService.createAccessToken(user_id);
                String refresh_token = jwtService.createRefreshToken(user_id);
                return new PostUserRes(access_token, refresh_token, user_id);
            }

            //등록되어 있지 않다면 insert하고 user_id 받아오기
            postUserRes = createUser(dao, userInfo);
//            System.out.println(postUserRes);
            Long user_id = postUserRes.getUser_id();

            try{
                //jwt 발급
                String access_token = jwtService.createAccessToken(user_id);
                String refresh_token = jwtService.createRefreshToken(user_id);
                return new PostUserRes(access_token, refresh_token, user_id);
            } catch (Exception exception) {
                System.out.println(exception);
                throw new BaseException(DATABASE_ERROR);
            }
        } catch (Exception exception) {
            System.out.println(exception);
            throw new BaseException(DATABASE_ERROR);
        }
    }



}
