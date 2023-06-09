package protection.model.logicalnodes.breaker;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.controls.DPC;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.settings.ING;
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
    @Getter @Setter
    public ING OpDlTmms1 = new ING();
    @Getter @Setter
    public ING OpDlTmms2 = new ING();
    @Getter @Setter
    public ING OpDlTmms3 = new ING();
    @Getter @Setter
    public ING OpDlTmms4 = new ING();
    @Getter @Setter
    public ING OpDlTmms5 = new ING();
    @Getter @Setter
    public ING OpDlTmms6 = new ING();

    @Setter
    private boolean APV = false;
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

        if (APV) {
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
                    setTimeDelayToZero();
                }
            }
        }

        if (anyOpnGeneralValue) {
            Pos.getCtlVal().setValue(false);
            if (!APVoperated && APV) {
                counterAPV++;
            }
        } else {
            Pos.getCtlVal().setValue(true);
        }
    }

    private void setTimeDelayToZero() {
        OpDlTmms1.setSetVal(0);
        OpDlTmms2.setSetVal(0);
        OpDlTmms3.setSetVal(0);
        OpDlTmms4.setSetVal(0);
        OpDlTmms5.setSetVal(0);
        OpDlTmms6.setSetVal(0);
    }
}
