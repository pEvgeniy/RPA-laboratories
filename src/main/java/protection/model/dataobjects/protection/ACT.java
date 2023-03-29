package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;
import protection.model.common.DataAttribute;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description Срабатывание защиты
 */
public class ACT extends DATA {

    @Getter @Setter
    private DataAttribute<Boolean> general = new DataAttribute<>();

    @Getter @Setter
    private DataAttribute<Boolean> phsA = new DataAttribute<>();

    @Getter @Setter
    private DataAttribute<Boolean> phsB = new DataAttribute<>();

    @Getter @Setter
    private DataAttribute<Boolean> phsC = new DataAttribute<>();
}
