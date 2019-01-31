package braindraft.model;

import braindraft.model.dataset.Data;
import braindraft.model.dataset.DataSet;
import braindraft.model.network.HiddenLayer;
import braindraft.model.network.Network;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author flo
 */
public class Trainer implements NetworkTrainer {

    private final Network network;

    private DataSet trainSet;
    private DataSet testSet;

    private final SimpleBooleanProperty runningProperty;

    private volatile boolean inPause = false;
    private volatile boolean toStop = false;

    public Trainer(final Network network, final SimpleBooleanProperty runningProperty) {
        this.network = network;
        this.runningProperty = runningProperty;
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
        final Callable<DoubleSummaryStatistics> testCall = () -> {
            final List<Double> errors = new ArrayList<>();

            for (int i = 0; i < testSet.size(); i++) {
                final Data data = testSet.get(i);
                errors.add(quadraticError(activate(data), data.getOutput()));

                while (inPause) {
                    Thread.sleep(100);
                }
                if (toStop) {
                    return null;
                }
            }

            runningProperty.set(false);
            return errors.stream()
                    .mapToDouble(val -> val)
                    .summaryStatistics();
        };

        FutureTask<DoubleSummaryStatistics> futureTask = new FutureTask<>(testCall);
        new Thread(futureTask).start();
        try {
            return futureTask.get();
        } catch (final InterruptedException | ExecutionException ex) {
            return null;
        }
    }

    @Override
    public void pause() {
        inPause = true;
    }

    @Override
    public void continuate() {
        inPause = false;
    }

    @Override
    public void stop() {
        toStop = true;
        inPause = false;
    }

    private double[] activate(final Data data) {
        network.getInputLayer().setInputValues(data.getInput());
        network.getHiddenLayers().forEach(HiddenLayer::activate);
        network.getOutputLayer().activate();

        return network.getOutputLayer().getOutputs();
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

    public Network getNetwork() {
        return network;
    }
}
