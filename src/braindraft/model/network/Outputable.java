package braindraft.model.network;

import braindraft.model.MyDoubleProperty;


/**
 *
 * @author flo
 */
public interface Outputable {

    MyDoubleProperty getOutputProperty();

    double getOutput();
}
