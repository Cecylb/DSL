package cecylb.dsl.modelv2.builders;

import cecylb.dsl.modelv2.tmp.*;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

public class MXBuilder  extends AbstractBuilder {

    private ModifiableMX builder;
    public MXBuilder() {
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

        addRule("object/label/coordinates/x_coordinate/DBL", leaf -> {
            builder.setLabelX(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/label/coordinates/y_coordinate/DBL", leaf -> {
            builder.setLabelY(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/amount/NUM", leaf -> {
            builder.setAmount(Integer.parseInt(leaf.token().value()));
        });

        addRule("object/spacing/NUM", leaf -> {
            builder.setSpacing(Integer.parseInt(leaf.token().value()));
        });

        addRule("object/inputs/NUM", leaf -> {
            builder.setInput(Integer.parseInt(leaf.token().value()));
        });

        addRule("object/BFR", leaf -> {
            Character alphabet = 'a';
            for (int i = 0; i < builder.input(); i++) {
                builder.inputs().add(new Port.Builder()
                        .portX(-builder.sizeX() * 2)
                        .portY(-((builder.sizeY() * 4) / (builder.input() + 2) * (i + 1)) + builder.sizeY() * 2)
                        .portName("i" + alphabet.toString())
                        .portLabel(String.valueOf(i))
                        .build());
                alphabet++;
            }
            builder.inputs().add(new Port.Builder()
                    .portX(-builder.sizeX() * 2)
                    .portY((builder.sizeY() * 4) / (builder.input() + 1) - builder.sizeY() * 2)
                    .portName(builder.INPUTS[0])
                    .portLabel(builder.INPUTS[0])
                    .build());
            builder.setOutput((int)Math.pow(2, builder.input()+1));
            alphabet = 'a';
            for (int i = 0; i < builder.output(); i++) {
                builder.outputs().add(new Port.Builder()
                        .portX(builder.sizeX() * 2)
                        .portY(((builder.sizeY() * 4) / (builder.output() + 1) * (i + 1)) - builder.sizeY() * 2)
                        .portName("o" + alphabet.toString())
                        .portLabel(String.valueOf(i))
                        .build());
                alphabet++;
            }
            for (MX.Rectangles rectangle : MX.Rectangles.values()) {
                builder.rectangles().add(new Rectangle.Builder()
                        .swX(rectangle.getSwX() * builder.sizeX() * 2)
                        .swY(rectangle.getSwY() * builder.sizeY() * builder.output() / 4)
                        .neX(rectangle.getNeX() * builder.sizeX() * 2)
                        .neY(rectangle.getNeY() * builder.sizeY() * builder.output() / 4)
                        .build());
            }
        });
    }

    @Override
    public TexObject build(ASTElement tree) {
        builder = ModifiableMX.create();
        process(tree);
        return builder;
    }
}
