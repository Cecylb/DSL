package cecylb.dsl.modelv2.builders.objects;

import cecylb.dsl.modelv2.elements.*;
import cecylb.dsl.modelv2.elements.AND;
import cecylb.dsl.modelv2.elements.TexObject;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

import java.util.Map;

public class ANDBuilder extends AbstractObjectBuilder {

    private ModifiableAND builder;
    public ANDBuilder() {

        addRule("object/position/coordinates/x_coordinate/DBL", leaf -> {
            builder.setPosX(Double.parseDouble(leaf.token().value()));
        });

        addRule("object/position/coordinates/y_coordinate/DBL", leaf -> {
            builder.setPosY(Double.parseDouble(leaf.token().value()) - 15.0);
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

        addRule("object/label/labelname/LET", leaf -> {
            builder.setLabelName(leaf.token().value());
        });

        addRule("object/label/labelname/OBJ", leaf -> {
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

        addRule("object/BFR", leaf -> {
            for(AND.Rectangles rectangle : AND.Rectangles.values()) {
                builder.rectangles().add(new Rectangle.Builder()
                        .swX(rectangle.getSwX()*builder.sizeX()*2)
                        .swY(rectangle.getSwY()*builder.sizeY()*2)
                        .neX(rectangle.getNeX()*builder.sizeX()*2)
                        .neY(rectangle.getNeY()*builder.sizeY()*2)
                        .build());
            }
            int i = 0;
            for(Map.Entry<String, String> entry : AND.inputs.entrySet()){
                builder.inputs().add(new Port.Builder()
                        .portX(-builder.sizeX()* 2)
                        .portY(-((builder.sizeY() * 4) / (AND.inputs.size() + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName(entry.getKey())
                        .portLabel(entry.getKey())
                        .portLine(entry.getValue())
                        .build());
                i++;
            }
            i=0;
            for(Map.Entry<String, String> entry : AND.outputs.entrySet()){
                builder.outputs().add(new Port.Builder()
                        .portX(builder.sizeX()*2)
                        .portY(-((builder.sizeY() * 4) / (AND.outputs.size() + 1) * (i + 1)) + builder.sizeY() * 2)
                        .portName(entry.getKey())
                        .portLabel("ab ")
                        .portLine(entry.getValue())
                        .build());
                i++;
            }
        });
    }

    @Override
    public TexObject build(ASTElement tree) {
        builder = ModifiableAND.create();
        process(tree);
        return builder;
    }
}

