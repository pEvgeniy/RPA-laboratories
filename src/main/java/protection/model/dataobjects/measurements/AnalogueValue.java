package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.common.DataAttribute;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class AnalogueValue extends DATA {

    @Getter @Setter
    private DataAttribute<Double> f = new DataAttribute<>(0d);
}
