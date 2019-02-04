package braindraft.window.graphicalnetwork;

import braindraft.model.network.Network;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flo
 */
public class GraphicalNetwork {

    private final List<GraphicalInput> inputs = new ArrayList<>();
    private final List<List<GraphicalHidden>> hiddensList = new ArrayList<>();
    private final List<GraphicalOutput> outputs = new ArrayList<>();

    public GraphicalNetwork(final Network network) {
        network.getInputLayer().forEach(neuron -> inputs.add(new GraphicalInput(neuron)));
        network.getHiddenLayers().forEach(hiddenLayer -> {
            final List<GraphicalHidden> hiddens = new ArrayList<>();
            hiddenLayer.forEach(neuron -> hiddens.add(new GraphicalHidden(neuron)));
            hiddensList.add(hiddens);
        });
        network.getOutputLayer().forEach(neuron -> outputs.add(new GraphicalOutput(neuron)));
    }

    public void actualize() {
        inputs.forEach(GraphicalInput::actualize);
        hiddensList.stream()
                .flatMap(List::stream)
                .forEach(GraphicalHidden::actualize);
        outputs.forEach(GraphicalOutput::actualize);
    }

    public List<GraphicalInput> getInputs() {
        return inputs;
    }

    public List<List<GraphicalHidden>> getHiddensList() {
        return hiddensList;
    }

    public List<GraphicalOutput> getOutputs() {
        return outputs;
    }
}
