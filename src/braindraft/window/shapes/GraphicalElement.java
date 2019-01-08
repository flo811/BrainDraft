package braindraft.window.shapes;

import braindraft.model.network.Outputable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Shape;

/**
 *
 * @author flo
 */
public abstract class GraphicalElement extends StackPane {

    private final Label valueLabel = new Label();

    public GraphicalElement(final Shape shape, final Outputable outputable) {
        super(shape);
        super.getChildren().add(valueLabel);
        setAlignment(Pos.CENTER);

        valueLabel.textProperty().bind(outputable.getOutputProperty().asString());
    }
}
