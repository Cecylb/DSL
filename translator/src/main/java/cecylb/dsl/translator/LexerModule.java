package cecylb.dsl.translator;

import io.github.therealmone.tdf4j.module.lexer.AbstractLexerModule;

public class LexerModule extends AbstractLexerModule {
    @Override
    public void configure() {
        tokenize("BSL").pattern("^\\[$").priority(1);   // Bracket square left
        tokenize("BSR").pattern("^\\]$").priority(1);   // Bracket square right
        tokenize("BFL").pattern("^\\{$").priority(1);   // Bracket figure left
        tokenize("BFR").pattern("^\\}$").priority(1);   // Bracket figure right
        tokenize("BRL").pattern("^\\($").priority(1);   // Bracket round left
        tokenize("BRR").pattern("^\\)$").priority(1);   // Bracket round right
        tokenize("COL").pattern("^\\:$").priority(1);   // Colon
        tokenize("QUT").pattern("^\"$").priority(1);    // Quotes
        //tokenize("GRD").pattern("^\\#$").priority(1);     // Grid
        tokenize("DIV").pattern("^\\;$").priority(1);   // Division
        tokenize("CMA").pattern("^\\,$").priority(1);   // Comma
        tokenize("DOT").pattern("^\\.$").priority(1);   // Dot
        tokenize("EQL").pattern("^\\=$").priority(1);   // Equals
        //tokenize("COM").pattern("^\\//$").priority(1);    // Comment
        tokenize("LIN").pattern("^--$").priority(1);    // Line
        tokenize("INV").pattern("^(o-)|(-o)$").priority(1); // Inversion line
        tokenize("ARW").pattern("^(->)|(<-)$").priority(1); // Arrow
        tokenize("DTL").pattern("^(\\*-)|(-\\*)$").priority(1); // Dot line
        tokenize("OBJ").pattern("^(AC|UDVTr|ARSTr|CTTr|DC|DTr|LDC|MX|PDC|RDC|SC|ShRSTr|SRSTr|TTr|UJKTr)$").priority(2); // Element
        tokenize("SHS").pattern("^(a1|a2|a3|a4|a5)$").priority(2); // Sheet size
        tokenize("ORI").pattern("^(landscape|portrait)$").priority(2); // Orientation
        tokenize("NEW").pattern("^new$").priority(2); // New element
        tokenize("POS").pattern("^position$").priority(2); // Position KW
        tokenize("SIZ").pattern("^size$").priority(2); // Size KW
        tokenize("LAB").pattern("^label$").priority(2); // Label KW
        //tokenize("FON").pattern("^font$").priority(2); // Fonts KW
        tokenize("PRT").pattern("^inputs$").priority(2); // Ports KW
        tokenize("SPC").pattern("^spacing$").priority(2); // Spacing KW
        tokenize("AMT").pattern("^amount").priority(2); // Amount KW
        tokenize("MTH").pattern("^[\\+\\-\\/\\*]$").priority(2); // Math operations
        tokenize("COP").pattern("^[<>]|(<=)|(>=)|(==)|(!=)$").priority(2); // Comparison
        tokenize("CON").pattern("^connections$").priority(2); // Connections KW
        tokenize("COD").pattern("^code$").priority(3); // Code block KW
        tokenize("LET").pattern("^([a-z]+[0-1]*)|([A-Z]+[0-1]*)$").priority(1); // Letters
        tokenize("NUM").pattern("^(0|([1-9][0-9]*))$").priority(2); // Numbers
        tokenize("DBL").pattern("^(-?)(0|([1-9][0-9]*))(\\.[0-9]+)?$").priority(1); // Double numbers
        tokenize("SCL").pattern("^(small|medium|large)$").priority(2); // Scale options
        //tokenize("POS").pattern("^(top|middle|bottom)$").priority(2); // Position options (unused)
        tokenize("CDX").pattern("^x$").priority(2); // Coordinates
        tokenize("CDY").pattern("^y$").priority(2); // Coordinates
        tokenize("CUR").pattern("^#n$").priority(2); // Property
        //tokenize("IDN").pattern("^id$").priority(2); //
        //tokenize("single_line_comment").pattern("//.*(\n|\r|\r\n|\n\r)").hidden(true);
        tokenize("SLA").pattern("^\\\\$").priority(2); // Backslash
        tokenize("multi_line_comment").pattern("/\\*[^(\\*/)]*\\*/").hidden(true); // Ignore comments
        tokenize("WS").pattern("\\s|\\n|\\r").priority(Integer.MAX_VALUE).hidden(true); // Ignore spaces
        tokenize("DLR").pattern("^\\$$").priority(2);
        tokenize("KOD").pattern("^(documentclass|declareshape|tikzpicture)$").priority(2); // For the inline TeX code
        tokenize("IOL").pattern("^\'*$").priority(1);
        tokenize("QOT").pattern("^\\'$").priority(2);
    }
}
