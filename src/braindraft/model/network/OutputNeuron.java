package braindraft.model.network;

import braindraft.model.ActivationFunctions;

/**
 *
 * @author flo
 */
public class OutputNeuron extends VirtualNeuron {

    private double expected;

    public OutputNeuron(final String name, final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunctions activationFunction, final double learningRate,
            final double bias, final Layer<? extends Outputable>... previousLayer) {
        super(name, weightRangeStartMin, weightRangeStartMax, activationFunction, learningRate, bias, previousLayer);
    }

    @Override
    public void calculateDelta() {
        delta = (expected - output) * activationFunction.getActivationFunction().applyDerivate(weightedSum);
    }

    public double getExpected() {
        return expected;
    }

    public void setExpected(final double expected) {
        this.expected = expected;
    }
}
