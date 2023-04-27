package protection.model.logicalnodes.measurements;

import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;
import utils.filters.Filter;
import utils.filters.FourierFiler;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class MMXU extends LN {

    /*
     * Входы
     */
    public MV phsAInstI = new MV();
    public MV phsBInstI = new MV();
    public MV phsCInstI = new MV();
    public MV phsAInstV = new MV();
    public MV phsBInstV = new MV();
    public MV phsCInstV = new MV();

    /*
     * Выходы
     */
    public final WYE A = new WYE();
    public final WYE PhV = new WYE();
    public final WYE W = new WYE();
    public final WYE WAr = new WYE();
    public final WYE WA = new WYE();
    public final WYE PF = new WYE();
    public final WYE Wg = new WYE();


    /*
     * Переменные
     */
    private final Filter phsACurrentF = new FourierFiler();
    private final Filter phsBCurrentF = new FourierFiler();
    private final Filter phsCCurrentF = new FourierFiler();
    private final Filter phsAVoltageF = new FourierFiler();
    private final Filter phsBVoltageF = new FourierFiler();
    private final Filter phsCVoltageF = new FourierFiler();

    @Override
    public void process() {
        phsACurrentF.process(phsAInstI, A.getPhsA());
        phsBCurrentF.process(phsBInstI, A.getPhsB());
        phsCCurrentF.process(phsCInstI, A.getPhsC());

        phsAVoltageF.process(phsAInstV, PhV.getPhsA());
        phsBVoltageF.process(phsBInstV, PhV.getPhsB());
        phsCVoltageF.process(phsCInstV, PhV.getPhsC());


        PF.getPhsA().getCVal().getMag().getF().setValue(
                Math.cos(PhV.getPhsA().getCVal().getAng().getF().getValue() -
                        A.getPhsA().getCVal().getAng().getF().getValue())
        );
        PF.getPhsB().getCVal().getMag().getF().setValue(
                Math.cos(PhV.getPhsB().getCVal().getAng().getF().getValue() -
                        A.getPhsB().getCVal().getAng().getF().getValue())
        );
        PF.getPhsC().getCVal().getMag().getF().setValue(
                Math.cos(PhV.getPhsC().getCVal().getAng().getF().getValue() -
                        A.getPhsC().getCVal().getAng().getF().getValue())
        );

        WA.getPhsA().getCVal().getMag().getF().setValue(
                PhV.getPhsA().getCVal().getMag().getF().getValue() *
                        A.getPhsA().getCVal().getMag().getF().getValue()
        );
        WA.getPhsB().getCVal().getMag().getF().setValue(
                PhV.getPhsB().getCVal().getMag().getF().getValue() *
                        A.getPhsB().getCVal().getMag().getF().getValue()
        );
        WA.getPhsC().getCVal().getMag().getF().setValue(
                PhV.getPhsC().getCVal().getMag().getF().getValue() *
                        A.getPhsC().getCVal().getMag().getF().getValue()
        );

        W.getPhsA().getCVal().getMag().getF().setValue(
                WA.getPhsA().getCVal().getMag().getF().getValue() *
                        PF.getPhsA().getCVal().getMag().getF().getValue()
        );
        W.getPhsB().getCVal().getMag().getF().setValue(
                WA.getPhsB().getCVal().getMag().getF().getValue() *
                        PF.getPhsB().getCVal().getMag().getF().getValue()
        );
        W.getPhsC().getCVal().getMag().getF().setValue(
                WA.getPhsC().getCVal().getMag().getF().getValue() *
                        PF.getPhsC().getCVal().getMag().getF().getValue()
        );

        Wg.getPhsA().getCVal().getMag().getF().setValue(
                (W.getPhsA().getCVal().getMag().getF().getValue() +
                        W.getPhsB().getCVal().getMag().getF().getValue() +
                        W.getPhsC().getCVal().getMag().getF().getValue()) / 3
        );
    }
}
