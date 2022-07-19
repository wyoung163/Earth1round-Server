package donggrami.earth1round.src.place;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.src.domain.repository.PlaceRepository;
import donggrami.earth1round.src.place.model.GetPlacesRes;
import donggrami.earth1round.src.place.model.PostPlaceReq;
import donggrami.earth1round.src.place.model.PostPlaceRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import java.util.List;

import static donggrami.earth1round.config.BaseResponseStatus.DATABASE_ERROR;

@Service
@RequiredArgsConstructor
public class PlaceService {
    @Autowired
    private final PlaceRepository placeRepository;
    @Autowired
    private final PlaceDao placeDao;

    public List<GetPlacesRes> retrievePlaces() throws BaseException {
        try{
            return placeRepository.findPlaceByStatus();
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    public PostPlaceRes createPlace(PostPlaceReq postPlaceReq) throws BaseException {
//        try{
//            placeRepository.save(placeDao.toEntity);
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }
}
