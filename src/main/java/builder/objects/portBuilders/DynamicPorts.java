package builder.objects.portBuilders;

import builder.objects.ObjBuilder;
import data.Ports;

import java.util.List;

public class DynamicPorts extends ObjBuilder {
    public List<Ports> getInputsD(List inputs, int inputsN){
        char alphabet = 'a';
        //inputsN=inputsN+inputs.size();
        for(int i=0; i<inputsN-1; i++){
            super.inputsL.add(new Ports.Builder()
                    .name("i"+alphabet)
                    .label(Integer.toString(i))
                    .x(-sizeX * 2)
                    .y(((sizeY * 4) / (inputsN + 1) * (i + 1)) - sizeY * 2)
                    .position(i+1)
                    .build()
            );
            alphabet++;
        }
        for(int i=0; i<inputs.size(); i++){
            super.inputsL.add(new Ports.Builder()
                    .name(inputs.get(i).toString())
                    .label(inputs.get(i).toString())
                    .x(-sizeX * 2)
                    .y((((sizeY * 4) / (inputsN + 1) * (inputsN + 1)) - sizeY * 2)-0.2)
                    .position(inputsN)
                    .build()
            );
        }
        return super.inputsL;
    }
    public List<Ports> getOutputsD(int outputsN){
        char alphabet = 'a';
        for(int i=0; i<outputsN; i++){
            super.outputsL.add(new Ports.Builder()
                    .name("o"+alphabet)
                    .label(Integer.toString(i))
                    .x(sizeX * 2)
                    .y(((sizeY * 4) / (outputsN + 1) * (i + 1)) - sizeY * 2)
                    .position(i+1)
                    .build()
            );
            alphabet++;
        }
        return super.outputsL;
    }
}
