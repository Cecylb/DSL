package builder.objects.objBuilders;

import builder.objects.ObjBuilder;
import builder.objects.portBuilders.DynamicPorts;
import data.Rectangles;
import elements.DC;

public class DCBuilder extends ObjBuilder {
    public void setShape(){
        DynamicPorts dn = new DynamicPorts();
        System.out.println(inputsN);
        System.out.println(getInputsN());
        super.inputsL = dn.getInputsD(DC.inputs, getInputsN());
        System.out.println(inputsL);
        super.outputsL = dn.getOutputsD((int)Math.pow(2.0, (double)inputsN));
        System.out.println(outputsL);
        for(DC.Rectangles rec : DC.Rectangles.values()){
            super.rectangles.add(new Rectangles.Builder()
                    .neX(rec.getNeX()*sizeX*2)
                    .neY(rec.getNeY()*sizeY*outputsL.size()/4)
                    .swX(rec.getSwX()*sizeX*2)
                    .swY(rec.getSwY()*sizeY*outputsL.size()/4)
                    .build()
            );
        }
    }
}
