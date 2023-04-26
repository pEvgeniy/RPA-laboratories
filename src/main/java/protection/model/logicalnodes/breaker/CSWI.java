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
    private ACT OpOpn3  = new ACT();
    @Getter @Setter
    private ACT OpOpn4  = new ACT();
    @Getter @Setter
    private ACT OpOpn5  = new ACT();
    @Getter @Setter
    private ACT OpOpn6  = new ACT();


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

    private int counterAPV = 0;
    private boolean APVinProcess = false;
    private boolean APVoperated = false;

    @Override
    public void process() {
        boolean anyOpnGeneralValue = OpOpn1.getGeneral().getValue()
                || OpOpn2.getGeneral().getValue()
                || OpOpn3.getGeneral().getValue()
                || OpOpn4.getGeneral().getValue()
                || OpOpn5.getGeneral().getValue()
                || OpOpn6.getGeneral().getValue();

        if (counterAPV == 80) {
            Pos.getCtlVal().setValue(true);
            APVinProcess = true;
            counterAPV = 0;
        }
        if (APVinProcess) {
            counterAPV++;
            if (counterAPV < 50) {
                return;
            } else {
                APVinProcess = false;
                APVoperated = true;
            }
        }

        if (anyOpnGeneralValue) {
            Pos.getCtlVal().setValue(false);
            if (!APVoperated) {
                counterAPV++;
            }
        } else {
            Pos.getCtlVal().setValue(true);
        }
    }
}
