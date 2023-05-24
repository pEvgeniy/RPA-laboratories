package protection.model.dataobjects.settings;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

@Getter
@Setter
public class Point {
    private DataAttribute<Double> xVal = new DataAttribute<>(0d);
    private DataAttribute<Double> yVal = new DataAttribute<>(0d);

    public Point(Double x, Double y){
        xVal.setValue(x);
        yVal.setValue(y);
    }
}
