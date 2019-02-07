package braindraft.model;

import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author flo
 */
public enum ActivationFunctions {

    LOGISTIC(x -> 1 / (1 + Math.exp(-x)), x -> Math.exp(x) / ((1 + Math.exp(x)) * (1 + Math.exp(x)))),
    TANH(x -> Math.tanh(x), x -> 1 - Math.tanh(x) * Math.tanh(x)),
    HEAVISIDE(x -> x < 0 ? 0 : 1, x -> 0),
    RELU(x -> x > 0 ? x : x * 0.1, x -> x > 0 ? 1 : 0.1),
    SOFTPLUS(x -> Math.log1p(x), x -> 1 / (1 + Math.exp(-x)));

    private final ActivationFunction activationFunction;

    private ActivationFunctions(final DoubleUnaryOperator function,
            final DoubleUnaryOperator derivate) {
        activationFunction = new ActivationFunction() {
            @Override
            public DoubleUnaryOperator getFunction() {
                return function;
            }

            @Override
            public DoubleUnaryOperator getFunctionDerivate() {
                return derivate;
            }
        };
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
}
