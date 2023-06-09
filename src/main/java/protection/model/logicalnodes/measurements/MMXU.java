package protection.model.logicalnodes.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;
import utils.filters.Filter;
import utils.filters.FourierFilter20;
import utils.filters.FourierFilter80;
import utils.frequency.FrequencyMeter20;
import utils.frequency.FrequencyMeter80;

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
    public MV Frequency = new MV();


    /*
     * Переменные
     */
    private Filter phsACurrentF;
    private Filter phsBCurrentF;
    private Filter phsCCurrentF;
    private Filter phsAVoltageF;
    private Filter phsBVoltageF;
    private Filter phsCVoltageF;
    private final FrequencyMeter80 frequencyMeter80 = new FrequencyMeter80();
    private final FrequencyMeter20 frequencyMeter20 = new FrequencyMeter20();

    @Setter
    private boolean freqMeter80 = false;
    @Setter
    private boolean freqMeter20 = false;

    public MMXU() {
        this.phsACurrentF = new FourierFilter80();
        this.phsBCurrentF = new FourierFilter80();
        this.phsCCurrentF = new FourierFilter80();
        this.phsAVoltageF = new FourierFilter80();
        this.phsBVoltageF = new FourierFilter80();
        this.phsCVoltageF = new FourierFilter80();
    }

    public MMXU(boolean setFilterFor20) {
        if (setFilterFor20) {
            this.phsACurrentF = new FourierFilter20();
            this.phsBCurrentF = new FourierFilter20();
            this.phsCCurrentF = new FourierFilter20();
            this.phsAVoltageF = new FourierFilter20();
            this.phsBVoltageF = new FourierFilter20();
            this.phsCVoltageF = new FourierFilter20();
        }
    }

    @Override
    public void process() {
        if (freqMeter80) {
            frequencyMeter80.process(phsAInstI, Frequency);
        } else if (freqMeter20) {
            frequencyMeter20.process(phsAInstI, Frequency);
        }

        phsACurrentF.process(phsAInstI, A.getPhsA(), Frequency);
        phsBCurrentF.process(phsBInstI, A.getPhsB(), Frequency);
        phsCCurrentF.process(phsCInstI, A.getPhsC(), Frequency);

        phsAVoltageF.process(phsAInstV, PhV.getPhsA(), Frequency);
        phsBVoltageF.process(phsBInstV, PhV.getPhsB(), Frequency);
        phsCVoltageF.process(phsCInstV, PhV.getPhsC(), Frequency);

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
//        System.out.println("PhVA = " + PhV.getPhsA().getCVal().getMag().getF().getValue());
//        System.out.println("PhVB = " + PhV.getPhsB().getCVal().getMag().getF().getValue());

        Uab.getCVal().toVector(
                (PhV.getPhsA().getCVal().getMag().getF().getValue() - PhV.getPhsB().getCVal().getMag().getF().getValue()),
                (PhV.getPhsA().getCVal().getAng().getF().getValue() - PhV.getPhsB().getCVal().getAng().getF().getValue()));

//        System.out.println("Uab mag = " + Uab.getCVal().getMag().getF().getValue());
//        System.out.println("Uab ang = " + Uab.getCVal().getAng().getF().getValue());

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
