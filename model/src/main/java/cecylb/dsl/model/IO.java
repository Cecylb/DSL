package cecylb.dsl.model;

import java.util.ArrayList;
import java.util.List;

public class IO {

    private final List<Ports> inputsL = new ArrayList<>();
    private final List<Ports> outputsL = new ArrayList<>();

    public List<Ports> getInputs(final String[] inputs, final double sizeX, final double sizeY) {
        for (int i = 0; i < inputs.length; i++) {
            inputsL.add(new Ports.Builder()
                    .name(inputs[i])
                    .label(inputs[i])
                    .x(-sizeX * 2)
                    .y(((sizeY * 4) / (inputs.length + 1) * (i + 1)) - sizeY * 2)
                    .position(i + 1)
                    .build()
            );
        }
        return inputsL;
    }

    public List<Ports> getOutputs(final String[] outputs, final double sizeX, final double sizeY) {
        for (int i = 0; i < outputs.length; i++) {
            outputsL.add(new Ports.Builder()
                    .name(outputs[i])
                    .label(outputs[i])
                    .x(sizeX * 2) // ВОЗМОЖНЫ ПРОБЛЕМЫ С ОТРИСОВКОЙ СОЕДИНЕНИЙ
                    .y(((sizeY * 4) / (outputs.length + 1) * (i + 1)) - sizeY * 2)
                    .position(i + 1)
                    .build()
            );
        }
        return outputsL;
    }
}
