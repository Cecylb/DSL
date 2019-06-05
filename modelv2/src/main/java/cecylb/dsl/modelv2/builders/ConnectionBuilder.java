package cecylb.dsl.modelv2.builders;

import cecylb.dsl.modelv2.tmp.Connection;
import cecylb.dsl.modelv2.tmp.ModifiableConnection;
import io.github.therealmone.tdf4j.model.ast.ASTElement;

public class ConnectionBuilder extends AbstractConnectionBuilder {

    private ModifiableConnection builder;
    public ConnectionBuilder() {

        addRule("connection/obj1/LET", leaf -> {
            builder.setObjName1(leaf.token().value());
        });

        addRule("connection/port1/LET", leaf -> {
            builder.setPort1(leaf.token().value());
        });

        addRule("connection/port1/NUM", leaf -> {
            builder.setPort1(leaf.token().value());
        });

        addRule("conneсtion/ARW", leaf -> {
            builder.setLineType(leaf.token().value());
        });

        addRule("conneсtion/LIN", leaf -> {
            builder.setLineType("-");
        });

        addRule("conneсtion/INV", leaf -> {
            builder.setLineType(leaf.token().value());
        });

        addRule("conneсtion/DTL", leaf -> {
            builder.setLineType(leaf.token().value());
        });

        addRule("connection/obj2/LET", leaf -> {
            builder.setObjName2(leaf.token().value());
        });

        addRule("connection/port2/LET", leaf -> {
            builder.setPort2(leaf.token().value());
        });

        addRule("connection/port2/NUM", leaf -> {
            builder.setPort1(leaf.token().value());
        });

        addRule("connection/obj2/property/IDN", leaf -> {
            builder.setSelf(true);
        });

    }

    public Connection build(ASTElement tree) {
        builder = ModifiableConnection.create();
        process(tree);
        return builder;
    }
}
