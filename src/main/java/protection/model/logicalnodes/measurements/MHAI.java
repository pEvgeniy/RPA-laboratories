package protection.model.logicalnodes.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.HWYE;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.common.LN;
import utils.filters.FilterHarm;
import utils.filters.FourierFilterHarm;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MHAI extends LN {

    public MV phsAInstI = new MV();
    public MV phsBInstI = new MV();
    public MV phsCInstI = new MV();

    private HWYE HA = new HWYE();

    private List<FilterHarm> phsACurF = new ArrayList<>();
    private List<FilterHarm> phsBCurF = new ArrayList<>();
    private List<FilterHarm> phsCCurF = new ArrayList<>();

    public MHAI() {
        setFilters();
    }

    @Override
    public void process() {
        for (int harm = 1; harm <= HA.getHarmNumber().getValue(); harm++){
            phsACurF.get(harm - 1).process(phsAInstI, HA.getPhsAH().get(harm - 1));
            phsBCurF.get(harm - 1).process(phsBInstI, HA.getPhsBH().get(harm - 1));
            phsCCurF.get(harm - 1).process(phsCInstI, HA.getPhsCH().get(harm - 1));
        }
    }

    private void setFilters() {
        for (int harm = 1; harm <= HA.getHarmNumber().getValue(); harm++) {
            phsACurF.add(new FourierFilterHarm(harm));
            phsBCurF.add(new FourierFilterHarm(harm));
            phsCCurF.add(new FourierFilterHarm(harm));
        }
    }
}
