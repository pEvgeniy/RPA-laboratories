package protection.model.logicalnodes.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.dataobjects.measurements.SEQ;
import protection.model.dataobjects.measurements.WYE;
import protection.model.logicalnodes.common.LN;

@Getter @Setter
public class MSQI extends LN {
    private SEQ SeqA = new SEQ();
    private SEQ SeqV = new SEQ();

    private WYE A = new WYE();

    private WYE ImbA = new WYE();
    private WYE ImbV = new WYE();
    @Override
    public void process() {
        zeroSequenceExecutor(ImbA, SeqA);
        zeroSequenceExecutor(ImbV, SeqV);
    }

    private void zeroSequenceExecutor(WYE input, SEQ result) {
//        Прямая последовательность и повороты на 120
        A.getPhsA().getCVal().setOrthogonalComponents(
                input.getPhsA().getCVal().getMag().getF().getValue(),
                input.getPhsA().getCVal().getAng().getF().getValue()
        );

        A.getPhsB().getCVal().setOrthogonalComponents(
                input.getPhsB().getCVal().getMag().getF().getValue(),
                input.getPhsB().getCVal().getAng().getF().getValue() - 120
        );

        A.getPhsC().getCVal().setOrthogonalComponents(
                input.getPhsC().getCVal().getMag().getF().getValue(),
                input.getPhsC().getCVal().getAng().getF().getValue() + 120
        );

        result.getC1().getCVal().toVector(
                (A.getPhsA().getCVal().getX().getF().getValue() +
                        A.getPhsB().getCVal().getX().getF().getValue() +
                        A.getPhsC().getCVal().getX().getF().getValue()) / 3,

                (A.getPhsA().getCVal().getY().getF().getValue() +
                        A.getPhsB().getCVal().getY().getF().getValue() +
                        A.getPhsC().getCVal().getY().getF().getValue()) / 3
        );

//        Обратная последовательность и повороты на -120
        A.getPhsB().getCVal().setOrthogonalComponents(
                input.getPhsB().getCVal().getMag().getF().getValue(),
                input.getPhsB().getCVal().getAng().getF().getValue() + 120
        );

        A.getPhsC().getCVal().setOrthogonalComponents(
                input.getPhsC().getCVal().getMag().getF().getValue(),
                input.getPhsC().getCVal().getAng().getF().getValue() - 120
        );

        result.getC2().getCVal().toVector(
                (A.getPhsA().getCVal().getX().getF().getValue() +
                        A.getPhsB().getCVal().getX().getF().getValue() +
                        A.getPhsC().getCVal().getX().getF().getValue()) / 3,

                (A.getPhsA().getCVal().getY().getF().getValue() +
                        A.getPhsB().getCVal().getY().getF().getValue() +
                        A.getPhsC().getCVal().getY().getF().getValue()) / 3
        );

//        Нулевая последовательность
        A.getPhsB().getCVal().setOrthogonalComponents(
                input.getPhsB().getCVal().getMag().getF().getValue(),
                input.getPhsB().getCVal().getAng().getF().getValue()
        );

        A.getPhsC().getCVal().setOrthogonalComponents(
                input.getPhsC().getCVal().getMag().getF().getValue(),
                input.getPhsC().getCVal().getAng().getF().getValue()
        );

        result.getC3().getCVal().toVector(
                (A.getPhsA().getCVal().getX().getF().getValue() +
                        A.getPhsB().getCVal().getX().getF().getValue() +
                        A.getPhsC().getCVal().getX().getF().getValue()) / 3,

                (A.getPhsA().getCVal().getY().getF().getValue() +
                        A.getPhsB().getCVal().getY().getF().getValue() +
                        A.getPhsC().getCVal().getY().getF().getValue()) / 3
        );
    }
}
