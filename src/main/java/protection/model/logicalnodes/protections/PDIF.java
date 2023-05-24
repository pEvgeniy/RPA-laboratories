package protection.model.logicalnodes.protections;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.protection.Direction;
import protection.model.dataobjects.settings.CSD;
import protection.model.dataobjects.settings.ING;
import protection.model.logicalnodes.common.LN;

@Getter
@Setter
public class PDIF extends LN {

    private WYE DifAClc = new WYE();
    private WYE RstA = new WYE();

    private CSD TmASt = new CSD();

    private ACT Blc = new ACT();

    public ING OpDlTmms = new ING();

    public final ACT Str = new ACT();
    public final ACT Op = new ACT();

    private double CntA = 0;
    private double CntB = 0;
    private double CntC = 0;

    @Override
    public void process() {
        if (Blc.getGeneral().getValue()) {
//            System.out.println("Блокировка по 5-ой гармонике");
            CntA = 0;
            CntB = 0;
            CntC = 0;
            Op.getGeneral().setValue(false);
            return;
        }

        boolean phsA = isInCharacteristic(
                RstA.getPhsA().getCVal().getMag().getF().getValue(),
                DifAClc.getPhsA().getCVal().getMag().getF().getValue()
        );
        boolean phsB = isInCharacteristic(
                RstA.getPhsB().getCVal().getMag().getF().getValue(),
                DifAClc.getPhsB().getCVal().getMag().getF().getValue()
        );
        boolean phsC = isInCharacteristic(
                RstA.getPhsC().getCVal().getMag().getF().getValue(),
                DifAClc.getPhsC().getCVal().getMag().getF().getValue()
        );

        Str.getPhsA().setValue(phsA);
        Str.getPhsB().setValue(phsB);
        Str.getPhsC().setValue(phsC);
        Str.getGeneral().setValue(phsA || phsB || phsC);

        if (phsA) CntA += 1;
        else CntA = 0;

        if (phsB) CntB += 1;
        else CntB = 0;

        if (phsC) CntC += 1;
        else CntC = 0;

        if (OpDlTmms.getSetVal() == null) {
            OpDlTmms.setSetVal(0);
        }

        Op.getGeneral().setValue(CntA > OpDlTmms.getSetVal()
                || CntB > OpDlTmms.getSetVal()
                || CntC > OpDlTmms.getSetVal()
        );

        Op.getPhsA().setValue(CntA > OpDlTmms.getSetVal());
        Op.getPhsB().setValue(CntB > OpDlTmms.getSetVal());
        Op.getPhsC().setValue(CntC > OpDlTmms.getSetVal());
    }

    private boolean isInCharacteristic(double RstA, double ALoc) {
        if (RstA < TmASt.getCrvPts().get(1).getXVal().getValue() && RstA >= 0) {
            return ALoc > TmASt.getCrvPts().get(1).getYVal().getValue();
        } else if (RstA >= TmASt.getCrvPts().get(1).getXVal().getValue()
                && RstA <= TmASt.getCrvPts().get(2).getXVal().getValue()) {
            return isUpperFirstLine(RstA, ALoc);
        } else if (RstA >= TmASt.getCrvPts().get(2).getXVal().getValue()
                && RstA <= TmASt.getCrvPts().get(3).getXVal().getValue()) {
            return isUpperSecondLine(
                    TmASt.getCrvPts().get(2).getXVal().getValue(), TmASt.getCrvPts().get(2).getYVal().getValue(),
                    TmASt.getCrvPts().get(3).getXVal().getValue(), TmASt.getCrvPts().get(3).getYVal().getValue(),
                    RstA, ALoc);
        } else if (RstA >= TmASt.getCrvPts().get(3).getXVal().getValue()) {
            return isUpperThirdLine(RstA, ALoc);
        }
        return false;
    }

    private boolean isUpperFirstLine(double x, double y0) {
        return y0 >= Math.tan(Math.toRadians(30)) * x;
    }

    private boolean isUpperSecondLine(double x1, double y1, double x2, double y2, double x0, double y0) {
        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;
        return y0 >= (m * x0 + b);

    }

    private boolean isUpperThirdLine(double x0, double y0) {
        return y0 >= Math.tan(Math.toRadians(35)) * x0;
    }
}
