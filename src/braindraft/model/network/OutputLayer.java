package braindraft.model.network;

import java.util.List;

/**
 *
 * @author flo
 */
public class OutputLayer extends Layer<OutputNeuron> {

    public OutputLayer(final List<OutputNeuron> outputs) {
        super(outputs);
    }

    public void activate() {
        neurons.forEach(OutputNeuron::activate);
    }

    public double[] getOutputs() {
        final double[] outputs = new double[size()];
        for (int i = 0; i < neurons.size(); i++) {
            outputs[i] = neurons.get(i).getOutput();
        }
        
        return outputs;
    }
}
