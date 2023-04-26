package protection.model.logicalnodes.protections;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;
import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.WYE;
import protection.model.dataobjects.protection.ACD;
import protection.model.dataobjects.protection.Direction;
import protection.model.logicalnodes.common.LN;

import java.util.Map;

@Getter @Setter
public class RDIR extends LN {
    private ACD Dir = new ACD();
    private WYE W = new WYE();
    private Map<CMV, DataAttribute<Direction>> wToDir;

    @Override
    public void process() {
        wToDir = Map.of(
                W.getPhsA(), Dir.getDirPhsA(),
                W.getPhsB(), Dir.getDirPhsB(),
                W.getPhsC(), Dir.getDirPhsC()
        );
        for (Map.Entry<CMV, DataAttribute<Direction>> phase : wToDir.entrySet()) {
            if (phase.getKey().getCVal().getMag().getF().getValue() < 0) {
                phase.getValue().setValue(Direction.BACKWARD);
            } else {
                phase.getValue().setValue(Direction.FORWARD);
            }
        }
    }
}
