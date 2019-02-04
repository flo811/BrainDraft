package braindraft.window.graphicalnetwork;

import braindraft.model.network.HiddenNeuron;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public class GraphicalHidden extends GraphicalNeuron {

    private final Text output = new Text("0");

    private final HiddenNeuron neuron;

    public GraphicalHidden(final HiddenNeuron neuron) {
        super();

        final Rectangle rectangle = new Rectangle(100, 100, Color.LIGHTGREEN);
        rectangle.setArcWidth(30);
        rectangle.setArcHeight(30);
        getChildren().addAll(rectangle, output);

        this.neuron = neuron;
    }

    @Override
    public void actualize() {
        output.setText(String.valueOf(neuron.getOutput()));
    }
}
