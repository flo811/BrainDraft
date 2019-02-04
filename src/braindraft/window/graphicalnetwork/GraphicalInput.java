package braindraft.window.graphicalnetwork;

import braindraft.model.network.InputNeuron;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public class GraphicalInput extends GraphicalNeuron {

    private final Text output = new Text("0");

    private final InputNeuron neuron;

    public GraphicalInput(final InputNeuron neuron) {
        super();

        getChildren().addAll(new Circle(50, Color.ANTIQUEWHITE), output);
        this.neuron = neuron;
    }

    @Override
    public void actualize() {
        output.setText(String.valueOf(neuron.getOutput()));
    }
}
