package braindraft.model;

import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author flo
 */
public interface ActivationFunction {

    DoubleUnaryOperator getFunction();

    DoubleUnaryOperator getFunctionDerivate();

    default double apply(final double value) {
        return getFunction().applyAsDouble(value);
    }

    default double applyDerivate(final double value) {
        return getFunctionDerivate().applyAsDouble(value);
    }
}
