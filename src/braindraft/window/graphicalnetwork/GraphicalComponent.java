package braindraft.window.graphicalnetwork;

import java.text.NumberFormat;
import javafx.geometry.Pos;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public abstract class GraphicalComponent extends StackPane {

    protected final Text output = new Text();

    protected final VBox vBox = new VBox(output);

    public GraphicalComponent(final Shape shape) {
        super(shape);
        getChildren().add(vBox);
        
        vBox.setAlignment(Pos.CENTER);
        output.setFill(Color.BLUE);
    }

    public abstract void actualize();

    protected static String formatValue(final double nbr) {
        final NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setMinimumFractionDigits(3);

        return numberFormat.format(nbr);
    }
}
