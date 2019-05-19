package builder.objects.portBuilders;

import builder.objects.ObjBuilder;
import data.Ports;

import java.util.List;

public class StaticPorts extends ObjBuilder {
    public List<Ports> getInputs(List inputs) {
        for (int i=0; i<inputs.size(); i++) {
            inputsL.add(new Ports.Builder()
                    .name(inputs.get(i).toString())
                    .label(inputs.get(i).toString())
                    .x(-sizeX * 2)
                    .y(((sizeY * 4) / (inputs.size() + 1) * (i + 1)) - sizeY * 2)
                    .position(i+1)
                    .build()
            );
        }
        return inputsL;
    }
    public List<Ports>getOutputs(List outputs) {
        for (int i=0; i<outputs.size(); i++) {
            outputsL.add(new Ports.Builder()
                    .name(outputs.get(i).toString())
                    .label(outputs.get(i).toString())
                    .x(sizeX * 2) // ВОЗМОЖНЫ ПРОБЛЕМЫ С ОТРИСОВКОЙ СОЕДИНЕНИЙ
                    .y(((sizeY * 4) / (outputs.size() + 1) * (i + 1)) - sizeY * 2)
                    .position(i+1)
                    .build()
            );
        }
        return outputsL;
    }
}
