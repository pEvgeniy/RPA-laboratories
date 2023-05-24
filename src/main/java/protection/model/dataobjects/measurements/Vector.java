package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DATA;

public class Vector extends DATA {

    @Getter @Setter
    private AnalogueValue mag = new AnalogueValue();
    @Getter @Setter
    private AnalogueValue ang = new AnalogueValue();
    @Getter @Setter
    private AnalogueValue x = new AnalogueValue();
    @Getter @Setter
    private AnalogueValue y = new AnalogueValue();

    public void toVector(double magToSet, double angToSet) {
        mag.getF().setValue(Math.sqrt(Math.pow(magToSet, 2) + Math.pow(angToSet, 2)));
        ang.getF().setValue(Math.toDegrees(Math.atan2(angToSet, magToSet)));
    }

    public void setOrthogonalComponents(double magToSet, double angToSet) {
        x.getF().setValue(magToSet * Math.cos(Math.toRadians(angToSet)));
        y.getF().setValue(magToSet * Math.sin(Math.toRadians(angToSet)));
    }

    public double getOrtX(double mag, double ang) {
        return mag * Math.cos(Math.toRadians(ang));
    }

    public double getOrtY(double mag, double ang) {
        return mag * Math.sin(Math.toRadians(ang));
    }

    public void setResistance(CMV current, CMV voltage) {
        ang.getF().setValue(voltage.getCVal().getAng().getF().getValue() -
                current.getCVal().getAng().getF().getValue());
        mag.getF().setValue(voltage.getCVal().getMag().getF().getValue() /
                current.getCVal().getMag().getF().getValue());
        x.getF().setValue(mag.getF().getValue() * Math.cos(Math.toRadians(ang.getF().getValue())));
        y.getF().setValue(mag.getF().getValue() * Math.sin(Math.toRadians(ang.getF().getValue())));
    }
}
