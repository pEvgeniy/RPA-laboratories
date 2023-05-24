package protection.model.dataobjects.measurements;

import lombok.Getter;
import lombok.Setter;
import protection.model.common.DataAttribute;

import java.util.ArrayList;
import java.util.List;

public class HWYE {
    @Getter @Setter
    private List<CMV> phsAH = new ArrayList<>();

    @Getter @Setter
    private List<CMV> phsBH = new ArrayList<>();

    @Getter @Setter
    private List<CMV> phsCH = new ArrayList<>();

    @Getter @Setter
    private List<CMV> neutH = new ArrayList<>();

    @Getter @Setter
    private DataAttribute<Integer> harmNumber = new DataAttribute<>(5);

    public HWYE() {
        setLists();
    }

    private void setLists() {
        for(int i = 0; i < harmNumber.getValue(); i++){
            phsAH.add(new CMV());
            phsBH.add(new CMV());
            phsCH.add(new CMV());
            neutH.add(new CMV());
        }
    }
}
