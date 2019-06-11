package cecylb.dsl.modelv2.builders.objects;

import cecylb.dsl.modelv2.tmp.*;
import cecylb.dsl.modelv2.tmp.EXOR;
import cecylb.dsl.modelv2.tmp.TexObject;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

public class EXORBuilder extends AbstractObjectBuilder {

    private ModifiableEXOR builder;
    public EXORBuilder() {

        addRule("object/position/coordinates/x_coordinate/DBL", leaf -> {
            builder.setPosX(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/position/coordinates/y_coordinate/DBL", leaf -> {
            builder.setPosY(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/size/SCL", leaf -> {
            switch(leaf.token().value()) {
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
            builder.setAmount(Integer.parseInt(leaf.token().value()));
        });

        addRule("object/spacing/NUM", leaf -> {
            builder.setSpacing(Integer.parseInt(leaf.token().value()));
        });

        addRule("object/BFR", leaf -> {
            for(EXOR.Rectangles rectangle : EXOR.Rectangles.values()) {
                builder.rectangles().add(new Rectangle.Builder()
                        .swX(rectangle.getSwX()*builder.sizeX()*2)
                        .swY(rectangle.getSwY()*builder.sizeY()*2)
                        .neX(rectangle.getNeX()*builder.sizeX()*2)
                        .neY(rectangle.getNeY()*builder.sizeY()*2)
                        .build());
            }
            for(int i=0; i<EXOR.INPUTS.length; i++){
                builder.inputs().add(new Port.Builder()
                        .portX(-builder.sizeX()* 2)
                        .portY(-((builder.sizeY() * 4) / (EXOR.INPUTS.length + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName(EXOR.INPUTS[i])
                        .portLabel(EXOR.INPUTS[i])
                        .build());
            }
            for(int i=0; i<EXOR.OUTPUTS.length; i++){
                builder.outputs().add(new Port.Builder()
                        .portX(builder.sizeX()*2)
                        .portY(-((builder.sizeY() * 4) / (EXOR.OUTPUTS.length + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName(EXOR.OUTPUTS[i])
                        .portLabel(EXOR.OUTPUTS[i] + " ")
                        .build());
            }
        });
    }

    @Override
    public TexObject build(ASTElement tree) {
        builder = ModifiableEXOR.create();
        process(tree);
        return builder;
    }
}

