package braindraft.model;

import java.util.DoubleSummaryStatistics;

/**
 *
 * @author flo
 */
public interface NetworkTrainer {

    public void train();

    public DoubleSummaryStatistics test();

    public void pause();

    public void continuate();

    public void stop();
}
