package braindraft.model.network;

import braindraft.model.ActivationFunction;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author flo
 */
public abstract class VirtualNeuron implements Outputable, Serializable {

    protected ActivationFunction activationFunction;
    protected double learningRate;
    protected double bias;

    protected final Map<Outputable, SimpleDoubleProperty> entriesWeight = new HashMap<>();
    protected final SimpleDoubleProperty biasWeight = new SimpleDoubleProperty();
    protected final SimpleDoubleProperty output = new SimpleDoubleProperty();

    protected final SimpleDoubleProperty errorProperty = new SimpleDoubleProperty();

    protected double weightedSum;

    public VirtualNeuron(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunction activationFunction, final Layer<?> previousLayer,
            final double learningRate, final double bias) {
        this.activationFunction = activationFunction;
        this.learningRate = learningRate;
        this.bias = bias;

        biasWeight.set(Math.random() * (weightRangeStartMax - weightRangeStartMin) + weightRangeStartMin);
        previousLayer.forEach(out -> entriesWeight.put(out, new SimpleDoubleProperty(
                Math.random() * (weightRangeStartMax - weightRangeStartMin) + weightRangeStartMin)));
    }

    public void activate() {
        weightedSum = bias * biasWeight.get() + entriesWeight.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getOutput() * entry.getValue().get())
                .sum();
        output.set(activationFunction.apply(weightedSum));
    }

    public abstract void calculateErrorAndUpdateWeight();

    public double getWeightWith(final VirtualNeuron neuron) {
        return entriesWeight.get(neuron).get();
    }

    @Override
    public SimpleDoubleProperty getOutputProperty() {
        return output;
    }

    @Override
    public double getOutput() {
        return output.get();
    }

    public ActivationFunction getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(final ActivationFunction activationFunction) {
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

    public SimpleDoubleProperty getEntryWeightProperty(final Outputable entry) {
        return entriesWeight.get(entry);
    }

    public void setEntryWeight(final Outputable entry, final double weight) {
        entriesWeight.get(entry).set(weight);
    }

    public SimpleDoubleProperty getBiasWeightProperty() {
        return biasWeight;
    }

    public void setBiasWeight(final double weight) {
        biasWeight.set(weight);
    }

    public SimpleDoubleProperty getErrorProperty() {
        return errorProperty;
    }

    public double getError() {
        return errorProperty.get();
    }
}
