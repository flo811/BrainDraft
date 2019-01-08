package braindraft.model.network;

import braindraft.model.ActFunction;
import braindraft.model.MyDoubleProperty;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author flo
 */
public abstract class VirtualNeuron implements Outputable, Serializable {

    protected ActFunction activationFunction;
    protected double learningRate;
    protected double bias;

    protected final Map<Outputable, MyDoubleProperty> entriesWeight = new HashMap<>();
    protected final MyDoubleProperty biasWeight = new MyDoubleProperty();
    protected final MyDoubleProperty output = new MyDoubleProperty();

    protected final MyDoubleProperty errorProperty = new MyDoubleProperty();

    protected double weightedSum;

    public VirtualNeuron(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActFunction activationFunction, final Layer<?> previousLayer,
            final double learningRate, final double bias) {
        this.activationFunction = activationFunction;
        this.learningRate = learningRate;
        this.bias = bias;

        biasWeight.set(Math.random() * (weightRangeStartMax - weightRangeStartMin) + weightRangeStartMin);
        previousLayer.forEach(out -> entriesWeight.put(out, new MyDoubleProperty(
                Math.random() * (weightRangeStartMax - weightRangeStartMin) + weightRangeStartMin)));
    }

    public void activate() {
        weightedSum = bias * biasWeight.get() + entriesWeight.entrySet().stream()
                .mapToDouble(entry -> entry.getKey().getOutput() * entry.getValue().get())
                .sum();
        output.set(activationFunction.getActivationFunction().apply(weightedSum));
    }

    public abstract void calculateErrorAndUpdateWeight();

    public double getWeightWith(final VirtualNeuron neuron) {
        return entriesWeight.get(neuron).get();
    }

    @Override
    public MyDoubleProperty getOutputProperty() {
        return output;
    }

    @Override
    public double getOutput() {
        return output.get();
    }

    public ActFunction getActivationFunction() {
        return activationFunction;
    }

    public void setActivationFunction(final ActFunction activationFunction) {
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

    public MyDoubleProperty getEntryWeightProperty(final Outputable entry) {
        return entriesWeight.get(entry);
    }

    public void setEntryWeight(final Outputable entry, final double weight) {
        entriesWeight.get(entry).set(weight);
    }

    public MyDoubleProperty getBiasWeightProperty() {
        return biasWeight;
    }

    public void setBiasWeight(final double weight) {
        biasWeight.set(weight);
    }

    public MyDoubleProperty getErrorProperty() {
        return errorProperty;
    }

    public double getError() {
        return errorProperty.get();
    }
}
