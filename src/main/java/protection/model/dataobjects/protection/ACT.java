package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.common.DataAttribute;

public class ACT extends DATA {

    @Getter @Setter
    private DataAttribute<Boolean> general = new DataAttribute<>(false);

    @Getter @Setter
    private DataAttribute<Boolean> phsA = new DataAttribute<>(false);

    @Getter @Setter
    private DataAttribute<Boolean> phsB = new DataAttribute<>(false);

    @Getter @Setter
    private DataAttribute<Boolean> phsC = new DataAttribute<>(false);
}
