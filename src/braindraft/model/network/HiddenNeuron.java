package braindraft.model.network;

import braindraft.model.ActivationFunctions;

/**
 *
 * @author flo
 */
public class HiddenNeuron extends VirtualNeuron {

    private Layer<? extends VirtualNeuron> nextLayer;

    public HiddenNeuron(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunctions activationFunction, final double learningRate,
            final double bias, final Layer<? extends Outputable>... previousLayers) {
        super(weightRangeStartMin, weightRangeStartMax, activationFunction, learningRate, bias, previousLayers);
    }

    @Override
    public void calculateErrorAndUpdateWeight() {
        final double ponderatedErrors = nextLayer.getAll().stream()
                .mapToDouble(neuron -> neuron.getWeightWith(this) * neuron.getError())
                .sum();
        errorProperty.set(ponderatedErrors * activationFunction.getActivationFunction().applyDerivate(weightedSum));
        biasWeight.add(learningRate * errorProperty.get());
        entriesWeight.forEach((neuron, weightProperty) -> weightProperty.add(learningRate * errorProperty.get() * neuron.getOutput()));
    }

    public void setNextLayer(final Layer<? extends VirtualNeuron> nextLayer) {
        this.nextLayer = nextLayer;
    }
}
