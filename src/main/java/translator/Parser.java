package translator;

import data.Connection;
import data.Objects;
import data.Property;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

public interface Parser extends io.github.therealmone.tdf4j.parser.Parser {
    Context getContext();

    class Context {
        private final Queue<Property> properties;
        private final Queue<Objects> objects;
        private final Queue<Connection> connections;
        private final Map<String, String> inline;

        public Context() {
            this.properties = new LinkedList<>();
            this.objects = new LinkedList<>();
            this.connections = new LinkedList<>();
            this.inline = new TreeMap<>();
        }

        public Queue<Property> getProperties() {
            return properties;
        }

        public Queue<Objects> getObjects() {
            return objects;
        }

        public Queue<Connection> getConnections() {
            return connections;
        }

        public Map<String, String> getInline() { return inline; }
    }
}
