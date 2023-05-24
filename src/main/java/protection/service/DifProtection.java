package protection.service;

import protection.model.dataobjects.settings.CSD;
import protection.model.dataobjects.settings.Point;
import protection.model.logicalnodes.breaker.CSWI;
import protection.model.logicalnodes.breaker.XCBR;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.compare.PHAR;
import protection.model.logicalnodes.compare.PTRC;
import protection.model.logicalnodes.dif.RMXU;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MHAI;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.protections.PDIF;
import protection.model.logicalnodes.protections.PTOC;

import java.util.ArrayList;
import java.util.List;

public class DifProtection {

    private final static List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {
        LCOM lcom = new LCOM();
        lnList.add(lcom);
        lcom.setFilePath(
                "src/main/resources/experiments/LR4/DPB/5 sections/",
                "KzB"
        );

        MMXU mmxu1 = new MMXU(true);
        lnList.add(mmxu1);
        mmxu1.setFreqMeter20(true);
        mmxu1.phsAInstI = lcom.OUT.get(0);
        mmxu1.phsBInstI = lcom.OUT.get(1);
        mmxu1.phsCInstI = lcom.OUT.get(2);

        MMXU mmxu2 = new MMXU(true);
        lnList.add(mmxu2);
        mmxu2.setFreqMeter20(true);
        mmxu2.phsAInstI = lcom.OUT.get(3);
        mmxu2.phsBInstI = lcom.OUT.get(4);
        mmxu2.phsCInstI = lcom.OUT.get(5);

        MMXU mmxu3 = new MMXU(true);
        lnList.add(mmxu3);
        mmxu3.setFreqMeter20(true);
        mmxu3.phsAInstI = lcom.OUT.get(6);
        mmxu3.phsBInstI = lcom.OUT.get(7);
        mmxu3.phsCInstI = lcom.OUT.get(8);

        MMXU mmxu4 = new MMXU(true);
        lnList.add(mmxu4);
        mmxu4.setFreqMeter20(true);
        mmxu4.phsAInstI = lcom.OUT.get(9);
        mmxu4.phsBInstI = lcom.OUT.get(10);
        mmxu4.phsCInstI = lcom.OUT.get(11);

        MMXU mmxu5 = new MMXU(true);
        lnList.add(mmxu5);
        mmxu5.setFreqMeter20(true);
        mmxu5.phsAInstI = lcom.OUT.get(12);
        mmxu5.phsBInstI = lcom.OUT.get(13);
        mmxu5.phsCInstI = lcom.OUT.get(14);

        MHAI mhai1 = new MHAI();
        lnList.add(mhai1);
        mhai1.phsAInstI = lcom.OUT.get(0);
        mhai1.phsBInstI = lcom.OUT.get(1);
        mhai1.phsCInstI = lcom.OUT.get(2);

        MHAI mhai2 = new MHAI();
        lnList.add(mhai2);
        mhai2.phsAInstI = lcom.OUT.get(3);
        mhai2.phsBInstI = lcom.OUT.get(4);
        mhai2.phsCInstI = lcom.OUT.get(5);

        MHAI mhai3 = new MHAI();
        lnList.add(mhai3);
        mhai3.phsAInstI = lcom.OUT.get(6);
        mhai3.phsBInstI = lcom.OUT.get(7);
        mhai3.phsCInstI = lcom.OUT.get(8);

        MHAI mhai4 = new MHAI();
        lnList.add(mhai4);
        mhai4.phsAInstI = lcom.OUT.get(9);
        mhai4.phsBInstI = lcom.OUT.get(10);
        mhai4.phsCInstI = lcom.OUT.get(11);

        MHAI mhai5 = new MHAI();
        lnList.add(mhai5);
        mhai5.phsAInstI = lcom.OUT.get(12);
        mhai5.phsBInstI = lcom.OUT.get(13);
        mhai5.phsCInstI = lcom.OUT.get(14);

        PHAR phar1 = new PHAR();
        lnList.add(phar1);
        phar1.setHA(mhai1.getHA());

        PHAR phar2 = new PHAR();
        lnList.add(phar2);
        phar2.setHA(mhai2.getHA());

        PHAR phar3 = new PHAR();
        lnList.add(phar3);
        phar3.setHA(mhai3.getHA());

        PHAR phar4 = new PHAR();
        lnList.add(phar4);
        phar4.setHA(mhai4.getHA());

        PHAR phar5 = new PHAR();
        lnList.add(phar5);
        phar5.setHA(mhai5.getHA());

        PTRC ptrc1 = new PTRC();
        lnList.add(ptrc1);
        ptrc1.addOp(phar1.getStr());
        ptrc1.addOp(phar2.getStr());
        ptrc1.addOp(phar3.getStr());
        ptrc1.addOp(phar4.getStr());
        ptrc1.addOp(phar5.getStr());

        RMXU rmxu = new RMXU();
        lnList.add(rmxu);
        rmxu.setWye1(mmxu1.A);
        rmxu.setWye2(mmxu2.A);
        rmxu.setWye3(mmxu3.A);
        rmxu.setWye4(mmxu4.A);
        rmxu.setWye5(mmxu5.A);

        PDIF pdif = new PDIF();
        lnList.add(pdif);
        pdif.OpDlTmms.setSetVal(10);
        pdif.setDifAClc(rmxu.getALoc());
        pdif.setRstA(rmxu.getRstA());
        pdif.setBlc(ptrc1.getTr());
        CSD csd = new CSD();
        List<Point> points = List.of(
                new Point(0d, 5000d),
                new Point(16000d, 5000d),
                new Point(20000d, 6000d),
                new Point(25000d, 7500d)
        );
        csd.setCrvPts(points);
        pdif.setTmASt(csd);

        PTOC ptoc = new PTOC();
        ptoc.A = rmxu.getALoc();
        ptoc.StrVal.getSetMag().getF().setValue(25000d);
        ptoc.OpDlTmms.setSetVal(10);

        PTRC ptrc2 = new PTRC();
        lnList.add(ptrc2);
        ptrc2.addOp(pdif.getOp());
        ptrc2.addOp(ptoc.Op);

        CSWI cswi = new CSWI();
        lnList.add(cswi);
        cswi.setOpOpn1(ptrc2.getTr());
        cswi.getOpOpn1().getGeneral().setValue(false);

        XCBR xcbr1 = new XCBR();
        lnList.add(xcbr1);
        xcbr1.setPos(cswi.getPos());

        XCBR xcbr2 = new XCBR();
        lnList.add(xcbr2);
        xcbr2.setPos(cswi.getPos());

        XCBR xcbr3 = new XCBR();
        lnList.add(xcbr3);
        xcbr3.setPos(cswi.getPos());

        XCBR xcbr4 = new XCBR();
        lnList.add(xcbr4);
        xcbr4.setPos(cswi.getPos());

        XCBR xcbr5 = new XCBR();
        lnList.add(xcbr5);
        xcbr5.setPos(cswi.getPos());

        NHMI nhmi = new NHMI();
        lnList.add(nhmi);
        nhmi.addSignals(new NHMISignal("iDif", pdif.getDifAClc().getPhsA().getCVal().getMag().getF()));
        nhmi.addSignals(new NHMISignal("iTorm", pdif.getRstA().getPhsA().getCVal().getMag().getF()));
        nhmi.addSignals(new NHMISignal("Garm block", ptrc1.getTr().getGeneral()));
        nhmi.addSignals(new NHMISignal("dif prot", pdif.getOp().getGeneral()));
        nhmi.addSignals(new NHMISignal("ptoc prot", ptoc.Op.getGeneral()));
        nhmi.addSignals(new NHMISignal("breaker1", xcbr1.getPos().getCtlVal()));
        nhmi.addSignals(new NHMISignal("breaker2", xcbr1.getPos().getCtlVal()));
        nhmi.addSignals(new NHMISignal("breaker3", xcbr1.getPos().getCtlVal()));
        nhmi.addSignals(new NHMISignal("breaker4", xcbr1.getPos().getCtlVal()));
        nhmi.addSignals(new NHMISignal("breaker5", xcbr1.getPos().getCtlVal()));

        NHMI nhmi1 = new NHMI();
        lnList.add(nhmi1);
        nhmi1.addSignals(new NHMISignal("instIa_1", lcom.OUT.get(0).getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("instIb_1", lcom.OUT.get(1).getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("instIc_1", lcom.OUT.get(2).getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("Ia_filtered", mmxu1.A.getPhsA().getCVal().getMag().getF()));
        nhmi1.addSignals(new NHMISignal("freqA", mmxu1.Frequency.getInstMag().getF()));
        nhmi1.addSignals(new NHMISignal("Ib_filtered", mmxu1.A.getPhsB().getCVal().getMag().getF()));
        nhmi1.addSignals(new NHMISignal("Ic_filtered", mmxu1.A.getPhsC().getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ia_1Gr", mhai1.getHA().getPhsAH().get(0).getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ia_5Gr", mhai1.getHA().getPhsAH().get(4).getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ib_1Gr", mhai1.getHA().getPhsBH().get(0).getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ib_5Gr", mhai1.getHA().getPhsBH().get(4).getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ic_1Gr", mhai1.getHA().getPhsCH().get(0).getCVal().getMag().getF()));
//        nhmi1.addSignals(new NHMISignal("Ic_5Gr", mhai1.getHA().getPhsCH().get(4).getCVal().getMag().getF()));
        nhmi1.addSignals(new NHMISignal("raznA", phar1.getRaznA().getCVal().getMag().getF()));
        nhmi1.addSignals(new NHMISignal("raznB", phar1.getRaznB().getCVal().getMag().getF()));
        nhmi1.addSignals(new NHMISignal("raznC", phar1.getRaznC().getCVal().getMag().getF()));



        NHMI nhmi2 = new NHMI();
        lnList.add(nhmi2);
        nhmi2.addSignals(new NHMISignal("instIa_2", lcom.OUT.get(3).getInstMag().getF()));
        nhmi2.addSignals(new NHMISignal("instIb_2", lcom.OUT.get(4).getInstMag().getF()));
        nhmi2.addSignals(new NHMISignal("instIc_2", lcom.OUT.get(5).getInstMag().getF()));
        nhmi2.addSignals(new NHMISignal("Ia_filtered", mmxu2.A.getPhsA().getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("freqA", mmxu2.Frequency.getInstMag().getF()));
        nhmi2.addSignals(new NHMISignal("Ib_filtered", mmxu2.A.getPhsB().getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("Ic_filtered", mmxu2.A.getPhsC().getCVal().getMag().getF()));
//        nhmi2.addSignals(new NHMISignal("Ia_1Gr", mhai2.getHA().getPhsAH().get(0).getCVal().getMag().getF()));
//        nhmi2.addSignals(new NHMISignal("Ia_5Gr", mhai2.getHA().getPhsAH().get(4).getCVal().getMag().getF()));
//        nhmi2.addSignals(new NHMISignal("Ib_1Gr", mhai2.getHA().getPhsBH().get(0).getCVal().getMag().getF()));
//        nhmi2.addSignals(new NHMISignal("Ib_5Gr", mhai2.getHA().getPhsBH().get(4).getCVal().getMag().getF()));
//        nhmi2.addSignals(new NHMISignal("Ic_1Gr", mhai2.getHA().getPhsCH().get(0).getCVal().getMag().getF()));
//        nhmi2.addSignals(new NHMISignal("Ic_5Gr", mhai2.getHA().getPhsCH().get(4).getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("raznA", phar2.getRaznA().getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("raznB", phar2.getRaznB().getCVal().getMag().getF()));
        nhmi2.addSignals(new NHMISignal("raznC", phar2.getRaznC().getCVal().getMag().getF()));

        NHMI nhmi3 = new NHMI();
        lnList.add(nhmi3);
        nhmi3.addSignals(new NHMISignal("instIa_3", lcom.OUT.get(6).getInstMag().getF()));
        nhmi3.addSignals(new NHMISignal("instIb_3", lcom.OUT.get(7).getInstMag().getF()));
        nhmi3.addSignals(new NHMISignal("instIc_3", lcom.OUT.get(8).getInstMag().getF()));
        nhmi3.addSignals(new NHMISignal("Ia_filtered", mmxu3.A.getPhsA().getCVal().getMag().getF()));
        nhmi3.addSignals(new NHMISignal("freqA", mmxu3.Frequency.getInstMag().getF()));
        nhmi3.addSignals(new NHMISignal("Ib_filtered", mmxu3.A.getPhsB().getCVal().getMag().getF()));
        nhmi3.addSignals(new NHMISignal("Ic_filtered", mmxu3.A.getPhsC().getCVal().getMag().getF()));
//        nhmi3.addSignals(new NHMISignal("Ia_1Gr", mhai3.getHA().getPhsAH().get(0).getCVal().getMag().getF()));
//        nhmi3.addSignals(new NHMISignal("Ia_5Gr", mhai3.getHA().getPhsAH().get(4).getCVal().getMag().getF()));
//        nhmi3.addSignals(new NHMISignal("Ib_1Gr", mhai3.getHA().getPhsBH().get(0).getCVal().getMag().getF()));
//        nhmi3.addSignals(new NHMISignal("Ib_5Gr", mhai3.getHA().getPhsBH().get(4).getCVal().getMag().getF()));
//        nhmi3.addSignals(new NHMISignal("Ic_1Gr", mhai3.getHA().getPhsCH().get(0).getCVal().getMag().getF()));
//        nhmi3.addSignals(new NHMISignal("Ic_5Gr", mhai3.getHA().getPhsCH().get(4).getCVal().getMag().getF()));
        nhmi3.addSignals(new NHMISignal("raznA", phar3.getRaznA().getCVal().getMag().getF()));
        nhmi3.addSignals(new NHMISignal("raznB", phar3.getRaznB().getCVal().getMag().getF()));
        nhmi3.addSignals(new NHMISignal("raznC", phar3.getRaznC().getCVal().getMag().getF()));

        NHMI nhmi4 = new NHMI();
        lnList.add(nhmi4);
        nhmi4.addSignals(new NHMISignal("instIa_4", lcom.OUT.get(9).getInstMag().getF()));
        nhmi4.addSignals(new NHMISignal("instIb_4", lcom.OUT.get(10).getInstMag().getF()));
        nhmi4.addSignals(new NHMISignal("instIc_4", lcom.OUT.get(11).getInstMag().getF()));
        nhmi4.addSignals(new NHMISignal("Ia_filtered", mmxu4.A.getPhsA().getCVal().getMag().getF()));
        nhmi4.addSignals(new NHMISignal("freqA", mmxu4.Frequency.getInstMag().getF()));
        nhmi4.addSignals(new NHMISignal("Ib_filtered", mmxu4.A.getPhsB().getCVal().getMag().getF()));
        nhmi4.addSignals(new NHMISignal("Ic_filtered", mmxu4.A.getPhsC().getCVal().getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Ia_1Gr", mhai4.getHA().getPhsAH().get(0).getCVal().getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Ia_5Gr", mhai4.getHA().getPhsAH().get(4).getCVal().getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Ib_1Gr", mhai4.getHA().getPhsBH().get(0).getCVal().getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Ib_5Gr", mhai4.getHA().getPhsBH().get(4).getCVal().getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Ic_1Gr", mhai4.getHA().getPhsCH().get(0).getCVal().getMag().getF()));
//        nhmi4.addSignals(new NHMISignal("Ic_5Gr", mhai4.getHA().getPhsCH().get(4).getCVal().getMag().getF()));
        nhmi4.addSignals(new NHMISignal("raznA", phar4.getRaznA().getCVal().getMag().getF()));
        nhmi4.addSignals(new NHMISignal("raznB", phar4.getRaznB().getCVal().getMag().getF()));
        nhmi4.addSignals(new NHMISignal("raznC", phar4.getRaznC().getCVal().getMag().getF()));

        NHMI nhmi5 = new NHMI();
        lnList.add(nhmi5);
        nhmi5.addSignals(new NHMISignal("instIa_5", lcom.OUT.get(12).getInstMag().getF()));
        nhmi5.addSignals(new NHMISignal("instIb_5", lcom.OUT.get(13).getInstMag().getF()));
        nhmi5.addSignals(new NHMISignal("instIc_5", lcom.OUT.get(14).getInstMag().getF()));
        nhmi5.addSignals(new NHMISignal("Ia_filtered", mmxu5.A.getPhsA().getCVal().getMag().getF()));
        nhmi5.addSignals(new NHMISignal("freqA", mmxu5.Frequency.getInstMag().getF()));
        nhmi5.addSignals(new NHMISignal("Ib_filtered", mmxu5.A.getPhsB().getCVal().getMag().getF()));
        nhmi5.addSignals(new NHMISignal("Ic_filtered", mmxu5.A.getPhsC().getCVal().getMag().getF()));
//        nhmi5.addSignals(new NHMISignal("Ia_1Gr", mhai5.getHA().getPhsAH().get(0).getCVal().getMag().getF()));
//        nhmi5.addSignals(new NHMISignal("Ia_5Gr", mhai5.getHA().getPhsAH().get(4).getCVal().getMag().getF()));
//        nhmi5.addSignals(new NHMISignal("Ib_1Gr", mhai5.getHA().getPhsBH().get(0).getCVal().getMag().getF()));
//        nhmi5.addSignals(new NHMISignal("Ib_5Gr", mhai5.getHA().getPhsBH().get(4).getCVal().getMag().getF()));
//        nhmi5.addSignals(new NHMISignal("Ic_1Gr", mhai5.getHA().getPhsCH().get(0).getCVal().getMag().getF()));
//        nhmi5.addSignals(new NHMISignal("Ic_5Gr", mhai5.getHA().getPhsCH().get(4).getCVal().getMag().getF()));
        nhmi5.addSignals(new NHMISignal("raznA", phar5.getRaznA().getCVal().getMag().getF()));
        nhmi5.addSignals(new NHMISignal("raznB", phar5.getRaznB().getCVal().getMag().getF()));
        nhmi5.addSignals(new NHMISignal("raznC", phar5.getRaznC().getCVal().getMag().getF()));

        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);
        }
    }
}
