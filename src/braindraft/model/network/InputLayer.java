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
}
