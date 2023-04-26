package protection.model.dataobjects.protection;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description Пуск защиты
 */
public class ACD extends ACT {

    @Getter @Setter
    private DataAttribute<Direction> dirGeneral = new DataAttribute<>();
    {
        dirGeneral.setName("dirGeneral");
        dirGeneral.setParent(this);
        this.getChildren().add(dirGeneral);
    }

    @Getter @Setter
    private DataAttribute<Direction> dirPhsA = new DataAttribute<>();

    @Getter @Setter
    private DataAttribute<Direction> dirPhsB = new DataAttribute<>();

    @Getter @Setter
    private DataAttribute<Direction> dirPhsC = new DataAttribute<>();
}
