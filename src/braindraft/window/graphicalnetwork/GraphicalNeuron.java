package braindraft.window.graphicalnetwork;

import javafx.scene.layout.StackPane;

/**
 *
 * @author flo
 */
public abstract class GraphicalNeuron extends StackPane {

    public GraphicalNeuron() {
        super();
    }

    public abstract void actualize();
}
