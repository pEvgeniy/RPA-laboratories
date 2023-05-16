package utils.filters;

import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;

public class FourierFiler extends Filter {

    public static int bSize = 40;
    private final double[] bufferX = new double[bSize];
    private final double[] bufferY = new double[bSize];
    private int bCount = 0;
    private double x = 0d;
    private double y = 0d;
    private final double k =(double) 2/bSize;

    @Override
    public void process(MV instMag, CMV result) {
        double value = instMag.getInstMag().getF().getValue();

        x += k * value * Math.sin((2 * Math.PI * 50) * (0.02/bSize) * bCount) - bufferX[bCount];
        y += k * value * Math.cos((2 * Math.PI * 50) * (0.02/bSize) * bCount) - bufferY[bCount];

        bufferX[bCount] = (k * value * Math.sin((2 * Math.PI * 50) * (0.02/bSize) * bCount));
        bufferY[bCount] = (k * value * Math.cos((2 * Math.PI * 50) * (0.02/bSize) * bCount));

        bCount++;
        if(bCount >= bSize) {
            bCount = 0;
        }

        result.getCVal().toVector(x, y);
    }
}
