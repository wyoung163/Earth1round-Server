package donggrami.earth1round.src.custom;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.auth.KakaoAuthService;
import donggrami.earth1round.src.custom.model.GetCustomRes;
import donggrami.earth1round.src.custom.model.PatchCustomReq;
import donggrami.earth1round.src.domain.entity.Custom;
import donggrami.earth1round.src.domain.entity.User;
import donggrami.earth1round.src.domain.repository.CustomRepository;
import donggrami.earth1round.src.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
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

    public GetCustomRes retrieveCustom(Long user_id) {
        try {
            Optional<User> user = userRepository.findById(user_id);
            Optional<Custom> custom = customRepository.findByUser(user.get());
            int custom_num = 0;
            if (custom.isEmpty()) {
                custom_num = customRepository.save(customDao.toEntity(user.get())).getCustom_num();
            }
            else {
                custom_num = custom.get().getCustom_num();
            }

            return new GetCustomRes(custom_num);
        }catch (NoSuchElementException exception) {
            throw new BaseException(EMPTY_USER, HttpStatus.BAD_REQUEST);
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR, HttpStatus.BAD_REQUEST);
        }
    }

    public void modifyCustom(Long user_id, PatchCustomReq patchCustomReq) {
        try {
            Optional<User> user = userRepository.findById(user_id);
            Optional<Custom> custom = customRepository.findByUser(user.get());
            if (custom.isPresent()) {
                Custom patch_custom = custom.get();
                patch_custom.setCustom_num(patchCustomReq.getCustom_num());
                patch_custom.setUpdated_at(new Timestamp(new Date().getTime()));
                customRepository.save(patch_custom);
            }
        } catch (NoSuchElementException exception) {
            throw new BaseException(EMPTY_USER, HttpStatus.BAD_REQUEST);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR, HttpStatus.BAD_REQUEST);
        }
    }
}
