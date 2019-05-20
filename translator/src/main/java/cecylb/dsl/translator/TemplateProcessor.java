package cecylb.dsl.translator;

import java.io.IOException;
import java.io.OutputStream;

public interface TemplateProcessor {

    void process(final Parser.Context context, final OutputStream output);

    class Collector {
        private final OutputStream output;

        public Collector(final OutputStream output) {
            this.output = output;
        }

        public void append(final String s) {
            try {
                output.write(s.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
