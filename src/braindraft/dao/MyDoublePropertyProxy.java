package braindraft.dao;

import braindraft.model.MyDoubleProperty;
import java.io.Serializable;

/**
 *
 * @author flo
 */
public class MyDoublePropertyProxy implements Serializable {

    private static final long serialVersionUID = 2121357408327589216L;

    private final double value;

    public MyDoublePropertyProxy(final double value) {
        this.value = value;
    }

    private Object readResolve() {
        return new MyDoubleProperty(value);
    }
}
