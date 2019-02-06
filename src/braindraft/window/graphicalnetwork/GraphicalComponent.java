package braindraft.window.graphicalnetwork;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.geometry.Pos;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public abstract class GraphicalComponent extends VBox {

    protected final Text output = new Text();
    protected final Text nameTxt = new Text();

    protected final VBox vBox = new VBox(output);

    protected final Shape shape;

    public GraphicalComponent(final String name, final Shape shape) {
        super(5);

        this.shape = shape;
        getChildren().addAll(nameTxt, new StackPane(shape, vBox));

        nameTxt.setFill(Color.DARKBLUE);
        nameTxt.setText(name);
        setAlignment(Pos.CENTER);
        vBox.setAlignment(Pos.CENTER);
        output.setFill(Color.BLUE);
        shape.setStroke(Color.SLATEGRAY);
        vBox.setMouseTransparent(true);
        nameTxt.setVisible(false);

        final Effect effect = new BoxBlur();
        shape.setOnMouseEntered(e -> {
            shape.setEffect(effect);
            nameTxt.setVisible(true);
        });
        shape.setOnMouseExited(e -> {
            shape.setEffect(null);
            nameTxt.setVisible(false);
        });
    }

    public abstract void actualize();

    protected static String formatValue(final double nbr) {
        final NumberFormat numberFormat = NumberFormat.getInstance(Locale.US);
        numberFormat.setMinimumFractionDigits(3);

        return numberFormat.format(nbr);
    }
}
