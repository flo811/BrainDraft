package braindraft.model.network;

import java.io.Serializable;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author flo
 */
public class InputNeuron implements Outputable, Serializable {

    private final SimpleDoubleProperty input = new SimpleDoubleProperty();

    public void setInput(final double input) {
        this.input.set(input);
    }

    @Override
    public SimpleDoubleProperty getOutputProperty() {
        return input;
    }

    @Override
    public double getOutput() {
        return input.get();
    }
}
