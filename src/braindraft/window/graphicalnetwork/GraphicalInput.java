package braindraft.window.graphicalnetwork;

import braindraft.model.network.InputNeuron;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 *
 * @author flo
 */
public class GraphicalInput extends GraphicalComponent {

    private final InputNeuron neuron;

    public GraphicalInput(final InputNeuron neuron) {
        super(new Circle(50, Color.ANTIQUEWHITE));

        this.neuron = neuron;
        actualize();
    }

    @Override
    public void actualize() {
        output.setText(formatValue(neuron.getOutput()));
    }
}
