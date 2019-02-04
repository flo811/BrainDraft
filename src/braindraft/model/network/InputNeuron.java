package braindraft.model.network;

import java.io.Serializable;

/**
 *
 * @author flo
 */
public class InputNeuron implements Outputable, Serializable {

    private double input;

    public void setInput(final double input) {
        this.input = input;
    }

    @Override
    public double getOutput() {
        return input;
    }
}
