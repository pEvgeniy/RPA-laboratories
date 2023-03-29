package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class Vector extends DATA {

    @Getter @Setter
    private AnalogueValue mag = new AnalogueValue();

    @Getter @Setter
    private AnalogueValue ang = new AnalogueValue();
}
