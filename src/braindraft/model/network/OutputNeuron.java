package braindraft.model.network;

import braindraft.model.ActivationFunctions;

/**
 *
 * @author flo
 */
public class OutputNeuron extends VirtualNeuron {

    private double expected;

    public OutputNeuron(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunctions activationFunction, final double learningRate,
            final double bias, final Layer<? extends Outputable>... previousLayer) {
        super(weightRangeStartMin, weightRangeStartMax, activationFunction, learningRate, bias, previousLayer);
    }

    @Override
    public void calculateErrorAndUpdateWeight() {
        error = (expected - output) * activationFunction.getActivationFunction().applyDerivate(weightedSum);
        entriesWeight.entrySet().forEach(entry -> entry.setValue(learningRate * error * entry.getKey().getOutput()));
    }

    public double getExpected() {
        return expected;
    }

    public void setExpected(final double expected) {
        this.expected = expected;
    }
}
