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
        final double ponderatedErrors = nextLayer.stream()
                .mapToDouble(neuron -> neuron.getWeightWith(this) * neuron.getError())
                .sum();
        error = ponderatedErrors * activationFunction.getActivationFunction().applyDerivate(weightedSum);
        biasWeight += learningRate * error * bias;
        entriesWeight.entrySet().forEach(entry -> entry.setValue(entry.getValue() + learningRate * error * entry.getKey().getOutput()));
    }

    public void setNextLayer(final Layer<? extends VirtualNeuron> nextLayer) {
        this.nextLayer = nextLayer;
    }
}
