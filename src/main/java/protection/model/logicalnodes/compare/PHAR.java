package protection.model.logicalnodes.compare;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.HWYE;
import protection.model.dataobjects.protection.ACT;
import protection.model.logicalnodes.common.LN;

public class PHAR extends LN {
    @Setter
    private HWYE HA = new HWYE();

    @Getter
    private final ACT Str = new ACT();

    @Getter
    private CMV raznA = new CMV();
    @Getter
    private CMV raznB = new CMV();
    @Getter
    private CMV raznC = new CMV();

    @Override
    public void process() {
        for (int i = 0; i < HA.getHarmNumber().getValue(); i++) {
            double phsAHar1 = HA.getPhsAH().get(0).getCVal().getMag().getF().getValue();
            double phsBHar1 = HA.getPhsBH().get(0).getCVal().getMag().getF().getValue();
            double phsCHar1 = HA.getPhsCH().get(0).getCVal().getMag().getF().getValue();

            double phsAHar5 = HA.getPhsAH().get(4).getCVal().getMag().getF().getValue();
            double phsBHar5 = HA.getPhsBH().get(4).getCVal().getMag().getF().getValue();
            double phsCHar5 = HA.getPhsCH().get(4).getCVal().getMag().getF().getValue();

            if ((phsAHar5 / phsAHar1) > 0.2) {
                Str.getPhsA().setValue(true);
            } else {
                Str.getPhsA().setValue(false);
            }
            if ((phsBHar5 / phsBHar1) > 0.2) {
                Str.getPhsB().setValue(true);
            } else {
                Str.getPhsB().setValue(false);
            }
            if ((phsCHar5 / phsCHar1) > 0.2) {
                Str.getPhsC().setValue(true);
            } else {
                Str.getPhsC().setValue(false);
            }
            raznA.getCVal().getMag().getF().setValue(phsAHar5/phsAHar1);
            raznB.getCVal().getMag().getF().setValue(phsBHar5/phsBHar1);
            raznC.getCVal().getMag().getF().setValue(phsCHar5/phsCHar1);

            if (Str.getPhsA().getValue() || Str.getPhsB().getValue() || Str.getPhsC().getValue()) {
                Str.getGeneral().setValue(true);
            } else {
                Str.getGeneral().setValue(false);
            }
        }
    }
}
