package braindraft.model.network;

import braindraft.model.MyDoubleProperty;
import java.io.Serializable;

/**
 *
 * @author flo
 */
public class InputNeuron implements Outputable, Serializable {

    private final MyDoubleProperty input = new MyDoubleProperty();

    public void setInput(final double input) {
        this.input.set(input);
    }

    @Override
    public MyDoubleProperty getOutputProperty() {
        return input;
    }

    @Override
    public double getOutput() {
        return input.get();
    }
}
