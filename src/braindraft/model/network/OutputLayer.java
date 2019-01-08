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
}
