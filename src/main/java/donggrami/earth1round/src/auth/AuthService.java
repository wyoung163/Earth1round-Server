package donggrami.earth1round.src.auth;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.domain.entity.Course;
import donggrami.earth1round.src.domain.entity.Custom;
import donggrami.earth1round.src.domain.entity.Profile;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static donggrami.earth1round.config.BaseResponseStatus.RESPONSE_ERROR;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public void userWithdrawal(Long user_id) throws BaseException{
        try{
            //Settings
            if (userRepository.findById(user_id) == null){
                throw new BaseException(RESPONSE_ERROR, HttpStatus.BAD_REQUEST);
            }

            userRepository.deleteById(user_id);
        } catch (Exception exception) {
            throw new BaseException(RESPONSE_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
