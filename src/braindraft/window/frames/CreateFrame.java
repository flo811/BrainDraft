package braindraft.window.frames;

import braindraft.dao.NetworkDAO;
import braindraft.model.ActivationFunctions;
import braindraft.model.network.Network;
import braindraft.window.Window;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public final class CreateFrame extends BorderPane {

    private final Text inputText = new Text("How many inputs ?");
    private final Text nbrHiddenText = new Text("How many hidden layers ?");
    private final Text outputText = new Text("How many outputs ?");
    private final Text activationfunctionText = new Text("Activation function :");
    private final Text minWeightText = new Text("Min starting weight :");
    private final Text maxWeightText = new Text("Max starting weight :");
    private final Text biasText = new Text("Init bias :");
    private final Text learningRateText = new Text("Learning rate :");

    private final TextField inputTextField = new TextField("4");
    private final TextField outputTextField = new TextField("1");
    private final TextField minWeightTextField = new TextField("-1");
    private final TextField maxWeightTextField = new TextField("1");
    private final TextField biasTextField = new TextField("1");
    private final TextField learningRateTextField = new TextField("0.1");

    private final ComboBox<Integer> nbrHiddenCombo = new ComboBox<>(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6));
    private final ComboBox<ActivationFunctions> activationFunctionCombo;

    private final Button validateButton = new Button("", new ImageView("validate.png"));

    private final VBox inputsBox = new VBox(inputText, inputTextField);
    private final VBox nbrHiddenBox = new VBox(nbrHiddenText, nbrHiddenCombo);
    private final VBox hiddenBox = new VBox(20);
    private final VBox outputBox = new VBox(outputText, outputTextField);
    private final VBox activationFunctionBox = new VBox(activationfunctionText);
    private final VBox minWeightBox = new VBox(minWeightText, minWeightTextField);
    private final VBox maxWeightBox = new VBox(maxWeightText, maxWeightTextField);
    private final VBox biasBox = new VBox(biasText, biasTextField);
    private final VBox learningRateBox = new VBox(learningRateText, learningRateTextField);

    private final VBox optionsContainerBox = new VBox(20, inputsBox, nbrHiddenBox, hiddenBox, outputBox,
            activationFunctionBox, minWeightBox, maxWeightBox, biasBox, learningRateBox, new StackPane(validateButton));
    private final ScrollPane optionsBox = new ScrollPane(optionsContainerBox);

    private final Map<Integer, Integer> hiddenNbrs = new HashMap<>();
    private final SimpleObjectProperty<Network> networkProperty = new SimpleObjectProperty<>();

    public CreateFrame(final Window window) throws IllegalArgumentException, IllegalAccessException {
        super();
        setLeft(optionsBox);

        optionsBox.setFitToWidth(true);
        optionsBox.setFitToHeight(true);
        optionsContainerBox.setPadding(new Insets(10, 10, 10, 10));
        validateButton.setDefaultButton(true);

        inputTextField.setBorder(getTextFileBorder());
        outputTextField.setBorder(getTextFileBorder());
        minWeightTextField.setBorder(getTextFileBorder());
        maxWeightTextField.setBorder(getTextFileBorder());
        biasTextField.setBorder(getTextFileBorder());
        learningRateTextField.setBorder(getTextFileBorder());

        activationFunctionCombo = new ComboBox<>(FXCollections.observableArrayList(Arrays.stream(ActivationFunctions.values())
                .collect(Collectors.toList())
        ));
        activationFunctionBox.getChildren().add(activationFunctionCombo);

        inputTextField.textProperty().addListener(getChangeListener(inputTextField, true));
        nbrHiddenCombo.valueProperty().addListener((ob, oVal, nVal) -> createLayers());
        outputTextField.textProperty().addListener(getChangeListener(outputTextField, true));
        activationFunctionCombo.valueProperty().addListener((obs, oldVal, newVal) -> createNetwork());
        minWeightTextField.textProperty().addListener(getChangeListener(minWeightTextField, false));
        maxWeightTextField.textProperty().addListener(getChangeListener(maxWeightTextField, false));
        biasTextField.textProperty().addListener(getChangeListener(biasTextField, false));
        learningRateTextField.textProperty().addListener(getChangeListener(learningRateTextField, false));

        inputTextField.setOnKeyTyped(getOnlyNumValuesHandler());
        outputTextField.setOnKeyTyped(getOnlyNumValuesHandler());
        minWeightTextField.setOnKeyTyped(getOnlyNumValuesHandler());
        maxWeightTextField.setOnKeyTyped(getOnlyNumValuesHandler());

        activationFunctionCombo.getSelectionModel().selectFirst();
        nbrHiddenCombo.getSelectionModel().select(1);

        validateButton.setOnAction(e -> window.displayNetworkFrame(networkProperty.get()));
    }

    private void createNetwork() {
        final int input = Integer.valueOf(inputTextField.getText());
        final int output = Integer.valueOf(outputTextField.getText());
        final double min = Double.valueOf(minWeightTextField.getText());
        final double max = Double.valueOf(maxWeightTextField.getText());
        final double bias = Double.valueOf(biasTextField.getText());
        final double rate = Double.valueOf(learningRateTextField.getText());

        networkProperty.set(NetworkDAO.createSimpleNetwork(min, max,
                activationFunctionCombo.getValue(), rate, bias, input,
                new ArrayList<>(hiddenNbrs.values()), output)
        );
        setCenter(new NetworkFrame(networkProperty.get()));
    }

    private void createLayers() {
        hiddenNbrs.clear();
        hiddenBox.getChildren().clear();

        for (int i = 0; i < nbrHiddenCombo.getValue(); i++) {
            final TextField hiddenTextField = new TextField();
            hiddenBox.getChildren().add(
                    new VBox(
                            new Text("Hidden layer " + (i + 1) + " size:"), hiddenTextField)
            );

            final int key = i;
            hiddenTextField.setBorder(getTextFileBorder());
            hiddenTextField.setOnKeyTyped(getOnlyNumValuesHandler());
            hiddenTextField.textProperty().addListener((obs, oldVal, newVal) -> {
                try {
                    if (Integer.valueOf(newVal) < 1) {
                        throw new RuntimeException();
                    }
                    hiddenTextField.setBackground(getTextFieldBackground(Color.WHITE));
                    hiddenNbrs.put(key, Integer.valueOf(hiddenTextField.getText()));
                    try {
                        createNetwork();
                    } catch (final RuntimeException re) {
                    }
                } catch (final RuntimeException re) {
                    hiddenTextField.setBackground(getTextFieldBackground(Color.ORANGERED));
                } finally {
                    setButtonVisibility();
                }
            });

            hiddenTextField.setText("4");
        }
        
        createNetwork();
    }

    private EventHandler<KeyEvent> getOnlyNumValuesHandler() {
        return k -> {
            if (k.getCharacter().matches("[^0-9.]")) {
                k.consume();
            }
        };
    }

    private ChangeListener<String> getChangeListener(final TextField tf, final boolean mustBeInteger) {
        return (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
            try {
                if (mustBeInteger) {
                    if (Integer.valueOf(newVal) < 1) {
                        throw new RuntimeException();
                    }
                } else {
                    Double.valueOf(newVal);
                }
                tf.setBackground(getTextFieldBackground(Color.WHITE));
                try {
                    createNetwork();
                } catch (final RuntimeException re) {
                }
            } catch (final RuntimeException re) {
                tf.setBackground(getTextFieldBackground(Color.ORANGERED));
            } finally {
                setButtonVisibility();
            };
        };
    }

    private void setButtonVisibility() {
        final Stream<TextField> tfStream = Stream.concat(
                Stream.of(inputTextField, outputTextField, minWeightTextField, maxWeightTextField, biasTextField, learningRateTextField),
                Stream.of(hiddenBox.getChildren().stream()
                        .flatMap(vb -> ((Pane) vb).getChildren().stream())
                        .filter(el -> el instanceof TextField)
                        .map(tf -> (TextField) tf)
                        .toArray(TextField[]::new))
        );

        final boolean disable = tfStream
                .map(tf -> tf.getBackground())
                .filter(b -> b != null)
                .flatMap(b -> b.getFills().stream())
                .map(bgf -> bgf.getFill())
                .anyMatch(color -> color == Color.ORANGERED);

        validateButton.setDisable(disable);
    }

    private Border getTextFileBorder() {
        return new Border(
                new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
                        new CornerRadii(5), BorderWidths.DEFAULT)
        );
    }

    private Background getTextFieldBackground(final Color color) {
        return new Background(
                new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)
        );
    }

    public SimpleObjectProperty<Network> getNetworkProperty() {
        return networkProperty;
    }
}
