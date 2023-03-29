package protection.model.dataobjects.controls;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

public class DPC {

    @Getter @Setter
    private DataAttribute<Codedenum> stVal = new DataAttribute<>(Codedenum.INTERMEDIATE);

    @Getter @Setter
    private DataAttribute<Boolean> ctlVal = new DataAttribute<>(true);
}
