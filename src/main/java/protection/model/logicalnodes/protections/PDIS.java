package protection.model.logicalnodes.protections;

import lombok.Setter;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.settings.ASG;
import protection.model.dataobjects.settings.ING;
import protection.model.logicalnodes.common.LN;

import java.util.HashMap;
import java.util.Map;

public class PDIS extends LN {
    /*
     * Входы
     */
    public WYE Z = new WYE();

    /*
     * Уставки
     */
    public ING OpDlTmms = new ING();
    /**
     * точка 1 - левый верхний угол характеристики
     * точка 2 - правый верхний угол характеристики
     * точка 3 - левый нижний угол характеристики
     * точка 4 - правый нижний угол характеристики
     */
    @Setter
    private Map<Integer, double[]> point = new HashMap<>();
    @Setter
    private boolean OpSwing;

    /*
     * Выходы
     */
    public final ACD Str = new ACD();
    public final ACT Op = new ACT();

    private double CntA = 0;
    private double CntB = 0;
    private double CntC = 0;
    @Override
    public void process() {
        boolean phsAB = isInCharacteristic(
                Z.getPhsA().getCVal().getX().getF().getValue(),
                Z.getPhsA().getCVal().getY().getF().getValue()
        );
        boolean phsBC = isInCharacteristic(
                Z.getPhsB().getCVal().getX().getF().getValue(),
                Z.getPhsB().getCVal().getY().getF().getValue()
        );
        boolean phsCA = isInCharacteristic(
                Z.getPhsC().getCVal().getX().getF().getValue(),
                Z.getPhsC().getCVal().getY().getF().getValue()
        );

        Str.getPhsA().setValue(phsAB);
        Str.getPhsB().setValue(phsBC);
        Str.getPhsC().setValue(phsCA);
        Str.getGeneral().setValue(phsAB || phsBC || phsCA);
//        System.out.println("phsAB = " + phsAB);
//        System.out.println("phsBC = " + phsBC);
//        System.out.println("phsCA = " + phsCA);

        if (phsAB) CntA += 1;
        else CntA = 0;

        if (phsBC) CntB += 1;
        else CntB = 0;

        if (phsCA) CntC += 1;
        else CntC = 0;

        if (OpDlTmms.getSetVal() == null) {
            OpDlTmms.setSetVal(0);
        }

        if (OpSwing) {
            CntA = 0;
            CntB = 0;
            CntC = 0;
            System.out.println("Качания");
            return;
        }

        Op.getGeneral().setValue(CntA > OpDlTmms.getSetVal()
                || CntB > OpDlTmms.getSetVal()
                || CntC > OpDlTmms.getSetVal()
        );

        Op.getPhsA().setValue(CntA > OpDlTmms.getSetVal());
        Op.getPhsB().setValue(CntB > OpDlTmms.getSetVal());
        Op.getPhsC().setValue(CntC > OpDlTmms.getSetVal());
    }

    private boolean isInCharacteristic(double x, double y) {
        return x > point.get(3)[0] && x < point.get(2)[0]
                && y > point.get(3)[1] && y < point.get(2)[1]
                && y > getOYRight(x) && y < getOYLeft(x);
    }

    private double getOYLeft(double x) {
        double x1 = point.get(1)[0];
        double y1 = point.get(1)[1];
        double x2 = point.get(3)[0];
        double y2 = point.get(3)[1];
        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;
        return m * x + b;
    }

    private double getOYRight(double x) {
        double x1 = point.get(2)[0];
        double y1 = point.get(2)[1];
        double x2 = point.get(4)[0];
        double y2 = point.get(4)[1];
        double m = (y2 - y1) / (x2 - x1);
        double b = y1 - m * x1;
        return m * x + b;
    }
}
