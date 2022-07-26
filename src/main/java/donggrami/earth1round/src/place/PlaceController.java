package donggrami.earth1round.src.place;

import donggrami.earth1round.config.BaseException;
import donggrami.earth1round.config.BaseResponse;
import donggrami.earth1round.src.place.model.GetPlacesRes;
import donggrami.earth1round.src.place.model.PostPlaceReq;
import donggrami.earth1round.src.place.model.PostPlaceRes;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static donggrami.earth1round.config.BaseResponseStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {
    @Autowired
    private final PlaceService placeService;

    /**
     * Get Places list API
     * [GET] /places
     * @return BaseResponse<List<GetPlacesRes>>
     */
    @GetMapping("")
    public BaseResponse<List<GetPlacesRes>> getPlaces() {
        try {
            List<GetPlacesRes> getPlacesRes = placeService.retrievePlaces();
            return new BaseResponse<>(getPlacesRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * Post Place API (for dev)
     * [POST] /places/dev
     * @return BaseResponse<PostPlaceRes>
     */
    @ResponseBody
    @PostMapping("/dev")
    public BaseResponse<PostPlaceRes> createPlace(@RequestBody PostPlaceReq postPlaceReq) {
        try {
            if(postPlaceReq.getPlace_name() == null) {
                return new BaseResponse<>(POST_PLACE_EMPTY_PLACE_NAME);
            }
            if(postPlaceReq.getLatitude() == null) {
                return new BaseResponse<>(POST_PLACE_EMPTY_LATITUDE);
            }
            if(postPlaceReq.getLongitude() == null) {
                return new BaseResponse<>(POST_PLACE_EMPTY_LONGITUDE);
            }
            PostPlaceRes postPlaceRes = placeService.createPlace(postPlaceReq);
            return new BaseResponse<>(postPlaceRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
}
