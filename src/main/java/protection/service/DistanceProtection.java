package protection.service;

import protection.model.logicalnodes.breaker.CSWI;
import protection.model.logicalnodes.breaker.XCBR;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.NHMIP;
import protection.model.logicalnodes.gui.other.NHMIPoint;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.protections.PDIS;
import protection.model.logicalnodes.protections.RPSB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DistanceProtection {
    private final static List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {
        LCOM lcom = new LCOM();
        lnList.add(lcom);
        lcom.setFilePath(
                "src/main/resources/experiments/LR2/",
                "KZ1"
        );

        MMXU mmxu = new MMXU();
        lnList.add(mmxu);
        mmxu.phsAInstV = lcom.OUT.get(0);
        mmxu.phsBInstV = lcom.OUT.get(1);
        mmxu.phsCInstV = lcom.OUT.get(2);

        mmxu.phsAInstI = lcom.OUT.get(5);
        mmxu.phsBInstI = lcom.OUT.get(4);
        mmxu.phsCInstI = lcom.OUT.get(3);

        RPSB rpsb = new RPSB();
        lnList.add(rpsb);
        rpsb.setA(mmxu.A);
        rpsb.getSwgVal().getSetMag().getF().setValue(0.01d);

        PDIS pdis1 = new PDIS();
        lnList.add(pdis1);
        pdis1.Z = mmxu.Z;
        pdis1.setOpSwing(rpsb.getOp().getGeneral().getValue());
        pdis1.OpDlTmms.setSetVal(100);
        List<double[]> coordinatesPDIS1 = List.of(
                new double[] {-50, 300},
                new double[] {350, 300},
                new double[] {-70, -50},
                new double[] {330, -50}
        );
        pdis1.setPoint(
                Map.of(
                1, coordinatesPDIS1.get(0),
                2, coordinatesPDIS1.get(1),
                3, coordinatesPDIS1.get(2),
                4, coordinatesPDIS1.get(3)
                )
        );
        List<NHMIPoint<Double, Double>> nhmiPointsPDIS1 = new ArrayList<>();
        drawCharacteristic(nhmiPointsPDIS1, coordinatesPDIS1);

        PDIS pdis2 = new PDIS();
        lnList.add(pdis2);
        pdis2.Z = mmxu.Z;
        pdis2.setOpSwing(rpsb.getOp().getGeneral().getValue());
        pdis2.OpDlTmms.setSetVal(250);
        List<double[]> coordinatesPDIS2 = List.of(
                new double[] {-50, 600},
                new double[] {400, 600},
                new double[] {-70, -50},
                new double[] {330, -50}
        );
        pdis2.setPoint(
                Map.of(
                        1, coordinatesPDIS2.get(0),
                        2, coordinatesPDIS2.get(1),
                        3, coordinatesPDIS2.get(2),
                        4, coordinatesPDIS2.get(3)
                )
        );
        List<NHMIPoint<Double, Double>> nhmiPointsPDIS2 = new ArrayList<>();
        drawCharacteristic(nhmiPointsPDIS2, coordinatesPDIS2);


        CSWI cswi = new CSWI();
        lnList.add(cswi);
        cswi.setOpOpn1(pdis1.Op);
        cswi.getOpOpn1().getGeneral().setValue(false);
        cswi.setOpOpn2(pdis2.Op);
        cswi.getOpOpn2().getGeneral().setValue(false);

        XCBR xcbr = new XCBR();
        lnList.add(xcbr);
        xcbr.setPos(cswi.getPos());

        NHMI nhmi1 = new NHMI();
        lnList.add(nhmi1);
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsA", lcom.OUT.get(5).getInstMag().getF()));
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsB", lcom.OUT.get(4).getInstMag().getF()));
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsC", lcom.OUT.get(3).getInstMag().getF()));
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsA", lcom.OUT.get(0).getInstMag().getF()));
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsB", lcom.OUT.get(1).getInstMag().getF()));
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsC", lcom.OUT.get(2).getInstMag().getF()));
//        nhmi1.addSignals(
//                new NHMISignal("frequency", mmxu.Frequency.getInstMag().getF()));
//        nhmi1.addSignals(
//                new NHMISignal("FourierValuePhsA", mmxu.A.getPhsA().getCVal().getMag().getF())
//        );
//        nhmi1.addSignals(
//                new NHMISignal("FourierValuePhsB", mmxu.A.getPhsB().getCVal().getMag().getF())
//        );
//        nhmi1.addSignals(
//                new NHMISignal("FourierValuePhsC", mmxu.A.getPhsC().getCVal().getMag().getF())
//        );
//        nhmi1.addSignals(
//                new NHMISignal("FourierValuePhsA", mmxu.PhV.getPhsA().getCVal().getMag().getF())
//        );
//        nhmi1.addSignals(
//                new NHMISignal("FourierValuePhsB", mmxu.PhV.getPhsB().getCVal().getMag().getF())
//        );
//        nhmi1.addSignals(
//                new NHMISignal("FourierValuePhsC", mmxu.PhV.getPhsC().getCVal().getMag().getF())
//        );

        nhmi1.addSignals(
                "pdis1. Op general",
                new NHMISignal("Signal prot. 1", pdis1.Op.getGeneral()));
        nhmi1.addSignals(
                "pdis1. Op phsAB",
                new NHMISignal("pdis1 phsAB", pdis1.Op.getPhsA()));
        nhmi1.addSignals(
                "pdis1. Op phsBC",
                new NHMISignal("pdis1 phsBC", pdis1.Op.getPhsB()));
        nhmi1.addSignals(
                "pdis1. Op phsAC",
                new NHMISignal("pdis1 phsAC", pdis1.Op.getPhsC()));

        nhmi1.addSignals(
                "pdis2. Op general",
                new NHMISignal("Signal prot. 2", pdis2.Op.getGeneral()));
        nhmi1.addSignals(
                "pdis2. Op phsAB",
                new NHMISignal("pdis2 phsAB", pdis2.Op.getPhsA()));
        nhmi1.addSignals(
                "pdis2. Op phsBC",
                new NHMISignal("pdis2 phsBC", pdis2.Op.getPhsB()));
        nhmi1.addSignals(
                "pdis2. Op phsAC",
                new NHMISignal("pdis2 phsAC", pdis2.Op.getPhsC()));

        nhmi1.addSignals(
                "Breaker",
                new NHMISignal("Breaker", xcbr.getPos().getCtlVal()));

        NHMIP nhmip = new NHMIP();
        lnList.add(nhmip);
        nhmip.drawCharacteristic("St1", nhmiPointsPDIS1);
        nhmip.drawCharacteristic("St2", nhmiPointsPDIS2);

        nhmip.addSignals(
                new NHMISignal("Zab",
                        mmxu.getZab().getCVal().getX().getF(),
                        mmxu.getZab().getCVal().getY().getF()),
                new NHMISignal("Zbc",
                        mmxu.getZbc().getCVal().getX().getF(),
                        mmxu.getZbc().getCVal().getY().getF()),
                new NHMISignal("Zac",
                        mmxu.getZac().getCVal().getX().getF(),
                        mmxu.getZac().getCVal().getY().getF())
        );


        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);
        }
    }

    private static void drawCharacteristic(List<NHMIPoint<Double, Double>> nhmiPoints, List<double[]> coordinates) {
        //        рисуем верхнюю прямую
        for (int x = (int) coordinates.get(0)[0]; x < coordinates.get(1)[0]; x++) {
            nhmiPoints.add(new NHMIPoint<>((double) x, coordinates.get(0)[1]));
        }
//        рисуем правую линию
        double x2 = coordinates.get(1)[0];
        double y2 = coordinates.get(1)[1];
        double x4 = coordinates.get(3)[0];
        double y4 = coordinates.get(3)[1];
        double m = (y2 - y4) / (x2 - x4);
        double b = y2 - m * x2;
        for (int y = (int) coordinates.get(3)[1]; y < coordinates.get(1)[1]; y++) {
//            y = mx + b -> x = (y - b) / m
            nhmiPoints.add(new NHMIPoint<>((y - b) / m, (double) y));
        }
//        рисуем нижнюю прямую
        for (int x = (int) coordinates.get(2)[0]; x < coordinates.get(3)[0]; x++) {
            nhmiPoints.add(new NHMIPoint<>((double) x, coordinates.get(2)[1]));
        }
//        рисуем левую линию
        double x1 = coordinates.get(0)[0];
        double y1 = coordinates.get(0)[1];
        double x3 = coordinates.get(2)[0];
        double y3 = coordinates.get(2)[1];
        m = (y1 - y3) / (x1 - x3);
        b = y1 - m * x1;
        for (int y = (int) coordinates.get(2)[1]; y < coordinates.get(0)[1]; y++) {
//            y = mx + b -> x = (y - b) / m
            nhmiPoints.add(new NHMIPoint<>((y - b) / m, (double) y));
        }
    }
}
