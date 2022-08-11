package donggrami.earth1round.src.custom.model;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatchCustomReq {
    @Positive
    @NotNull
    private int custom_num;
}
