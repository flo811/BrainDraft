package braindraft.window.graphicalnetwork;

import braindraft.model.network.OutputNeuron;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author flo
 */
public class GraphicalOutput extends GraphicalNeuron {

    public GraphicalOutput(final OutputNeuron neuron) {
        super(new Rectangle(100, 100, Color.LIGHTSTEELBLUE), neuron);

        final Rectangle rectangle = (Rectangle) shape;
        rectangle.setArcWidth(80);
        rectangle.setArcHeight(80);
    }
}
