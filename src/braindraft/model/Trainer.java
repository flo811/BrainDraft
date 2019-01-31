package braindraft.model;

import braindraft.model.dataset.Data;
import braindraft.model.dataset.DataSet;
import braindraft.model.network.HiddenLayer;
import braindraft.model.network.Network;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 *
 * @author flo
 */
public class Trainer implements NetworkTrainer {

    private DataSet trainSet;
    private DataSet testSet;

    private final Network network;

    public Trainer(final Network network) {
        this.network = network;
    }

    public void trainWith(final List<Data> datas) throws Exception {
        trainSet = new DataSet(datas);

        if (trainSet.getInputSize() != network.getInputLayer().size() || trainSet.getOutputSize() != network.getOutputLayer().size()) {
            trainSet = null;
            throw new Exception("Datas are incompatible with the network !");
        }
    }

    public void testWith(final List<Data> datas) throws Exception {
        testSet = new DataSet(datas);

        if (testSet.getInputSize() != network.getInputLayer().size() || testSet.getOutputSize() != network.getOutputLayer().size()) {
            testSet = null;
            throw new Exception("Datas are incompatible with the network !");
        }
    }

    @Override
    public void train() {

    }

    @Override
    public DoubleSummaryStatistics test() {
        return testSet.stream()
                .mapToDouble(data -> quadraticError(activate(data), data.getOutput()))
                .summaryStatistics();
    }

    @Override
    public void pause() {

    }

    @Override
    public void continuate() {

    }

    @Override
    public void stop() {

    }

    private double quadraticError(final double[] actual, final double[] expected) {
        final int size = actual.length;
        double sum = 0;

        for (int i = 0; i < size; i++) {
            final double diff = actual[i] - expected[i];
            sum += diff * diff;
        }

        return sum / size;
    }

    private double[] activate(final Data data) {
        network.getInputLayer().setInputValues(data.getInput());
        network.getHiddenLayers().forEach(HiddenLayer::activate);
        network.getOutputLayer().activate();

        return network.getOutputLayer().getOutputs();
    }

    public Network getNetwork() {
        return network;
    }
}
