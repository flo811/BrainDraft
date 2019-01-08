package braindraft.model.network;

import braindraft.model.ActFunction;
import braindraft.model.MyDoubleProperty;

/**
 *
 * @author flo
 */
public class OutputNeuron extends VirtualNeuron {

    private final MyDoubleProperty expectedProperty = new MyDoubleProperty();

    public OutputNeuron(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActFunction activationFunction, final Layer<?> previousLayer,
            final double learningRate, final double bias) {
        super(weightRangeStartMin, weightRangeStartMax, activationFunction, previousLayer, learningRate, bias);
    }

    @Override
    public void calculateErrorAndUpdateWeight() {
        errorProperty.set((expectedProperty.get() - output.get())
                * activationFunction.getActivationFunction().applyDerivate(weightedSum));
        entriesWeight.forEach((neuron, weightProperty)
                -> weightProperty.add(learningRate * errorProperty.get() * neuron.getOutput()));
    }

    public MyDoubleProperty getExpectedProperty() {
        return expectedProperty;
    }
}
