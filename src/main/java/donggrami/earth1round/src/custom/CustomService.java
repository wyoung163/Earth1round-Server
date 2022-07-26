package donggrami.earth1round.src.custom;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.auth.KakaoAuthService;
import donggrami.earth1round.src.custom.model.GetCustomRes;
import donggrami.earth1round.src.domain.entity.Custom;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.CustomRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

import static donggrami.earth1round.config.BaseResponseStatus.DATABASE_ERROR;
import static donggrami.earth1round.config.BaseResponseStatus.EMPTY_USER;

@Service
@RequiredArgsConstructor
public class CustomService {
    @Autowired
    private final CustomDao customDao;
    @Autowired
    private final CustomRepository customRepository;
    @Autowired
    private final UserRepository userRepository;

    public GetCustomRes retrieveCustom(Long user_id) throws BaseException{
        try {
            Optional<User> user = userRepository.findById(user_id);
            Optional<Custom> custom = customRepository.findByUser(user.get());
            Long custom_id = null;
            if (custom.isEmpty()) {
                custom_id = customRepository.save(customDao.toEntity(user.get())).getCustom_id();
            }
            else {
                custom_id = custom.get().getCustom_id();
            }

            return new GetCustomRes(custom_id);
        }catch (NoSuchElementException exception) {
            throw new BaseException(EMPTY_USER);
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
