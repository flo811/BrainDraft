package braindraft.dao;

import braindraft.model.network.HiddenLayer;
import braindraft.model.network.InputLayer;
import braindraft.model.network.Network;
import braindraft.model.network.OutputLayer;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author flo
 */
public class NetworkProxy implements Serializable {

    private Network readResolve() {
        return new Network(new InputLayer(new ArrayList<>()), new ArrayList<HiddenLayer>(),
                new OutputLayer(new VirtualFlow.ArrayLinkedList<>()));
    }
}
