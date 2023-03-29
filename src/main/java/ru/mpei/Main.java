package ru.mpei;

import protection.model.logicalnodes.breaker.CSWI;
import protection.model.logicalnodes.breaker.XCBR;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.protections.PTOC;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Александр Холодов
 * @created ${MONTH}/${YEAR}
 * @description
 */
public class Main {

    private final static List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {
//        COMTRADE
        LCOM lcom = new LCOM();
        lnList.add(lcom);
        lcom.setFilePath(
                "src/main/resources/experiments/end-of-the-line/",
                "PhABC20"
        );

//        Measurements
        MMXU mmxu = new MMXU();
        lnList.add(mmxu);
        mmxu.phsAInst = lcom.OUT.get(0);
        mmxu.phsBInst = lcom.OUT.get(1);
        mmxu.phsCInst = lcom.OUT.get(2);

//        Protection 1
        PTOC ptoc1 = new PTOC();
        lnList.add(ptoc1);
        ptoc1.StrVal.getSetMag().getF().setValue(2500d);
        ptoc1.A = mmxu.A;

//        Protection 2
        PTOC ptoc2 = new PTOC();
        lnList.add(ptoc2);
        ptoc2.StrVal.getSetMag().getF().setValue(2100d);
        ptoc2.OpDlTmms.setSetVal(250);
        ptoc2.A = mmxu.A;

//        Signal to Q
        CSWI cswi = new CSWI();
        lnList.add(cswi);
        cswi.setOpOpn1(ptoc1.Op);
        cswi.getOpOpn1().getGeneral().setValue(false);
        cswi.setOpOpn2(ptoc2.Op);
        cswi.getOpOpn2().getGeneral().setValue(false);

//        Q
        XCBR xcbr = new XCBR();
        lnList.add(xcbr);
        xcbr.setPos(cswi.getPos());


//        GUI
        NHMI nhmi1 = new NHMI();
        lnList.add(nhmi1);
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsA", lcom.OUT.get(0).getInstMag().getF()));
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsB", lcom.OUT.get(1).getInstMag().getF()));
        nhmi1.addSignals(
                new NHMISignal("InstValuePhsC", lcom.OUT.get(2).getInstMag().getF()));

        NHMI nhmi2 =new NHMI();
        lnList.add(nhmi2);
        nhmi2.addSignals(
                new NHMISignal("FourierValuePhsA", mmxu.A.getPhsA().getCVal().getMag().getF()),
                new NHMISignal("Ust. 1", ptoc1.StrVal.getSetMag().getF()),
                new NHMISignal("Ust. 2", ptoc2.StrVal.getSetMag().getF()));
        nhmi2.addSignals(
                new NHMISignal("FourierValuePhsB", mmxu.A.getPhsB().getCVal().getMag().getF()),
                new NHMISignal("Ust. 1", ptoc1.StrVal.getSetMag().getF()),
                new NHMISignal("Ust. 2", ptoc2.StrVal.getSetMag().getF()));
        nhmi2.addSignals(
                new NHMISignal("FourierValuePhsC", mmxu.A.getPhsC().getCVal().getMag().getF()),
                new NHMISignal("Ust. 1", ptoc1.StrVal.getSetMag().getF()),
                new NHMISignal("Ust. 2", ptoc2.StrVal.getSetMag().getF()));

        NHMI nhmi3 =new NHMI();
        lnList.add(nhmi3);
        nhmi3.addSignals(
                new NHMISignal("FourierValuePhsA", mmxu.A.getPhsA().getCVal().getMag().getF()),
                new NHMISignal("Ust. 1", ptoc1.StrVal.getSetMag().getF()),
                new NHMISignal("Ust. 2", ptoc2.StrVal.getSetMag().getF()));
        nhmi3.addSignals(
                new NHMISignal("FourierValuePhsB", mmxu.A.getPhsB().getCVal().getMag().getF()),
                new NHMISignal("Ust. 1", ptoc1.StrVal.getSetMag().getF()),
                new NHMISignal("Ust. 2", ptoc2.StrVal.getSetMag().getF()));
        nhmi3.addSignals(
                new NHMISignal("FourierValuePhsC", mmxu.A.getPhsC().getCVal().getMag().getF()),
                new NHMISignal("Ust. 1", ptoc1.StrVal.getSetMag().getF()),
                new NHMISignal("Ust. 2", ptoc2.StrVal.getSetMag().getF()));

        nhmi3.addSignals(
                "ptoc1. Op general",
                new NHMISignal("Signal prot. 1", ptoc1.Op.getGeneral()));
        nhmi3.addSignals(
                "ptoc1. Op phsA",
                new NHMISignal("ptoc1 phsA", ptoc1.Op.getPhsA()));
        nhmi3.addSignals(
                "ptoc1. Op phsB",
                new NHMISignal("ptoc1 phsB", ptoc1.Op.getPhsB()));
        nhmi3.addSignals(
                "ptoc1. Op phsC",
                new NHMISignal("ptoc1 phsC", ptoc1.Op.getPhsC()));

        nhmi3.addSignals(
                "ptoc2. Op general",
                new NHMISignal("Signal prot. 2", ptoc2.Op.getGeneral()));
        nhmi3.addSignals(
                "ptoc2. Op phsA",
                new NHMISignal("ptoc2 phsA", ptoc2.Op.getPhsA()));
        nhmi3.addSignals(
                "ptoc2. Op phsB",
                new NHMISignal("ptoc2 phsB", ptoc2.Op.getPhsB()));
        nhmi3.addSignals(
                "ptoc2. Op phsC",
                new NHMISignal("ptoc2 phsC", ptoc2.Op.getPhsC()));

        nhmi3.addSignals(
                "Breaker",
                new NHMISignal("Breaker", xcbr.getPos().getCtlVal()));

        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);
        }

//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//
//        nhmi1.dispose();
    }
}