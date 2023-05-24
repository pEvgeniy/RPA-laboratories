package protection.model.logicalnodes.dif;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;

@Getter
@Setter
public class RMXU extends LN {

    private WYE wye1 = new WYE();
    private WYE wye2 = new WYE();
    private WYE wye3 = new WYE();
    private WYE wye4 = new WYE();
    private WYE wye5 = new WYE();

    private WYE ALoc = new WYE();
    private WYE RstA = new WYE();

    @Override
    public void process() {
        CMV phsA1 = wye1.getPhsA();
        CMV phsA2 = wye2.getPhsA();
        CMV phsA3 = wye3.getPhsA();
        CMV phsA4 = wye4.getPhsA();
        CMV phsA5 = wye5.getPhsA();

        CMV phsB1 = wye1.getPhsB();
        CMV phsB2 = wye2.getPhsB();
        CMV phsB3 = wye3.getPhsB();
        CMV phsB4 = wye4.getPhsB();
        CMV phsB5 = wye5.getPhsB();

        CMV phsC1 = wye1.getPhsC();
        CMV phsC2 = wye2.getPhsC();
        CMV phsC3 = wye3.getPhsC();
        CMV phsC4 = wye4.getPhsC();
        CMV phsC5 = wye5.getPhsC();

        ALoc.getPhsA().getCVal().getMag().getF().setValue(
                getSum(phsA1, phsA2, phsA3, phsA4, phsA5)
        );

        ALoc.getPhsB().getCVal().getMag().getF().setValue(
                getSum(phsB1, phsB2, phsB3, phsB4, phsB5)
        );

        ALoc.getPhsC().getCVal().getMag().getF().setValue(
                getSum(phsC1, phsC2, phsC3, phsC4, phsC5)
        );

        RstA.getPhsA().getCVal().getMag().getF().setValue(
                getMaxValue(phsA1, phsA2, phsA3, phsA4, phsA5)
        );

        RstA.getPhsB().getCVal().getMag().getF().setValue(
                getMaxValue(phsB1, phsB2, phsB3, phsB4, phsB5)
        );

        RstA.getPhsC().getCVal().getMag().getF().setValue(
                getMaxValue(phsC1, phsC2, phsC3, phsC4, phsC5)
        );
    }

    private double getSum(CMV phs1, CMV phs2, CMV phs3, CMV phs4, CMV phs5) {
        return phs1.getCVal().getMag().getF().getValue() +
                phs2.getCVal().getMag().getF().getValue() +
                phs3.getCVal().getMag().getF().getValue() +
                phs4.getCVal().getMag().getF().getValue() +
                phs5.getCVal().getMag().getF().getValue();
    }

    private double getOrtXSum(CMV phs1, CMV phs2, CMV phs3, CMV phs4, CMV phs5) {
        return phs1.getCVal().getOrtX(
                        phs1.getCVal().getMag().getF().getValue(),
                        phs1.getCVal().getAng().getF().getValue()) +
                phs2.getCVal().getOrtX(
                        phs2.getCVal().getMag().getF().getValue(),
                        phs2.getCVal().getAng().getF().getValue()) +
                phs3.getCVal().getOrtX(
                        phs3.getCVal().getMag().getF().getValue(),
                        phs3.getCVal().getAng().getF().getValue()) +
                phs4.getCVal().getOrtX(
                        phs4.getCVal().getMag().getF().getValue(),
                        phs4.getCVal().getAng().getF().getValue()) +
                phs5.getCVal().getOrtX(
                        phs5.getCVal().getMag().getF().getValue(),
                        phs5.getCVal().getAng().getF().getValue());
    }

    private double getOrtYSum(CMV phs1, CMV phs2, CMV phs3, CMV phs4, CMV phs5) {
        return phs1.getCVal().getOrtY(
                        phs1.getCVal().getMag().getF().getValue(),
                        phs1.getCVal().getAng().getF().getValue()) +
                phs2.getCVal().getOrtY(
                        phs2.getCVal().getMag().getF().getValue(),
                        phs2.getCVal().getAng().getF().getValue()) +
                phs3.getCVal().getOrtY(
                        phs3.getCVal().getMag().getF().getValue(),
                        phs3.getCVal().getAng().getF().getValue()) +
                phs4.getCVal().getOrtY(
                        phs4.getCVal().getMag().getF().getValue(),
                        phs4.getCVal().getAng().getF().getValue()) +
                phs5.getCVal().getOrtY(
                        phs5.getCVal().getMag().getF().getValue(),
                        phs5.getCVal().getAng().getF().getValue());
    }

    private double getMaxValue(CMV phs1, CMV phs2, CMV phs3, CMV phs4, CMV phs5) {
        double value1 = phs1.getCVal().getMag().getF().getValue();
        double value2 = phs2.getCVal().getMag().getF().getValue();
        double value3 = phs3.getCVal().getMag().getF().getValue();
        double value4 = phs4.getCVal().getMag().getF().getValue();
        double value5 = phs5.getCVal().getMag().getF().getValue();
        double[] values = new double[] {value1, value2, value3, value4, value5};

        double max = value1;

        for (double value : values) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }
}
