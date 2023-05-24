package frequency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.CustomLCOM;
import protection.model.logicalnodes.measurements.MMXU;

import java.util.ArrayList;
import java.util.List;

public class FrequencyTest {

    private MMXU mmxu;
    private CustomLCOM customLCOM;
    private List<LN> lnList;

    @BeforeEach
    public void beforeEach() {
        customLCOM = new CustomLCOM();
        mmxu = new MMXU();
        mmxu.setFreqMeter80(true);
        NHMI nhmi = new NHMI();
        nhmi.addSignals(new NHMISignal("signal", customLCOM.OUT.get(0).getInstMag().getF()));
        nhmi.addSignals(new NHMISignal("freq", mmxu.Frequency.getInstMag().getF()));
        lnList = new ArrayList<>();
        lnList.add(customLCOM);
        lnList.add(mmxu);
        lnList.add(nhmi);
    }

    @Test
    public void Frequency45() throws InterruptedException {
        customLCOM.setMag(1000);
        customLCOM.setFreq(45);

        mmxu.phsAInstI = customLCOM.OUT.get(0);

        while (customLCOM.hasNextData()) {
            lnList.forEach(LN::process);
        }
        Thread.sleep(3000);
    }

    @Test
    public void Frequency47() throws InterruptedException {
        customLCOM.setMag(1000);
        customLCOM.setFreq(47);

        mmxu.phsAInstI = customLCOM.OUT.get(0);

        while (customLCOM.hasNextData()) {
            lnList.forEach(LN::process);
        }
        Thread.sleep(3000);
    }

    @Test
    public void Frequency50() throws InterruptedException {
        customLCOM.setMag(1000);
        customLCOM.setFreq(50);

        mmxu.phsAInstI = customLCOM.OUT.get(0);

        while (customLCOM.hasNextData()) {
            lnList.forEach(LN::process);
        }
        Thread.sleep(3000);
    }

    @Test
    public void Frequency53() throws InterruptedException {
        customLCOM.setMag(1000);
        customLCOM.setFreq(52);

        mmxu.phsAInstI = customLCOM.OUT.get(0);

        while (customLCOM.hasNextData()) {
            lnList.forEach(LN::process);
        }
        Thread.sleep(3000);
    }

    @Test
    public void Frequency55() throws InterruptedException {
        customLCOM.setMag(1000);
        customLCOM.setFreq(54);

        mmxu.phsAInstI = customLCOM.OUT.get(0);

        while (customLCOM.hasNextData()) {
            lnList.forEach(LN::process);
        }
        Thread.sleep(3000);
    }
}
