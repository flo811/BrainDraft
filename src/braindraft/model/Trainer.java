package braindraft.model;

import braindraft.model.network.Network;

/**
 *
 * @author flo
 */
public class Trainer implements NetworkTrainer {

    private final Network network;

    public Trainer(final Network network) {
        this.network = network;
    }

    @Override
    public void train() {

    }

    @Override
    public void test() {

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

    public Network getNetwork() {
        return network;
    }
}
