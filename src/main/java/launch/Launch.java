package launch;

import data.Connection;
import data.Objects;
import data.Ports;
import data.Property;
import generate.*;
import io.github.therealmone.tdf4j.parser.model.ast.AST;
import org.junit.Test;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class Launch {

    @Test
    public void Test() {
        Grammar grammar = new Grammar();
        AST ast = grammar.getParser().parse(grammar.getLexer().stream("a4, landscape; new TTr{ size: 0.5x, 0.5y; label: 1.5x, 1.5y, \"TtR #n\"; inputs:  10; amount: 1; spacing: 4; }connections{ (TTr)Qn o- (TTr)S; }"));
        Queue<Property> property = grammar.getProperty();
        Queue<Objects> objects = grammar.getObjects();
        Queue<Connection> connections = grammar.getConnections();
        File teXcode = new File("/etc/home/cecylb/Documents/TeX/Test/Test.tex");
        StringBuilder sb = new StringBuilder();
        TeXfields fields = new TeXfields(); // поля, необходимые для TeX, в которых нет значений
        //GATHERING PROPERTIES
        Properties prop = new Properties();
        sb.append(prop.teXproperty(property.peek()));
        sb.append(fields.teXmakeAletter());
        //GATHERING OBJECT INFORMATION
        for(Objects object: objects) {
            Elements elem = new Elements(object);
            sb.append(elem.teXobjN());
            sb.append(elem.teXsize());
            sb.append(fields.teXborder());
            sb.append(elem.teXlabelC());
            sb.append(elem.teXport());
            sb.append(fields.teXbackgroundpath());
            sb.append(elem.teXportN());
            sb.append(fields.teXcloseP());
            sb.append(elem.teXportL());
            sb.append(fields.teXendG());
        }
        sb.append(fields.teXaddFont());
        sb.append(fields.teXfontSize());
        sb.append(fields.teXmakeAtother());
        sb.append(fields.teXbegin());

        for(Objects object : objects) {
            Elements elem = new Elements(object);
            sb.append(elem.teXamount());
            sb.append(elem.teXforEach());
            sb.append(elem.teXspacing());
            sb.append(elem.teXforEach());
            sb.append(fields.teXbrL());
            for (Connection connection : connections) {
                Connections conn = new Connections(connection);
                sb.append(conn.teXconn1());

                sb.append(conn.teXconn2());
            }
            sb.append(fields.teXbrR());
        }
        for(Objects object : objects){
            IO io = new IO();
            for(Ports input : object.inputs()){
                sb.append(io.teXconnIO(object.objName(), input.name(), "east", -object.sizeX(), "0", "<-"));
            }
            for(Ports output : object.outputs()){
                sb.append(io.teXconnIO(object.objName(), output.name(), "west", object.sizeX(), "\\N", "->"));
            }
        }
        sb.append(fields.teXend());
        System.out.println(sb.toString());
    }
}