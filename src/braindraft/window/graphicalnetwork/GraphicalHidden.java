package braindraft.window.graphicalnetwork;

import braindraft.model.network.HiddenNeuron;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author flo
 */
public class GraphicalHidden extends GraphicalNeuron {

    public GraphicalHidden(final HiddenNeuron neuron) {
        super(new Rectangle(100, 100, Color.LIGHTGREEN), neuron);

        final Rectangle rectangle = (Rectangle) getChildren().get(0);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
    }
}
