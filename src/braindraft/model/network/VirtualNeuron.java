package braindraft.model.network;

import braindraft.model.ActivationFunctions;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author flo
 */
public abstract class VirtualNeuron implements Outputable, Serializable {

    protected ActivationFunctions activationFunction;
    protected double learningRate;
    protected double bias;

    protected final Map<Outputable, Double> entriesWeight = new HashMap<>();
    protected double biasWeight;
    
    protected double output;
    protected double error;

    protected double weightedSum;

    public VirtualNeuron(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunctions activationFunction, final double learningRate,
            final double bias, final Layer<? extends Outputable>... previousLayers) {
        this.activationFunction = activationFunction;
        this.learningRate = learningRate;
        this.bias = bias;

        biasWeight = Math.random() * (weightRangeStartMax - weightRangeStartMin) + weightRangeStartMin;
        Arrays.stream(previousLayers)
                .flatMap(l -> l.stream())
                .forEach(out -> entriesWeight.put(out, Math.random() * (weightRangeStartMax - weightRangeStartMin) + weightRangeStartMin));
    }

    public void activate() {
        weightedSum = bias * biasWeight + entriesWeight.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getOutput() * entry.getValue())
                .sum();
        output = activationFunction.getActivationFunction().apply(weightedSum);
    }

    public abstract void calculateErrorAndUpdateWeight();

    public double getWeightWith(final VirtualNeuron neuron) {
        return entriesWeight.get(neuron);
    }

    @Override
    public double getOutput() {
        return output;
    }

    public double getError() {
        return error;
    }

    public ActivationFunctions getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(final ActivationFunctions activationFunction) {
        this.activationFunction = activationFunction;
    }

    public double getLearningRate() {
        return learningRate;
    }

    public void setLearningRate(final double learningRate) {
        this.learningRate = learningRate;
    }

    public double getBias() {
        return bias;
    }

    public void setBias(final double bias) {
        this.bias = bias;
    }

    public double getEntryWeight(final Outputable entry) {
        return entriesWeight.get(entry);
    }

    public double getBiasWeight() {
        return biasWeight;
    }

    public void setBiasWeight(final double weight) {
        biasWeight = weight;
    }
}
