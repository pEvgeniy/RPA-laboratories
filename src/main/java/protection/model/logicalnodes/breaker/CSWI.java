package protection.model.logicalnodes.breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.controls.DPC;
import protection.model.dataobjects.protection.ACT;
import protection.model.logicalnodes.common.LN;

public class CSWI extends LN {
    /*
     * Open/close switch
     */
    @Getter @Setter
    private ACT OpOpn1  = new ACT();
    @Getter @Setter
    private ACT OpOpn2  = new ACT();

    @Getter @Setter
    private ACT OpCls = new ACT();

    /*
     * Switch
     */
    @Getter @Setter
    private DPC Pos = new DPC();

    @Getter @Setter
    private DPC PosA = new DPC();

    @Getter @Setter
    private DPC PosB = new DPC();

    @Getter @Setter
    private DPC PosC = new DPC();

    @Override
    public void process() {
        if (OpOpn1.getGeneral().getValue()
                || OpOpn2.getGeneral().getValue()) {
            Pos.getCtlVal().setValue(false);
        } else {
            Pos.getCtlVal().setValue(true);
        }
    }
}
