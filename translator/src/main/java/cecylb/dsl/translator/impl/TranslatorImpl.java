package cecylb.dsl.translator.impl;

import cecylb.dsl.translator.*;
import io.github.therealmone.tdf4j.generator.LexerGenerator;
import io.github.therealmone.tdf4j.generator.ParserGenerator;
import io.github.therealmone.tdf4j.lexer.Lexer;

import java.io.*;

public class TranslatorImpl implements Translator {
    private final LexerModule lexerModule;
    private final ParserModule parserModule;
    private final TemplateProcessor templateProcessor;

    public TranslatorImpl(
            final LexerModule lexerModule,
            final ParserModule parserModule,
            final TemplateProcessor templateProcessor
    ) {
        this.lexerModule = lexerModule;
        this.parserModule = parserModule;
        this.templateProcessor = templateProcessor;
    }

    @Override
    public void translate(final InputStream input, final OutputStream output) throws IOException {
        final Lexer lexer = LexerGenerator.newInstance().generate(lexerModule);
        final Parser parser = ParserGenerator.newInstance().generate(parserModule, Parser.class);
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
