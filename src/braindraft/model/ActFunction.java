package braindraft.model;

import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author flo
 */
public enum ActFunction {
    HEAVISIDE("Heaviside", x -> x < 0 ? 0 : 1, x -> 0),
    LOGISTIC("Logistic", x -> 1 / (1 + Math.exp(-x)), x -> Math.exp(x) / ((1 + Math.pow(x, x)) * (1 + Math.pow(x, x)))),
    TANH("Tanh", x -> Math.tanh(x), x -> 1 - Math.tanh(x) * Math.tanh(x)),
    RELU("ReLu", x -> x > 0 ? x : 0, x -> x > 0 ? 1 : 0);

    private final ActivationFunction activationFunction;

    private ActFunction(final String name, final DoubleUnaryOperator function,
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

            @Override
            public String toString() {
                return name;
            }
        };
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }
}
