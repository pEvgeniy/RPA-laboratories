package utils.frequency;

import protection.model.dataobjects.measurements.MV;

import java.util.ArrayList;
import java.util.List;

public class FrequencyMeter80 {

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
                double curFrequency = k1 * 1 / (count * 0.00025) + (1 - k1) * (1 / (buffer * 0.00025));

                if (Math.abs(curFrequency - buffer) > buffer * 2) {
                    curFrequency = 1 / (measurements.size() * 0.00025);
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

    private void badRealisation(MV inst, MV frequency) {
        double value = inst.getInstMag().getF().getValue();
        count++;
        if (crossesZero(value)) {
            crossings++;
            if (crossings == 3) {
                count = counterDelta(count);
                System.out.println(1/(count * 0.00025));
                frequency.getInstMag().getF().setValue(k1 * 1 / (count * 0.00025) + (1 - k1) * (1 / (buffer * 0.00025)));
                count = 0;
                crossings = 1;
            }
        }
    }

    private boolean crossesZero(double point) {
        double epsilon = 20.5;
        return (point >= 0 && Math.abs(point) < epsilon) || (point <= 0 && Math.abs(point) < epsilon);
    }

    private int counterDelta(int count) {
        if (buffer == 0d) {
            buffer = count;
        }
        if (Math.abs(buffer - count) > buffer * 1.6) {
            return (int) buffer;
        }
        buffer = count;
        return count;
    }
}
