package translator;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Translator {
    void translate(final InputStream input, final OutputStream output) throws IOException;
}
