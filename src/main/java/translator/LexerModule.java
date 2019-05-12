package translator;

import io.github.therealmone.tdf4j.lexer.config.AbstractLexerModule;

public class LexerModule extends AbstractLexerModule {
    @Override
    public void configure() {
        tokenize("BSL").pattern("^\\[$").priority(1); // int max
        tokenize("BSR").pattern("^\\]$").priority(1); // priority-  необзяательный параметр
        tokenize("BFL").pattern("^\\{$").priority(1); // По умолчанию приоритет = 0
        tokenize("BFR").pattern("^\\}$").priority(1);
        tokenize("BRL").pattern("^\\($").priority(1);
        tokenize("BRR").pattern("^\\)$").priority(1);
        tokenize("COL").pattern("^\\:$").priority(1);
        tokenize("QUT").pattern("^\"$").priority(1);
        //tokenize("GRD").pattern("^\\#$").priority(1);
        tokenize("DIV").pattern("^\\;$").priority(1);
        tokenize("CMA").pattern("^\\,$").priority(1);
        tokenize("DOT").pattern("^\\.$").priority(1);
        tokenize("EQL").pattern("^\\=$").priority(1);
        tokenize("COM").pattern("^\\//$").priority(1);

        tokenize("LIN").pattern("^--$").priority(1);
        tokenize("INV").pattern("^(o-)|(-o)$").priority(1);
        tokenize("ARW").pattern("^(->)|(<-)$").priority(1);
        tokenize("DTL").pattern("^(\\*-)|(-\\*)$").priority(1);

        tokenize("OBJ").pattern("^(AC|ARSTr|CTTr|DC|DTr|LDC|MX|PDC|RDC|SC|ShRSTr|SRSTr|TTr|UJKTr)$").priority(2);
        tokenize("SHS").pattern("^(a1|a2|a3|a4|a5)$").priority(2);
        tokenize("ORI").pattern("^(landscape|portrait)$").priority(2);

        tokenize("NEW").pattern("^new$").priority(2);
        tokenize("SIZ").pattern("^size$").priority(2);
        tokenize("LAB").pattern("^label$").priority(2);
        tokenize("FON").pattern("^font$").priority(2);
        tokenize("PRT").pattern("^inputs$").priority(2);
        tokenize("SPC").pattern("^spacing$").priority(2);
        tokenize("AMT").pattern("^amount").priority(2);
        tokenize("MTH").pattern("^[\\+\\-\\/\\*]$").priority(2);
        tokenize("COP").pattern("^[<>]|(<=)|(>=)|(==)|(!=)$").priority(2);
        tokenize("CON").pattern("^connections$").priority(2);
        tokenize("LET").pattern("^([a-z]+[0-1]*)|([A-Z]+[0-1]*)$").priority(1);
        tokenize("NUM").pattern("^(0|([1-9][0-9]*))$").priority(2);
        tokenize("DBL").pattern("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$").priority(1);
        tokenize("SCL").pattern("^(small|medium|large)$").priority(2);
        tokenize("POS").pattern("^(top|middle|bottom)$").priority(2);
        tokenize("CRD").pattern("^(x|y)$").priority(2);
        tokenize("CUR").pattern("^#n$").priority(2);
        tokenize("IDN").pattern("^id$").priority(2);

        tokenize("single_line_comment").pattern("//.*(\n|\r|\r\n|\n\r)").hidden(true);
        tokenize("multi_line_comment").pattern("/\\*[^(\\*/)]*\\*/").hidden(true);

        tokenize("WS").pattern("\\s|\\n|\\r").priority(Integer.MAX_VALUE).hidden(true);
    }
}
