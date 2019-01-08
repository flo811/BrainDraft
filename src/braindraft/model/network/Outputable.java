package braindraft.model.network;

import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author flo
 */
public interface Outputable {

    SimpleDoubleProperty getOutputProperty();

    double getOutput();
}
