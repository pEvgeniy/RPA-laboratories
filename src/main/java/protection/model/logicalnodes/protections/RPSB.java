package protection.model.logicalnodes.protections;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.ACT;
import protection.model.dataobjects.settings.ASG;
import protection.model.logicalnodes.common.LN;

import java.util.LinkedList;
import java.util.List;

public class RPSB extends LN {
    @Getter @Setter
    private WYE A = new WYE();

    @Getter @Setter
    public ASG SwgVal = new ASG();

    @Getter
    private final ACD Str = new ACD();
    @Getter
    private final ACT Op = new ACT();

    private final List<Double> bufferA = new LinkedList<>();
    private final List<Double> bufferB = new LinkedList<>();
    private final List<Double> bufferC = new LinkedList<>();

    @Override
    public void process() {
        double phsA = A.getPhsA().getCVal().getMag().getF().getValue();
        double phsB = A.getPhsB().getCVal().getMag().getF().getValue();
        double phsC = A.getPhsC().getCVal().getMag().getF().getValue();

        if (bufferA.size() == 10) {
            bufferA.remove(9);
            bufferB.remove(9);
            bufferC.remove(9);
            bufferA.add(0, phsA);
            bufferB.add(0, phsB);
            bufferC.add(0, phsC);
        } else {
            bufferA.add(phsA);
            bufferB.add(phsB);
            bufferC.add(phsC);
        }

        if (bufferA.size() == 10) {
            if (Math.abs(bufferA.get(9) - bufferA.get(0)) > SwgVal.getSetMag().getF().getValue()
                    || Math.abs(bufferB.get(9) - bufferB.get(0)) > SwgVal.getSetMag().getF().getValue()
                    || Math.abs(bufferC.get(9) - bufferC.get(0)) > SwgVal.getSetMag().getF().getValue()) {
                Op.getGeneral().setValue(true);
            } else {
                Op.getGeneral().setValue(false);
            }
        }
    }
}
