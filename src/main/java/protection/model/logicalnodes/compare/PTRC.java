package protection.model.logicalnodes.compare;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.protection.ACT;
import protection.model.logicalnodes.common.LN;

import java.util.ArrayList;

@Getter
@Setter
public class PTRC extends LN {

    private ArrayList<ACT> Op = new ArrayList<>();

    private ACT Tr = new ACT();

    @Override
    public void process() {
        for (ACT op : Op) {
            if (op.getGeneral().getValue()) {
                Tr.getGeneral().setValue(true);
                break;
            } else {
                Tr.getGeneral().setValue(false);
            }
        }
    }

    public void addOp(ACT op) {
        Op.add(op);
    }
}
