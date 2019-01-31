package braindraft.model.dataset;

/**
 *
 * @author flo
 */
public class Data {

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
