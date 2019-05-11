package generate;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class IO {

    STGroup element = new STGroupFile("template.stg");

    public String teXconnIO(String objName, String port, String eorw, double sizeX, String index, String linetype){
        ST teXconnIO = element.getInstanceOf("teXconnIO");
        DecimalFormat df = new DecimalFormat("0.0000");
        DecimalFormatSymbols dfs = df.getDecimalFormatSymbols();
        dfs.setDecimalSeparator(',');
        df.setDecimalFormatSymbols(dfs);
        teXconnIO.add("eorw", eorw); // Придумать способ получения величины
        teXconnIO.add("port", port);
        teXconnIO.add("objName", objName);
        teXconnIO.add("space", df.format(sizeX*2));
        teXconnIO.add("index", index);
        teXconnIO.add("linetype", linetype);
        return teXconnIO.render();
    }
}
