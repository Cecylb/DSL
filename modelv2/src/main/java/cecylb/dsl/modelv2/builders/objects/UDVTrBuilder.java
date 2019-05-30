package cecylb.dsl.modelv2.builders.objects;

import cecylb.dsl.modelv2.builders.objects.AbstractObjectBuilder;
import cecylb.dsl.modelv2.tmp.*;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

public class UDVTrBuilder extends AbstractObjectBuilder {

    private ModifiableUDVTr builder;
    public UDVTrBuilder() {

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
            for(UDVTr.Rectangles rectangle : UDVTr.Rectangles.values()) {
                builder.rectangles().add(new Rectangle.Builder()
                        .swX(rectangle.getSwX()*builder.sizeX()*2)
                        .swY(rectangle.getSwY()*builder.sizeY()*2)
                        .neX(rectangle.getNeX()*builder.sizeX()*2)
                        .neY(rectangle.getNeY()*builder.sizeY()*2)
                        .build());
            }
            for(int i = 0; i< UDVTr.INPUTS.length; i++){
                builder.inputs().add(new Port.Builder()
                        .portX(-builder.sizeX()* 2)
                        .portY(((builder.sizeY() * 4) / (UDVTr.INPUTS.length + 1) * (i + 1)) - builder.sizeY() * 2)
                        .portName(UDVTr.INPUTS[i])
                        .portLabel(UDVTr.INPUTS[i])
                        .build());
            }
            for(int i = 0; i< UDVTr.OUTPUTS.length; i++){
                builder.outputs().add(new Port.Builder()
                        .portX(builder.sizeX()*2)
                        .portY(((builder.sizeY() * 4) / (UDVTr.OUTPUTS.length + 1) * (i + 1)) - builder.sizeY() * 2)
                        .portName(UDVTr.OUTPUTS[i])
                        .portLabel(UDVTr.OUTPUTS[i])
                        .build());
            }
        });
    }

    @Override
    public TexObject build(ASTElement tree) {
        builder = ModifiableUDVTr.create();
        process(tree);
        return builder;
    }
}
