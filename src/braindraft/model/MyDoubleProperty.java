package braindraft.model;

import braindraft.dao.MyDoublePropertyProxy;
import java.io.Serializable;
import javafx.beans.property.SimpleDoubleProperty;

/**
 *
 * @author flo
 */
public class MyDoubleProperty extends SimpleDoubleProperty implements Serializable {

    private static final long serialVersionUID = 4846179829111020259L;

    public MyDoubleProperty() {
    }

    public MyDoubleProperty(double initialValue) {
        super(initialValue);
    }

    private Object writeReplace() {
        return new MyDoublePropertyProxy(get());
    }
}
