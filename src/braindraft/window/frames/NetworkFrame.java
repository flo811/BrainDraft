package braindraft.window.frames;

import braindraft.model.network.Network;
import braindraft.window.shapes.GraphicalHidden;
import braindraft.window.shapes.GraphicalInput;
import braindraft.window.shapes.GraphicalOutput;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

/**
 *
 * @author flo
 */
public final class NetworkFrame extends ScrollPane {

    private static final int SPACING = 20;
    private static final Background BACKGROUND = new Background(new BackgroundFill(
            Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));

    private final HBox inputBox = new HBox(SPACING);
    private final VBox hiddenHBoxes = new VBox(SPACING);
    private final HBox outputBox = new HBox(SPACING);

    private final VBox networkBox = new VBox(SPACING, inputBox, hiddenHBoxes, outputBox);

    public NetworkFrame(final Network network) {
        super();
        setContent(networkBox);
        setFitToWidth(true);
        setFitToHeight(true);

        network.getInputLayer().forEach(neuron -> inputBox.getChildren()
                .add(new GraphicalInput(new Circle(50, Color.ANTIQUEWHITE), neuron)));

        network.getHiddenLayers().forEach(hiddenLayer -> {
            final HBox hiddenBox = new HBox(SPACING);
            hiddenLayer.forEach(neuron -> {
                final Rectangle hiddenShape = new Rectangle(50, 50, Color.LIGHTSTEELBLUE);
                hiddenShape.setArcWidth(50);
                hiddenShape.setArcHeight(50);
                hiddenBox.getChildren().add(new GraphicalHidden(hiddenShape, neuron));
            });
            hiddenHBoxes.getChildren().add(hiddenBox);
        });

        network.getOutputLayer().forEach(neuron -> {
            final Rectangle outputShape = new Rectangle(50, 50, Color.LIGHTGREEN);
            outputShape.setArcWidth(20);
            outputShape.setArcHeight(20);
            outputBox.getChildren().add(new GraphicalOutput(outputShape, neuron));
        });

        networkBox.setBackground(BACKGROUND);
        networkBox.setAlignment(Pos.CENTER);
        inputBox.setAlignment(Pos.CENTER);
        hiddenHBoxes.getChildren().forEach(box -> ((HBox) box).setAlignment(Pos.CENTER));
        outputBox.setAlignment(Pos.CENTER);
    }
}
