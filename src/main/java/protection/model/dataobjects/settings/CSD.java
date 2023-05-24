package protection.model.dataobjects.settings;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CSD {
    private List<Point> crvPts = new ArrayList<>();
    private DataAttribute<Integer> numPts = new DataAttribute<>(0);
}
