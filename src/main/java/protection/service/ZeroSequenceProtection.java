package protection.service;

import protection.model.dataobjects.protection.Direction;
import protection.model.logicalnodes.breaker.CSWI;
import protection.model.logicalnodes.breaker.XCBR;
import protection.model.logicalnodes.common.LN;
import protection.model.logicalnodes.gui.NHMI;
import protection.model.logicalnodes.gui.other.NHMISignal;
import protection.model.logicalnodes.input.LCOM;
import protection.model.logicalnodes.measurements.MMXU;
import protection.model.logicalnodes.measurements.MSQI;
import protection.model.logicalnodes.protections.PTOC;
import protection.model.logicalnodes.protections.RDIR;

import java.util.ArrayList;
import java.util.List;

public class ZeroSequenceProtection {
    private final static List<LN> lnList = new ArrayList<>();

    public static void main(String[] args) {
//        COMTRADE
        LCOM lcom = new LCOM();
        lnList.add(lcom);
        lcom.setFilePath(
                "src/main/resources/experiments/LR2/",
                "KZ4"
        );

//        Measurements
        MMXU mmxu = new MMXU();
        lnList.add(mmxu);
        mmxu.phsAInstV = lcom.OUT.get(0);
        mmxu.phsBInstV = lcom.OUT.get(1);
        mmxu.phsCInstV = lcom.OUT.get(2);

        mmxu.phsAInstI = lcom.OUT.get(3);
        mmxu.phsBInstI = lcom.OUT.get(4);
        mmxu.phsCInstI = lcom.OUT.get(5);

//        Zero sequences
        MSQI msqi = new MSQI();
        lnList.add(msqi);
        msqi.setImbA(mmxu.A);
        msqi.setImbV(mmxu.PhV);

//        Direction
        RDIR rdir = new RDIR();
        lnList.add(rdir);
        rdir.setW(mmxu.W);

//        Ненаправленные ступени
        PTOC ptoc1 = new PTOC();
        lnList.add(ptoc1);
        ptoc1.A.setPhsA(msqi.getSeqA().getC3());
        ptoc1.A.setPhsB(msqi.getSeqA().getC3());
        ptoc1.A.setPhsC(msqi.getSeqA().getC3());
        ptoc1.StrVal.getSetMag().getF().setValue(1000d);
        ptoc1.OpDlTmms.setSetVal(500);

        PTOC ptoc2 = new PTOC();
        lnList.add(ptoc2);
        ptoc2.A.setPhsA(msqi.getSeqA().getC3());
        ptoc2.A.setPhsB(msqi.getSeqA().getC3());
        ptoc2.A.setPhsC(msqi.getSeqA().getC3());
        ptoc2.StrVal.getSetMag().getF().setValue(500d);
        ptoc2.OpDlTmms.setSetVal(200);

        PTOC ptoc3 = new PTOC();
        lnList.add(ptoc3);
        ptoc3.A.setPhsA(msqi.getSeqA().getC3());
        ptoc3.A.setPhsB(msqi.getSeqA().getC3());
        ptoc3.A.setPhsC(msqi.getSeqA().getC3());
        ptoc3.StrVal.getSetMag().getF().setValue(70d);
        ptoc3.OpDlTmms.setSetVal(100);

//        Направленные ступени
        PTOC ptoc1d = new PTOC();
        lnList.add(ptoc1d);
        ptoc1d.A.setPhsA(msqi.getSeqA().getC3());
        ptoc1d.A.setPhsB(msqi.getSeqA().getC3());
        ptoc1d.A.setPhsC(msqi.getSeqA().getC3());
        ptoc1d.StrVal.getSetMag().getF().setValue(1000d);
        ptoc1d.OpDlTmms.setSetVal(500);
        ptoc1d.DirMod.getStVal().setValue(Direction.FORWARD);
        ptoc1d.Dir = rdir.getDir();

        PTOC ptoc2d = new PTOC();
        lnList.add(ptoc2d);
        ptoc2d.A.setPhsA(msqi.getSeqA().getC3());
        ptoc2d.A.setPhsB(msqi.getSeqA().getC3());
        ptoc2d.A.setPhsC(msqi.getSeqA().getC3());
        ptoc2d.StrVal.getSetMag().getF().setValue(500d);
        ptoc2d.OpDlTmms.setSetVal(200);
        ptoc2d.DirMod.getStVal().setValue(Direction.FORWARD);
        ptoc2d.Dir = rdir.getDir();

        PTOC ptoc3d = new PTOC();
        lnList.add(ptoc3d);
        ptoc3d.A.setPhsA(msqi.getSeqA().getC3());
        ptoc3d.A.setPhsB(msqi.getSeqA().getC3());
        ptoc3d.A.setPhsC(msqi.getSeqA().getC3());
        ptoc3d.StrVal.getSetMag().getF().setValue(80d);
        ptoc3d.OpDlTmms.setSetVal(400);
        ptoc3d.DirMod.getStVal().setValue(Direction.FORWARD);
        ptoc3d.Dir = rdir.getDir();

//        Signal to Q
        CSWI cswi = new CSWI();
        lnList.add(cswi);
        cswi.setAPV(true);
        cswi.setOpOpn1(ptoc1.Op);
        cswi.getOpOpn1().getGeneral().setValue(false);
        cswi.setOpOpn2(ptoc2.Op);
        cswi.getOpOpn2().getGeneral().setValue(false);
        cswi.setOpOpn3(ptoc3.Op);
        cswi.getOpOpn3().getGeneral().setValue(false);
        cswi.setOpOpn4(ptoc1d.Op);
        cswi.getOpOpn4().getGeneral().setValue(false);
        cswi.setOpOpn5(ptoc2d.Op);
        cswi.getOpOpn5().getGeneral().setValue(false);
        cswi.setOpOpn6(ptoc3d.Op);
        cswi.getOpOpn6().getGeneral().setValue(false);
        cswi.OpDlTmms1.setSetVal(ptoc1.OpDlTmms.getSetVal());
        cswi.OpDlTmms2.setSetVal(ptoc2.OpDlTmms.getSetVal());
        cswi.OpDlTmms3.setSetVal(ptoc3.OpDlTmms.getSetVal());
        cswi.OpDlTmms4.setSetVal(ptoc1d.OpDlTmms.getSetVal());
        cswi.OpDlTmms5.setSetVal(ptoc2d.OpDlTmms.getSetVal());
        cswi.OpDlTmms5.setSetVal(ptoc3d.OpDlTmms.getSetVal());

//        Q
        XCBR xcbr = new XCBR();
        lnList.add(xcbr);
        xcbr.setPos(cswi.getPos());

//        GUI
        NHMI nhmi = new NHMI();
        lnList.add(nhmi);
        nhmi.addSignals(
                new NHMISignal("InstValuePhsA", lcom.OUT.get(3).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("InstValuePhsB", lcom.OUT.get(4).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("InstValuePhsC", lcom.OUT.get(5).getInstMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Iпп", msqi.getSeqA().getC1().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Iоп", msqi.getSeqA().getC2().getCVal().getMag().getF())
        );
        nhmi.addSignals(
                new NHMISignal("Iнп", msqi.getSeqA().getC3().getCVal().getMag().getF()),
                new NHMISignal("РТОС1", ptoc1.StrVal.getSetMag().getF()),
                new NHMISignal("РТОС2", ptoc2.StrVal.getSetMag().getF()),
                new NHMISignal("РТОС3", ptoc3.StrVal.getSetMag().getF())
        );
//        nhmi.addSignals(
//                new NHMISignal("Wa", mmxu.W.getPhsA().getCVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Wb", mmxu.W.getPhsB().getCVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Wc", mmxu.W.getPhsC().getCVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("Wg", mmxu.Wg.getPhsA().getCVal().getMag().getF())
//        );
//        nhmi.addSignals(
//                new NHMISignal("WA", mmxu.WA.getPhsA().getCVal().getMag().getF())
//        );
        nhmi.addSignals(
                "ptoc1. Op general",
                new NHMISignal("Signal prot. 1", ptoc1.Op.getGeneral()));
        nhmi.addSignals(
                "ptoc2. Op general",
                new NHMISignal("Signal prot. 1", ptoc2.Op.getGeneral()));
        nhmi.addSignals(
                "ptoc3. Op general",
                new NHMISignal("Signal prot. 1", ptoc3.Op.getGeneral()));
        nhmi.addSignals(
                "ptoc1d. Op general",
                new NHMISignal("Signal prot. 1", ptoc1d.Op.getGeneral()));
        nhmi.addSignals(
                "ptoc2d. Op general",
                new NHMISignal("Signal prot. 1", ptoc2d.Op.getGeneral()));
        nhmi.addSignals(
                "ptoc3d. Op general",
                new NHMISignal("Signal prot. 1", ptoc3d.Op.getGeneral()));
        nhmi.addSignals(
                "Breaker",
                new NHMISignal("Breaker", xcbr.getPos().getCtlVal()));

        while (lcom.hasNextData()) {
            lnList.forEach(LN::process);
        }
    }
}
