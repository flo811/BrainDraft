package braindraft.model;

import java.io.Serializable;

/**
 *
 * @author flo
 */
public class ValidationRule implements Serializable {

    private static final long serialVersionUID = -7189516499924304847L;

    public Boolean apply(double[] actual, double[] expected) {
        return Math.abs(actual[0] - expected[0]) < 0.1;
    }
}
