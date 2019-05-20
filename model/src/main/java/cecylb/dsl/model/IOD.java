package cecylb.dsl.model;


import java.util.ArrayList;
import java.util.List;

public class IOD {

    private final List<Ports> inputsL = new ArrayList<>();
    private final List<Ports> outputsL = new ArrayList<>();

    public List<Ports> getInputsD(final String[] inputs, final int inputsN, final double sizeX, final double sizeY) {
        char alphabet = 'a';
        //inputsN=inputsN+inputs.size();
        for (int i = 0; i < inputsN - 1; i++) {
            inputsL.add(new Ports.Builder()
                    .name("i" + alphabet)
                    .label(Integer.toString(i))
                    .x(-sizeX * 2)
                    .y(((sizeY * 4) / (inputsN + 1) * (i + 1)) - sizeY * 2)
                    .position(i + 1)
                    .build()
            );
            alphabet++;
        }
        for (Object input : inputs) {
            inputsL.add(new Ports.Builder()
                    .name(input.toString())
                    .label(input.toString())
                    .x(-sizeX * 2)
                    .y((((sizeY * 4) / (inputsN + 1) * (inputsN + 1)) - sizeY * 2) - 0.2)
                    .position(inputsN)
                    .build()
            );
        }
        return inputsL;
    }

    public List<Ports> getOutputsD(final int outputsN, final double sizeX, final double sizeY) {
        char alphabet = 'a';
        for (int i = 0; i < outputsN; i++) {
            outputsL.add(new Ports.Builder()
                    .name("o" + alphabet)
                    .label(Integer.toString(i))
                    .x(sizeX * 2)
                    .y(((sizeY * 4) / (outputsN + 1) * (i + 1)) - sizeY * 2)
                    .position(i + 1)
                    .build()
            );
            alphabet++;
        }
        return outputsL;
    }
}
