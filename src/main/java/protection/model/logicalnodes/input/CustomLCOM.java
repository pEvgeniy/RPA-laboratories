package protection.model.logicalnodes.input;

import lombok.Setter;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.common.LN;

import java.util.ArrayList;
import java.util.List;

public class CustomLCOM extends LN {

    public final List<MV> OUT = new ArrayList<>();

    @Setter
    private double mag;
    @Setter
    private double freq;
    private double time = 0;

    public CustomLCOM() {
        OUT.add(new MV());
    }

    @Override
    public void process() {
        OUT.get(0).getInstMag().getF().setValue(
                mag * Math.sin(2 * Math.PI * freq * time)
        );
        time += 0.00025;
    }

    public boolean hasNextData() {
        return time < 1;
    }
}
