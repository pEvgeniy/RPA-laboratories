package utils.frequency;

import protection.model.dataobjects.measurements.MV;

import java.util.ArrayList;
import java.util.List;

public class FrequencyMeter20 {

    private double buffer = 0d;
    private int count = 0;
    private int crossings = 1;
    private final double k1 = Math.exp(-2);
    private final List<Double> measurements = new ArrayList<>();
    private final List<Double> freqList = new ArrayList<>();
    private double bufferValue;


    public void process(MV inst, MV frequency) {
        double value = inst.getInstMag().getF().getValue();
        measurements.add(value);

        if (bufferValue == 0) {
            bufferValue = value;
        }

        if ((value > 0 && bufferValue < 0) || (value < 0 && bufferValue > 0)) {
            freqList.add(value);

            if (freqList.size() == 2) {
                double curFrequency = k1 * 1 / (count * 0.001) + (1 - k1) * (1 / (buffer * 0.001));

                if (Math.abs(curFrequency - buffer) > buffer * 2) {
                    curFrequency = 1 / (measurements.size() * 0.001);
                }
//                System.out.println(curFrequency);
                frequency.getInstMag().getF().setValue(curFrequency);
                buffer = curFrequency;
                measurements.clear();
                freqList.clear();
            }
            bufferValue = value;
        }
    }
}
