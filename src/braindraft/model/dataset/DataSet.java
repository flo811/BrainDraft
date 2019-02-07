package braindraft.model.dataset;

import braindraft.model.ValidationRule;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 *
 * @author flo
 */
public class DataSet implements Serializable {

    private final List<Data> dataSet = new ArrayList<>();

    private final int inputSize;
    private final int outputSize;

    private final ValidationRule sortingRule;

    public DataSet(final List<Data> datas, final ValidationRule sortingRule) throws Exception {
        if (datas.isEmpty()) {
            throw new Exception("Dataset is empty !");
        }

        this.sortingRule = sortingRule;
        inputSize = datas.get(0).getInput().length;
        outputSize = datas.get(0).getOutput().length;

        if (datas.stream().anyMatch(data -> data.getInput().length != inputSize || data.getOutput().length != outputSize)) {
            throw new Exception("Datas have different sizes !");
        }

        dataSet.addAll(datas);
    }

    public Stream<Data> stream() {
        return dataSet.stream();
    }

    public void forEach(final Consumer<Data> dataConsumer) {
        dataSet.forEach(dataConsumer);
    }

    public int size() {
        return dataSet.size();
    }

    public List<Data> getDatas() {
        return dataSet;
    }

    public Data get(final int i) {
        return dataSet.get(i);
    }

    public int getInputSize() {
        return inputSize;
    }

    public int getOutputSize() {
        return outputSize;
    }

    public ValidationRule getSortingRule() {
        return sortingRule;
    }
}
