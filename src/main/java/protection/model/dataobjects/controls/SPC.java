package protection.model.dataobjects.controls;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

public class SPC {

    @Getter @Setter
    private DataAttribute<Boolean> stVal = new DataAttribute<>( false);
}
