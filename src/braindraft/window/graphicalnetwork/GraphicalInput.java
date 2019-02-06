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

    public GraphicalInput(final InputNeuron input) {
        super(input.getName(), new Circle(50, Color.ANTIQUEWHITE));

        this.neuron = input;
        actualize();

        shape.setOnMouseClicked(e -> {
            new ComponentParametersWindow(input).showAndWait();
            actualize();
        });
    }

    @Override
    public void actualize() {
        output.setText(formatValue(neuron.getOutput()));
    }
}
