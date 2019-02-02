package braindraft.dao;

import braindraft.model.ActivationFunctions;
import braindraft.model.dataset.Data;
import braindraft.model.network.HiddenLayer;
import braindraft.model.network.HiddenNeuron;
import braindraft.model.network.InputLayer;
import braindraft.model.network.InputNeuron;
import braindraft.model.network.Network;
import braindraft.model.network.OutputLayer;
import braindraft.model.network.OutputNeuron;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * @author flo
 */
public final class NetworkDAO {

    private NetworkDAO() {
    }

    public static Network createSimpleNetwork(final double weightRangeStartMin, final double weightRangeStartMax,
            final ActivationFunctions activationFunction, final double learningRate, final double bias,
            final int inputNbr, final List<Integer> hiddenNbrs, final int outputNbr) {

        final List<InputNeuron> inputs = IntStream
                .range(0, inputNbr)
                .mapToObj(i -> new InputNeuron())
                .collect(Collectors.toList());
        final InputLayer inputLayer = new InputLayer(inputs);

        final LinkedList<HiddenLayer> hiddenLayers = new LinkedList<>();
        for (final int nbr : hiddenNbrs) {
            final List<HiddenNeuron> hidden = IntStream
                    .range(0, nbr)
                    .mapToObj(i -> new HiddenNeuron(weightRangeStartMin, weightRangeStartMax,
                    activationFunction, learningRate, bias,
                    hiddenLayers.isEmpty() ? inputLayer : hiddenLayers.getLast()))
                    .collect(Collectors.toList());
            hiddenLayers.add(new HiddenLayer(hidden));
        }

        final List<OutputNeuron> outputs = IntStream
                .range(0, outputNbr)
                .mapToObj(i -> new OutputNeuron(weightRangeStartMin, weightRangeStartMax,
                activationFunction, learningRate, bias,
                hiddenLayers.isEmpty() ? inputLayer : hiddenLayers.getLast()))
                .collect(Collectors.toList());

        return new Network(inputLayer, hiddenLayers, new OutputLayer(outputs));
    }

    public static Network openNetwork(final File file) throws IOException, ClassNotFoundException {
        if (file == null) {
            return null;
        }

        try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (Network) ois.readObject();
        }
    }

    public static void saveNetwork(final Network network, final File file) throws IOException {
        try (final ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(network);
        }
    }

    public static List<Data> openDataSet(final File file) throws IOException, ClassNotFoundException {
        if (file == null) {
            return null;
        }

        try (final ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<Data>) ois.readObject();
        }
    }
}
