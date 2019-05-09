package elements;

import javax.swing.text.Element;
import java.util.List;

public interface TTr extends Element {

    List<String> inputs = List.of("S", "T", "C", "R");
    List<String> outputs = List.of("Q", "Qn");
}
