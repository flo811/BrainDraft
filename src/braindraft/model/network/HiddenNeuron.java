package braindraft.model.network;

import braindraft.model.ActivationFunctions;

/**
 *
 * @author flo
 */
public class HiddenNeuron extends VirtualNeuron {

    private Layer<? extends VirtualNeuron> nextLayer;

    public HiddenNeuron(final String name, final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunctions activationFunction, final double learningRate,
            final double bias, final Layer<? extends Outputable>... previousLayers) {
        super(name, weightRangeStartMin, weightRangeStartMax, activationFunction, learningRate, bias, previousLayers);
    }

    @Override
    public void calculateDelta() {
        final double ponderatedErrors = nextLayer.stream()
                .mapToDouble(neuron -> neuron.getWeightWith(this) * neuron.getDelta())
                .sum();
        delta = ponderatedErrors * activationFunction.getActivationFunction().applyDerivate(weightedSum);
    }

    public void setNextLayer(final Layer<? extends VirtualNeuron> nextLayer) {
        this.nextLayer = nextLayer;
    }
}
