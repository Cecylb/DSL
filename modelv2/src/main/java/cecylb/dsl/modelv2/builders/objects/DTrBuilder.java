package cecylb.dsl.modelv2.builders.objects;

import cecylb.dsl.modelv2.tmp.*;
import cecylb.dsl.modelv2.tmp.DTr;
import cecylb.dsl.modelv2.tmp.TexObject;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

public class DTrBuilder extends AbstractObjectBuilder {

    private ModifiableDTr builder;
    public DTrBuilder() {

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
            for(DTr.Rectangles rectangle : DTr.Rectangles.values()) {
                builder.rectangles().add(new Rectangle.Builder()
                        .swX(rectangle.getSwX()*builder.sizeX()*2)
                        .swY(rectangle.getSwY()*builder.sizeY()*2)
                        .neX(rectangle.getNeX()*builder.sizeX()*2)
                        .neY(rectangle.getNeY()*builder.sizeY()*2)
                        .build());
            }
            for(int i=0; i<DTr.INPUTS.length; i++){
                builder.inputs().add(new Port.Builder()
                        .portX(-builder.sizeX()* 2)
                        .portY(-((builder.sizeY() * 4) / (DTr.INPUTS.length + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName(DTr.INPUTS[i])
                        .portLabel(DTr.INPUTS[i])
                        .build());
            }
            for(int i=0; i<DTr.OUTPUTS.length; i++){
                builder.outputs().add(new Port.Builder()
                        .portX(builder.sizeX()*2)
                        .portY(-((builder.sizeY() * 4) / (DTr.OUTPUTS.length + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName(DTr.OUTPUTS[i])
                        .portLabel(DTr.OUTPUTS[i])
                        .build());
            }
        });
    }

    @Override
    public TexObject build(ASTElement tree) {
        builder = ModifiableDTr.create();
        process(tree);
        return builder;
    }
}
