package builder.objects.elements.parameters;

import data.Ports;

import java.util.ArrayList;
import java.util.List;

public class IOD {

    List<Ports> inputsLD = new ArrayList<>();
    List<Ports> outputsLD = new ArrayList<>();

    public List<Ports> getInputsD(
            List inputs,
            int inputsN,
            double sizeX,
            double sizeY) {
        char alphabet = 'a';
        //inputsN=inputsN+inputs.size();
        for (int i = 0; i < inputsN - 1; i++) {
            inputsLD.add(new Ports.Builder()
                    .name("i" + alphabet)
                    .label(Integer.toString(i))
                    .x(-sizeX * 2)
                    .y(((sizeY * 4) / (inputsN + 1) * (i + 1)) - sizeY * 2)
                    .position(i + 1)
                    .build()
            );
            alphabet++;
        }
        for (int i = 0; i < inputs.size(); i++) {
            inputsLD.add(new Ports.Builder()
                    .name(inputs.get(i).toString())
                    .label(inputs.get(i).toString())
                    .x(-sizeX * 2)
                    .y((((sizeY * 4) / (inputsN + 1) * (inputsN + 1)) - sizeY * 2) - 0.2)
                    .position(inputsN)
                    .build()
            );
        }
        return inputsLD;
    }

    public List<Ports> getOutputsD(
            int outputsN,
            double sizeX,
            double sizeY) {
        char alphabet = 'a';
        for (int i = 0; i < outputsN; i++) {
            outputsLD.add(new Ports.Builder()
                    .name("o" + alphabet)
                    .label(Integer.toString(i))
                    .x(sizeX * 2)
                    .y(((sizeY * 4) / (outputsN + 1) * (i + 1)) - sizeY * 2)
                    .position(i + 1)
                    .build()
            );
            alphabet++;
        }
        return outputsLD;
    }
}
