package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

@Getter @Setter
public class ENG {
    private DataAttribute<Direction> stVal = new DataAttribute<>(Direction.UNKNOWN);
}
