package protection.model.logicalnodes.measurements;

import lombok.Getter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;
import utils.filters.Filter;
import utils.filters.FourierFiler;

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
    public final WYE Z = new WYE();
    private final CMV Iab = new CMV();
    private final CMV Ibc = new CMV();
    private final CMV Iac = new CMV();
    private final CMV Uab = new CMV();
    private final CMV Ubc = new CMV();
    private final CMV Uac = new CMV();
    @Getter
    private final CMV Zab = new CMV();
    @Getter
    private final CMV Zbc = new CMV();
    @Getter
    private final CMV Zac = new CMV();
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

        countPowers();

        countResistance();
    }

    private void countPowers() {
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

    private void countResistance() {
        Uab.getCVal().toVector(
                (PhV.getPhsA().getCVal().getMag().getF().getValue() - PhV.getPhsB().getCVal().getMag().getF().getValue()),
                (PhV.getPhsA().getCVal().getAng().getF().getValue() - PhV.getPhsB().getCVal().getAng().getF().getValue()));
        Ubc.getCVal().toVector(
                (PhV.getPhsB().getCVal().getMag().getF().getValue() - PhV.getPhsC().getCVal().getMag().getF().getValue()),
                (PhV.getPhsB().getCVal().getAng().getF().getValue() - PhV.getPhsC().getCVal().getAng().getF().getValue()));
        Uac.getCVal().toVector(
                (PhV.getPhsC().getCVal().getMag().getF().getValue() - PhV.getPhsA().getCVal().getMag().getF().getValue()),
                (PhV.getPhsC().getCVal().getAng().getF().getValue() - PhV.getPhsA().getCVal().getAng().getF().getValue()));

        Iab.getCVal().toVector(
                (A.getPhsA().getCVal().getMag().getF().getValue() - A.getPhsB().getCVal().getMag().getF().getValue()),
                (A.getPhsA().getCVal().getAng().getF().getValue() - A.getPhsB().getCVal().getAng().getF().getValue()));
        Ibc.getCVal().toVector(
                (A.getPhsB().getCVal().getMag().getF().getValue() - A.getPhsC().getCVal().getMag().getF().getValue()),
                (A.getPhsB().getCVal().getAng().getF().getValue() - A.getPhsC().getCVal().getAng().getF().getValue()));
        Iac.getCVal().toVector(
                (A.getPhsC().getCVal().getMag().getF().getValue() - A.getPhsA().getCVal().getMag().getF().getValue()),
                (A.getPhsC().getCVal().getAng().getF().getValue() - A.getPhsA().getCVal().getAng().getF().getValue()));

//        System.out.println("Iab = " + Iab.getCVal().getMag().getF().getValue());
//        System.out.println("Uab = " + Uab.getCVal().getMag().getF().getValue());

        Zab.getCVal().setResistance(Iab, Uab);
        Zbc.getCVal().setResistance(Ibc, Ubc);
        Zac.getCVal().setResistance(Iac, Uac);

        Z.setPhsA(Zab);
        Z.setPhsB(Zbc);
        Z.setPhsC(Zbc);
//        System.out.println("Zab = " + Z.getPhsA().getCVal().getMag().getF().getValue());
//        System.out.println("Ugl = " + Z.getPhsA().getCVal().getAng().getF().getValue());
//        System.out.println("Rab = " + Z.getPhsA().getCVal().getX().getF().getValue());
//        System.out.println("Xab = " + Z.getPhsA().getCVal().getY().getF().getValue());
//        System.out.println("");
    }
}
