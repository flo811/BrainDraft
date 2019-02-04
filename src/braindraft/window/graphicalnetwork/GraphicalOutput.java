package braindraft.window.graphicalnetwork;

import braindraft.model.network.OutputNeuron;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public class GraphicalOutput extends GraphicalNeuron {

    protected final Text output = new Text("0");

    private final OutputNeuron neuron;

    public GraphicalOutput(final OutputNeuron neuron) {
        super();

        final Rectangle rectangle = new Rectangle(100, 100, Color.LIGHTSTEELBLUE);
        rectangle.setArcWidth(80);
        rectangle.setArcHeight(80);
        getChildren().addAll(rectangle, output);

        this.neuron = neuron;
    }

    @Override
    public void actualize() {
        output.setText(String.valueOf(neuron.getOutput()));
    }
}
