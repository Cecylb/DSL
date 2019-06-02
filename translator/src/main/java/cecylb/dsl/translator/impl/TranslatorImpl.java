package cecylb.dsl.translator.impl;

import cecylb.dsl.translator.*;
import io.github.therealmone.tdf4j.generator.impl.LexerGenerator;
import io.github.therealmone.tdf4j.generator.impl.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;
import io.github.therealmone.tdf4j.tdfparser.TdfParser;
import io.github.therealmone.tdf4j.tdfparser.TdfParserGenerator;

import java.io.*;

public class TranslatorImpl implements Translator {
    private final Lexer lexer;
    private final Parser parser;
    private final TemplateProcessor templateProcessor;

    public TranslatorImpl(
            final InputStream grammar,
            final TemplateProcessor templateProcessor
    ) throws IOException {
        final TdfParser tdfParser = new TdfParserGenerator(grammar).generate();
        this.lexer = new LexerGenerator(tdfParser.getLexerModule()).generate();
        this.parser = new ParserGenerator(tdfParser.getParserModule()).generate(Parser.class);
        this.templateProcessor = templateProcessor;
    }

    @Override
    public void translate(final InputStream input, final OutputStream output) throws IOException {
        parser.parse(lexer.stream(read(input)));
        templateProcessor.process(parser.getContext(), output);
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
