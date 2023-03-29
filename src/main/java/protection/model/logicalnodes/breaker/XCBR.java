package protection.model.logicalnodes.breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.controls.Codedenum;
import protection.model.dataobjects.controls.DPC;
import protection.model.dataobjects.controls.SPC;
import protection.model.logicalnodes.common.LN;

public class XCBR extends LN {
    @Getter @Setter
    private DPC Pos = new DPC();

    @Getter @Setter
    private SPC BlkOpn = new SPC();

    @Getter @Setter
    private SPC BlkCls = new SPC();

    @Override
    public void process() {
        if (Pos.getCtlVal().getValue() && !BlkOpn.getStVal().getValue()) {
//            Блокировка на выключение
            Pos.getStVal().setValue(Codedenum.OFF);
//            BlkOpn.getStVal().setValue(false);
        } else {
//            Блокировка на включение
            Pos.getStVal().setValue(Codedenum.ON);
//            BlkCls.getStVal().setValue(false);
        }
    }
}
