package braindraft.model.network;

import braindraft.model.ActivationFunction;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author flo
 */
public class OutputNeuron extends VirtualNeuron {

    private final SimpleDoubleProperty expectedProperty = new SimpleDoubleProperty();

    public OutputNeuron(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunction activationFunction, final Layer<?> previousLayer,
            final double learningRate, final double bias) {
        super(weightRangeStartMin, weightRangeStartMax, activationFunction, previousLayer, learningRate, bias);
    }

    @Override
    public void calculateErrorAndUpdateWeight() {
        errorProperty.set((expectedProperty.get() - output.get()) * activationFunction.applyDerivate(weightedSum));
        entriesWeight.forEach((neuron, weightProperty) -> weightProperty.add(learningRate * errorProperty.get() * neuron.getOutput()));
    }

    public SimpleDoubleProperty getExpectedProperty() {
        return expectedProperty;
    }
}
