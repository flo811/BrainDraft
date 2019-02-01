package braindraft.model;

/**
 *
 * @author flo
 */
public interface NetworkTrainer {

    public void train();

    public void test();

    public void pause();

    public void continuate();

    public void stop();
}
