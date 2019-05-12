package translator;

import data.Connection;
import data.Objects;
import data.Property;

import java.util.LinkedList;
import java.util.Queue;

public interface Parser extends io.github.therealmone.tdf4j.parser.Parser {
    Context getContext();

    class Context {
        private final Queue<Property> properties;
        private final Queue<Objects> objects;
        private final Queue<Connection> connections;

        public Context() {
            this.properties = new LinkedList<>();
            this.objects = new LinkedList<>();
            this.connections = new LinkedList<>();
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
    }
}
