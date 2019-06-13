package cecylb.dsl.modelv2.builders.objects;

import cecylb.dsl.modelv2.tmp.*;
import cecylb.dsl.modelv2.tmp.DC;
import cecylb.dsl.modelv2.tmp.TexObject;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

import java.util.Map;

public class DCBuilder extends AbstractObjectBuilder {

    private ModifiableDC builder;
    public DCBuilder() {

        addRule("object/position/coordinates/x_coordinate/DBL", leaf -> {
            builder.setPosX(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/position/coordinates/y_coordinate/DBL", leaf -> {
            builder.setPosY(Double.parseDouble(leaf.token().value()) - 400.0);
        });

        addRule("object/size/SCL", leaf -> {
            switch (leaf.token().value()) {
                case "small":
                    builder.setSizeX(Sizes.SMALL.getSizeX());
                    builder.setSizeY(Sizes.SMALL.getSizeY());
                    break;
                case "medium":
                    builder.setSizeX(Sizes.MEDIUM.getSizeX());
                    builder.setSizeY(Sizes.MEDIUM.getSizeY());
                    break;
                case "large":
                    builder.setSizeX(Sizes.LARGE.getSizeX());
                    builder.setSizeY(Sizes.LARGE.getSizeY());
                    break;
            }
        });

        addRule("object/size/coordinates/x_coordinate/DBL", leaf -> {
            builder.setSizeX(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/size/coordinates/y_coordinate/DBL", leaf -> {
            builder.setSizeY(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/label/LET", leaf -> {
            builder.setLabelName(leaf.token().value());
        });

        addRule("object/label/OBJ", leaf -> {
            builder.setLabelName(leaf.token().value());
        });

        addRule("object/label/coordinates/x_coordinate/DBL", leaf -> {
            builder.setLabelX(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/label/coordinates/y_coordinate/DBL", leaf -> {
            builder.setLabelY(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/amount/NUM", leaf -> {
            builder.setAmount(Integer.parseInt(leaf.token().value())-1);
        });

        addRule("object/spacing/NUM", leaf -> {
            builder.setSpacing(Integer.parseInt(leaf.token().value()));
        });

        addRule("object/inputs/NUM", leaf -> {
            builder.setInput(Integer.parseInt(leaf.token().value()));
        });

        addRule("object/BFR", leaf -> {
            builder.setObjName("DC"); // crutch

            Character alphabet = 'a';
            for (int i = 0; i < builder.input(); i++) {
                builder.inputs().add(new Port.Builder()
                        .portX(-builder.sizeX() * 2)
                        .portY(-((builder.sizeY() * 4) / (builder.input() + 2) * (i + 1)) + builder.sizeY() * 2)
                        .portName("i" + alphabet.toString())
                        .portLabel(String.valueOf(i))
                        .portLine("")
                        .build());
                alphabet++;
            }
            int i = 0;
            for(Map.Entry<String, String> entry : DC.inputs.entrySet()){
                builder.inputs().add(new Port.Builder()
                        .portX(-builder.sizeX()* 2)
                        .portY(-((builder.sizeY() * 4) / (DC.inputs.size() + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName(entry.getKey())
                        .portLabel(entry.getKey())
                        .portLine(entry.getValue())
                        .build());
                i++;
            }

            builder.setOutput((int)Math.pow(2, builder.input()+1));
            alphabet = 'a';
            for (i = 0; i < builder.output(); i++) {
                builder.outputs().add(new Port.Builder()
                        .portX(builder.sizeX() * 2)
                        .portY(-((builder.sizeY() * 4) / (builder.output() + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName("o" + alphabet.toString())
                        .portLabel(i + "  ")
                        .portLine("")
                        .build());
                alphabet++;
            }
            for (DC.Rectangles rectangle : DC.Rectangles.values()) {
                builder.rectangles().add(new Rectangle.Builder()
                        .swX(rectangle.getSwX() * builder.sizeX() * 2)
                        .swY(rectangle.getSwY() * builder.sizeY() * builder.output() / 8)
                        .neX(rectangle.getNeX() * builder.sizeX() * 2)
                        .neY(rectangle.getNeY() * builder.sizeY() * builder.output() / 8)
                        .build());
            }
        });
    }

    @Override
    public TexObject build(ASTElement tree) {
        builder = ModifiableDC.create();
        process(tree);
        return builder;
    }
}
