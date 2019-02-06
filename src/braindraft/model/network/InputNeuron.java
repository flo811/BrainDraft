package braindraft.model.network;

import java.io.Serializable;

/**
 *
 * @author flo
 */
public class InputNeuron implements Outputable, Serializable {

    private final String name;

    private double input;

    public InputNeuron(final String name) {
        this.name = name;
    }

    public void setInput(final double input) {
        this.input = input;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getOutput() {
        return input;
    }

    @Override
    public void setOutput(final double value) {
        input = value;
    }
}
