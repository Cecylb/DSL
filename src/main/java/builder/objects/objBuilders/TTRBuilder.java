package builder.objects.objBuilders;

import builder.objects.ObjBuilder;
import builder.objects.portBuilders.StaticPorts;
import data.Rectangles;
import elements.TTr;

public class TTRBuilder extends ObjBuilder {
    public void setShape(){
        StaticPorts sp = new StaticPorts();
        inputsL = sp.getInputs(TTr.inputs);
        outputsL = sp.getOutputs(TTr.outputs);
        for(TTr.Rectangles rec : TTr.Rectangles.values()){
            rectangles.add(new Rectangles.Builder()
                    .neX(rec.getNeX()*sizeX*2)
                    .neY(rec.getNeY()*sizeY*2)
                    .swX(rec.getSwX()*sizeX*2)
                    .swY(rec.getSwY()*sizeY*2)
                    .build()
            );
        }
    }
}
