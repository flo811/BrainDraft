package braindraft.window.graphicalnetwork;

import braindraft.model.network.Outputable;
import braindraft.model.network.VirtualNeuron;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public class GraphicalNeuron extends GraphicalComponent {

    private final Map<Outputable, Text> weightsMap = new HashMap<>();
    private final Text biasWeightTxt = new Text();

    private final VirtualNeuron neuron;

    public GraphicalNeuron(final Shape shape, final VirtualNeuron neuron) {
        super(shape);
        this.neuron = neuron;

        neuron.getWeights().forEach(entry -> weightsMap.put(entry.getKey(), new Text()));
        weightsMap.values().forEach(vBox.getChildren()::add);
        vBox.getChildren().add(biasWeightTxt);

        weightsMap.values().forEach(txt -> txt.setFill(Color.SADDLEBROWN));
        biasWeightTxt.setFill(Color.DARKVIOLET);

        actualize();
    }

    @Override
    public void actualize() {
        output.setText(formatValue(neuron.getOutput()));
        biasWeightTxt.setText(formatValue(neuron.getBiasWeight()));
        weightsMap.forEach((nrn, txt) -> txt.setText(formatValue(neuron.getWeightWith(nrn))));
    }
}
