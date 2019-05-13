package translator.impl;

import data.Connection;
import data.Objects;
import data.Ports;
import generate.*;
import io.github.therealmone.tdf4j.generator.LexerGenerator;
import io.github.therealmone.tdf4j.generator.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.parser.model.ast.AST;
import translator.LexerModule;
import translator.Parser;
import translator.ParserModule;
import translator.Translator;

import java.io.*;
import java.util.Map;

public class TranslatorImpl implements Translator {
    private final LexerModule lexerModule;
    private final ParserModule parserModule;

    public TranslatorImpl(final LexerModule lexerModule, final ParserModule parserModule) {
        this.lexerModule = lexerModule;
        this.parserModule = parserModule;
    }

    @Override
    public void translate(final InputStream input, final OutputStream output) throws IOException {
        final Lexer lexer = LexerGenerator.newInstance().generate(lexerModule);
        final Parser parser = ParserGenerator.newInstance().generate(parserModule, Parser.class);
        try (final StringReader reader = new StringReader(process(lexer, parser, read(input)))) {
            int bt;
            while((bt = reader.read()) != -1) {
                output.write(bt);
            }
        }
    }

    @SuppressWarnings("ConstantConditions")
    private String process(final Lexer lexer, final Parser parser, final String input) {
        final AST ast = parser.parse(lexer.stream(input));
        final StringBuilder sb = new StringBuilder();
        final TeXfields fields = new TeXfields(); // поля, необходимые для TeX, в которых нет значений
        //GATHERING PROPERTIES
        final Properties prop = new Properties();
        sb.append(prop.teXproperty(parser.getContext().getProperties().peek()));
        sb.append(fields.teXmakeAletter());
        //GATHERING OBJECT INFORMATION
        for(Objects object: parser.getContext().getObjects()) {
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
            sb.append(fields.teXaddFont());
            sb.append(elem.teXfontSize());
        }
        sb.append(fields.teXmakeAtother());
        sb.append(fields.teXbegin());

        for(Map.Entry<String, String> inline : parser.getContext().getInline().entrySet()){
            if(inline.getKey().equals("group")){
                System.out.println("HERE?");
                sb.append(inline.getValue());
            }
        }

        for(Objects object : parser.getContext().getObjects()) {
            Elements elem = new Elements(object);
            sb.append(elem.teXamount());
            sb.append(elem.teXforEach("0"));
            sb.append(elem.teXspacing());
            sb.append(elem.teXforEach("1"));
            sb.append(fields.teXbrL());
            for (Connection connection : parser.getContext().getConnections()) {
                Connections conn = new Connections(connection);
                sb.append(conn.teXconn1());
                sb.append(conn.teXconnC());
                sb.append(conn.teXconn2());
            }
            sb.append(fields.teXbrR());
        }
        for(Objects object : parser.getContext().getObjects()){
            IO io = new IO();
            for(Ports ports : object.inputs()){
                sb.append(io.teXconnIO(object.labelN(), ports.name(), "east", -object.sizeX(), "0", "<-"));
            }
            for(Ports output : object.outputs()){
                sb.append(io.teXconnIO(object.labelN(), output.name(), "west", object.sizeX(), "\\N", "->"));
            }
        }
        sb.append(fields.teXend());
        System.out.println(sb.toString());
        return sb.toString();
    }

    private String read(final InputStream inputStream) throws IOException {
        final StringWriter writer = new StringWriter();
        int bt;
        while((bt = inputStream.read()) != -1) {
            writer.write(bt);
        }
        return writer.toString();
    }
}
