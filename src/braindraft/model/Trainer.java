package braindraft.model;

import braindraft.model.dataset.Data;
import braindraft.model.dataset.DataSet;
import braindraft.model.network.HiddenLayer;
import braindraft.model.network.Network;
import braindraft.model.network.OutputNeuron;
import braindraft.window.graphicalnetwork.GraphicalNetwork;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;

/**
 *
 * @author flo
 */
public class Trainer implements NetworkTrainer {

    private final Network network;
    private final GraphicalNetwork graphicalNetwork;

    private final SimpleBooleanProperty runningProperty;

    private DataSet trainSet;
    private DataSet testSet;

    private volatile boolean inPause = false;
    private volatile boolean toStop = false;

    public Trainer(final Network network, final GraphicalNetwork graphicalNetwork, final SimpleBooleanProperty runningProperty) {
        this.network = network;
        this.graphicalNetwork = graphicalNetwork;
        this.runningProperty = runningProperty;
    }

    public void trainWith(final DataSet datas) throws Exception {
        trainSet = datas;

        if (trainSet.getInputSize() != network.getInputLayer().size() || trainSet.getOutputSize() != network.getOutputLayer().size()) {
            trainSet = null;
            throw new Exception("Datas are incompatible with the network !");
        }
    }

    public void testWith(final DataSet datas) throws Exception {
        testSet = datas;

        if (testSet.getInputSize() != network.getInputLayer().size() || testSet.getOutputSize() != network.getOutputLayer().size()) {
            testSet = null;
            throw new Exception("Datas are incompatible with the network !");
        }
    }

    @Override
    public void train() {
        final Task<Void> testTask = new Task<Void>() {
            @Override
            protected Void call() {
                if (trainSet == null) {
                    return null;
                }

                for (int i = 0; i < trainSet.size(); i++) {
                    while (inPause) {
                        try {
                            Thread.sleep(100);
                        } catch (final InterruptedException ie) {
                            cancel();
                        }
                    }
                    if (toStop) {
                        return null;
                    }

                    final Data data = trainSet.get(i);
                    activate(data);

                    final List<OutputNeuron> outputNeurons = network.getOutputLayer().getAll();
                    for (int j = 0; j < outputNeurons.size(); j++) {
                        outputNeurons.get(j).setExpected(data.getOutput()[j]);
                    }

                    network.getOutputLayer().updateWeights();
                    network.getHiddenLayers().forEach(HiddenLayer::updateWeights);

                    Platform.runLater(graphicalNetwork::actualize);

                    try {
                        Thread.sleep(1);
                    } catch (final InterruptedException ie) {
                        cancel();
                    }
                }

                return null;
            }
        };

        testTask.setOnSucceeded(e -> {
            runningProperty.set(false);
            toStop = false;
            final Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Network successfully trained !");
            alert.showAndWait();
        });

        new Thread(testTask).start();
    }

    @Override
    public void test() {
        final Task<DoubleSummaryStatistics> testTask = new Task<DoubleSummaryStatistics>() {
            @Override
            protected DoubleSummaryStatistics call() {
                if (testSet == null) {
                    return null;
                }

                final List<Double> errors = new ArrayList<>();
                final ValidationRule sortingRule = testSet.getSortingRule();

                for (int i = 0; i < testSet.size(); i++) {
                    while (inPause) {
                        try {
                            Thread.sleep(100);
                        } catch (final InterruptedException ie) {
                            cancel();
                        }
                    }
                    if (toStop) {
                        return null;
                    }

                    final Data data = testSet.get(i);
                    if (sortingRule != null) {
                        errors.add(sortingRule.apply(activate(data), data.getOutput()) ? 1.0 : 0.0);
                    } else {
                        errors.add(quadraticError(activate(data), data.getOutput()));
                    }

                    Platform.runLater(graphicalNetwork::actualize);

                   // try {
                     //   Thread.sleep(1);
                    //} catch (final InterruptedException ie) {
                   //     cancel();
                    //}
                }

                return errors.stream().collect(Collectors.summarizingDouble(x -> x));
            }
        };

        testTask.setOnSucceeded(e -> {
            try {
                runningProperty.set(false);
                toStop = false;
                final DoubleSummaryStatistics results = testTask.get();
                final Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText("Network successfully tested !");
                if (testSet.getSortingRule() == null) {
                    alert.setContentText("Number of tests : " + results.getCount() + "\nMean error = " + results.getAverage());
                } else {
                    final int totNbr = (int) results.getCount();
                    final int okNbr = (int) results.getSum();
                    final int okPercent = (int) (100 * results.getAverage());
                    alert.setContentText("Number of tests : " + totNbr + "\nOK = " + okNbr + " (" + okPercent + "%)"
                            + "\nKO = " + (totNbr - okNbr) + " (" + (100 - okPercent) + "%)");
                }
                alert.showAndWait();
            } catch (final Exception ex) {
                ex.printStackTrace();
            }
        });

        new Thread(testTask).start();
    }

    public void predict() {
        activate(new Data(
                network.getInputLayer().stream()
                        .mapToDouble(neuron -> neuron.getOutput())
                        .toArray(),
                new double[network.getOutputLayer().size()]
        ));
        graphicalNetwork.actualize();
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
