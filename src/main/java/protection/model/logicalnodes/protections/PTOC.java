package protection.model.logicalnodes.protections;

import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.protection.Direction;
import protection.model.dataobjects.protection.ENG;
import protection.model.dataobjects.settings.ASG;
import protection.model.dataobjects.settings.ING;
import protection.model.logicalnodes.common.LN;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class PTOC extends LN {

    /*
     * Входы
     */
    public WYE A = new WYE();

    /*
     * Уставки
     */
    public ASG StrVal = new ASG();
    public ING OpDlTmms = new ING();
    public ENG DirMod = new ENG();
    public ACD Dir = new ACD();

    /*
     * Выходы
     */
    public final ACD Str = new ACD();
    public final ACT Op = new ACT();

    /*
     * Переменные
     */
    private double CntA = 0;
    private double CntB = 0;
    private double CntC = 0;

    @Override
    public void process() {
        boolean phsA = A.getPhsA().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();
        boolean phsB = A.getPhsB().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();
        boolean phsC = A.getPhsC().getCVal().getMag().getF().getValue() > StrVal.getSetMag().getF().getValue();

        Str.getPhsA().setValue(phsA);
        Str.getPhsB().setValue(phsB);
        Str.getPhsC().setValue(phsC);

        if (phsA) CntA += 1;
        else CntA = 0;

        if (phsB) CntB += 1;
        else CntB = 0;

        if (phsC) CntC += 1;
        else CntC = 0;

        if (OpDlTmms.getSetVal() == null) {
            OpDlTmms.setSetVal(0);
        }

        if (DirMod.getStVal().getValue() == Direction.FORWARD) {
            if (Dir.getDirPhsA().getValue() == Direction.BACKWARD) {
                CntA = 0;
            }
            if (Dir.getDirPhsB().getValue() == Direction.BACKWARD) {
                CntB = 0;
            }
            if (Dir.getDirPhsC().getValue() == Direction.BACKWARD) {
                CntC = 0;
            }
        }

        Op.getGeneral().setValue(CntA > OpDlTmms.getSetVal()
                || CntB > OpDlTmms.getSetVal()
                || CntC > OpDlTmms.getSetVal()
        );

        Op.getPhsA().setValue(CntA > OpDlTmms.getSetVal());
        Op.getPhsB().setValue(CntB > OpDlTmms.getSetVal());
        Op.getPhsC().setValue(CntC > OpDlTmms.getSetVal());
    }
}
