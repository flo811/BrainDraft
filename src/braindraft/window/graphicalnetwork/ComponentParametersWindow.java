package braindraft.window.graphicalnetwork;

import braindraft.model.network.Outputable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author flo
 */
public class ComponentParametersWindow extends ScrollPane {

    protected static final double ELEMENT_SPACING = 3;
    protected static final double GROUP_SPACING = 20;

    protected Text valueTxt = new Text("Value :");
    protected TextField valueTF = new TextField();
    protected HBox valueVBox = new HBox(ELEMENT_SPACING, valueTxt, valueTF);

    protected final Button validateButton = new Button("Apply Changes");
    protected final VBox vBox = new VBox(GROUP_SPACING, valueVBox, validateButton);

    protected final Stage stage = new Stage(StageStyle.UNIFIED);

    public ComponentParametersWindow(final Outputable outputable) {
        super();

        final Scene scene = new Scene(this);
        stage.setTitle(outputable.getName());
        stage.getIcons().add(new Image("parameters.png"));
        stage.setScene(scene);
        setContent(vBox);

        setFitToWidth(true);
        setFitToHeight(true);
        vBox.setPadding(new Insets(7));
        vBox.setAlignment(Pos.CENTER);
        valueVBox.setAlignment(Pos.CENTER_LEFT);
        validateButton.setDefaultButton(true);

        valueTF.setText(GraphicalComponent.formatValue(outputable.getOutput()));
        valueTF.setOnKeyTyped(getOnlyNumValuesHandler());
        valueTF.textProperty().addListener(getChangeListener(valueTF));

        validateButton.setOnAction(e -> {
            outputable.setOutput(Double.valueOf(valueTF.getText()));
            stage.close();
        });

        stage.focusedProperty().addListener((obs, oldIsFocus, newIsFocus) -> {
            if (!newIsFocus) {
                stage.close();
            }
        });
    }

    protected static EventHandler<KeyEvent> getOnlyNumValuesHandler() {
        return k -> {
            if (k.getCharacter().matches("[^0-9.]")) {
                k.consume();
            }
        };
    }

    protected static ChangeListener<String> getChangeListener(final TextField tf) {
        return (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
            try {
                Double.valueOf(newVal);
                tf.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            } catch (final RuntimeException re) {
                tf.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY)));
            };
        };
    }

    public void showAndWait() {
        stage.showAndWait();
    }
}
