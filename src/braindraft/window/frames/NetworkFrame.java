package braindraft.window.frames;

import braindraft.model.network.Network;
import braindraft.window.graphicalnetwork.GraphicalNetwork;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 *
 * @author flo
 */
public final class NetworkFrame extends StackPane {

    private static final int SPACING = 20;
    private static final Background BACKGROUND = new Background(new BackgroundFill(
            Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));

    private final HBox inputBox = new HBox(SPACING);
    private final VBox hiddenHBoxes = new VBox(SPACING);
    private final HBox outputBox = new HBox(SPACING);

    private final VBox networkBox = new VBox(10, inputBox, hiddenHBoxes, outputBox);
    private final ScrollPane scrollBox = new ScrollPane(networkBox);

    private final GraphicalNetwork graphicalNetwork;

    public NetworkFrame(final Network network) {
        super();
        getChildren().add(scrollBox);

        graphicalNetwork = new GraphicalNetwork(network);

        inputBox.getChildren().addAll(graphicalNetwork.getInputs());
        graphicalNetwork.getHiddensList().forEach(hiddens -> {
            final HBox hiddenBox = new HBox(SPACING);
            hiddenBox.getChildren().addAll(hiddens);
            hiddenHBoxes.getChildren().add(hiddenBox);
        });
        outputBox.getChildren().addAll(graphicalNetwork.getOutputs());

        scrollBox.setFitToWidth(true);
        scrollBox.setFitToHeight(true);

        networkBox.setBackground(BACKGROUND);
        networkBox.setAlignment(Pos.CENTER);
        inputBox.setAlignment(Pos.CENTER);
        hiddenHBoxes.getChildren().forEach(box -> ((HBox) box).setAlignment(Pos.CENTER));
        outputBox.setAlignment(Pos.CENTER);
    }

    public void actualize() {
        graphicalNetwork.actualize();
    }

    public GraphicalNetwork getGraphicalNetwork() {
        return graphicalNetwork;
    }
}
