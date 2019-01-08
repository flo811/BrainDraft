package braindraft.model.network;

import braindraft.dao.NetworkProxy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author flo
 */
public class Network implements Serializable {

    private final InputLayer inputLayer;
    private final List<HiddenLayer> hiddenLayers;
    private final OutputLayer outputLayer;

    public Network(final InputLayer inputLayer, final List<HiddenLayer> hiddenLayers, final OutputLayer outputLayer) {
        this.inputLayer = inputLayer;
        this.hiddenLayers = hiddenLayers;
        this.outputLayer = outputLayer;
    }

    public InputLayer getInputLayer() {
        return inputLayer;
    }

    public List<HiddenLayer> getHiddenLayers() {
        return new ArrayList<>(hiddenLayers);
    }

    public OutputLayer getOutputLayer() {
        return outputLayer;
    }

    public InputNeuron getInput(final int i) {
        return inputLayer.get(i);
    }

    public HiddenNeuron getHidden(final int layer, final int i) {
        return hiddenLayers.get(layer).get(i);
    }

    public OutputNeuron getOutput(final int i) {
        return outputLayer.get(i);
    }

    private NetworkProxy writeReplace() {
        return new NetworkProxy();
    }
}
