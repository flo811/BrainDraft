package braindraft.model.network;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @author flo
 */
public abstract class Layer<T extends Outputable> implements Serializable {

    protected final List<T> neurons = new ArrayList<>();

    public Layer(final List<T> neurons) {
        this.neurons.addAll(neurons);
    }

    public void forEach(final Consumer<T> consumer) {
        neurons.forEach(consumer);
    }

    public int size() {
        return neurons.size();
    }

    public List<T> getAll() {
        return neurons;
    }

    public T get(final int i) {
        return neurons.get(i);
    }
}
