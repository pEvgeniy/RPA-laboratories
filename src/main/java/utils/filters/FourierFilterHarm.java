package utils.filters;

import protection.model.dataobjects.measurements.CMV;
import protection.model.dataobjects.measurements.MV;

public class FourierFilterHarm extends FilterHarm {
    public int bSize = 20;
    private final double[] bufferX = new double[9999];
    private final double[] bufferY = new double[9999];
    private int bCount = 0;
    private double x = 0d;
    private double y = 0d;
    private final double k =(double) 2/bSize;
    private double Fz;

    public FourierFilterHarm(int harm) {
        this.Fz = 50 * harm;
    }

    @Override
    public void process(MV instMag, CMV result) {
        double value = instMag.getInstMag().getF().getValue();

        x += k * value * Math.sin((2 * Math.PI * Fz) * (0.02/bSize) * bCount) - bufferX[bCount];
        y += k * value * Math.cos((2 * Math.PI * Fz) * (0.02/bSize) * bCount) - bufferY[bCount];

        bufferX[bCount] = (k * value * Math.sin((2 * Math.PI * Fz) * (0.02/bSize) * bCount));
        bufferY[bCount] = (k * value * Math.cos((2 * Math.PI * Fz) * (0.02/bSize) * bCount));

        bCount++;
        if(bCount >= bSize) {
            bCount = 0;
        }

        result.getCVal().toVector(x, y);
    }
}
