package protection.model.logicalnodes.input;

import lombok.SneakyThrows;
import org.apache.commons.io.FileUtils;
import protection.model.dataobjects.measurements.MV;
import protection.model.logicalnodes.common.LN;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Александр Холодов
 * @created 03/2023
 * @description
 */
public class LCOM extends LN {

    private File cfgFile;
    private File datFile;

    private List<String> cfgDataFile = new ArrayList<>();
    private List<String> datDataFile = new ArrayList<>();

    private List<Double> aCoefList = new ArrayList<>();
    private List<Double> bCoefList = new ArrayList<>();

    private int analogNumber = 0;
    private int discreteNumber = 0;

    private Iterator<String> dataIterator;

    public final List<MV> OUT = new ArrayList<>();

    public LCOM() {
        for (int i = 0; i < 20; i++) {
            OUT.add(new MV());
        }
    }

    @Override
    public void process() {
        if(dataIterator.hasNext()) {
            String[] line = dataIterator.next().split(",");

            for (int i = 0; i < analogNumber; i++) {
                double value = Double.parseDouble(line[i + 2]);
                value *= aCoefList.get(i);
                value += bCoefList.get(i);

                OUT.get(i).getInstMag().getF().setValue(value * 1000);
            }
        }
    }

    public boolean hasNextData() {
        return dataIterator.hasNext();
    }

    @SneakyThrows
    public void setFilePath(String path, String name) {
        cfgFile = new File(path + name + ".cfg");
        datFile = new File(path + name + ".dat");

        cfgDataFile = FileUtils.readLines(cfgFile, StandardCharsets.UTF_8);
        datDataFile = FileUtils.readLines(datFile, StandardCharsets.UTF_8);

        dataIterator = datDataFile.listIterator();

        extractCfgFileData();
    }

    private void extractCfgFileData() {
        analogNumber = Integer.parseInt(cfgDataFile.get(1).split(",")[1].replace("A", ""));
        discreteNumber = Integer.parseInt(cfgDataFile.get(1).split(",")[2].replace("D", ""));

        for (int i = 2; i < 2 + analogNumber; i++) {
            aCoefList.add(Double.parseDouble(cfgDataFile.get(i).split(",")[5]));
            bCoefList.add(Double.parseDouble(cfgDataFile.get(i).split(",")[6]));
        }
    }

}
