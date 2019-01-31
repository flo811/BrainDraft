package braindraft.model.network;

import java.util.List;

/**
 *
 * @author flo
 */
public class InputLayer extends Layer<InputNeuron> {

    public InputLayer(final List<InputNeuron> inputs) {
        super(inputs);
    }

    public void setInputValues(final double[] values) {
        for (int i = 0; i < values.length; i++) {
            neurons.get(i).setInput(values[i]);
        }
    }
}
