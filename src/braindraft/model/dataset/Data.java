package braindraft.model.dataset;

import java.io.Serializable;

/**
 *
 * @author flo
 */
public class Data implements Serializable {

    private static final long serialVersionUID = 1L;

    private final double input[];
    private final double output[];

    public Data(final double[] input, final double[] output) {
        this.input = input;
        this.output = output;
    }

    public double[] getInput() {
        return input;
    }

    public double[] getOutput() {
        return output;
    }
}
