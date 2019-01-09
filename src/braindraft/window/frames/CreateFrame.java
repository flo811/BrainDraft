package braindraft.window.frames;

import braindraft.dao.NetworkDAO;
import braindraft.model.ActFunction;
import braindraft.model.network.Network;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public final class CreateFrame extends BorderPane {

    private final Text inputText = new Text("How many inouts ?");
    private final Text nbrHiddenText = new Text("How many hidden layers ?");
    private final Text outputText = new Text("How many outputs ?");
    private final Text activationfunctionText = new Text("Activation function :");
    private final Text minWeightText = new Text("Min starting weight :");
    private final Text maxWeightText = new Text("Max starting weight :");
    private final Text biasText = new Text("Init bias :");
    private final Text learningRateText = new Text("Learning rate :");

    private final TextField inputTextField = new TextField("2");
    private final TextField outputTextField = new TextField("1");
    private final TextField minWeightTextField = new TextField("-1");
    private final TextField maxWeightTextField = new TextField("1");
    private final TextField biasTextField = new TextField("1");
    private final TextField learningRateTextField = new TextField("1");

    private final ComboBox<Integer> nbrHiddenCombo
            = new ComboBox<>(FXCollections.observableArrayList(0, 1, 2, 3, 4, 5, 6));
    private final ComboBox<ActFunction> activationFunctionCombo;

    private final VBox inputsBox = new VBox(inputText, inputTextField);
    private final VBox nbrHiddenBox = new VBox(nbrHiddenText, nbrHiddenCombo);
    private final VBox hiddenBox = new VBox(20);
    private final VBox outputBox = new VBox(outputText, outputTextField);
    private final VBox activationFunctionBox = new VBox(activationfunctionText);
    private final VBox minWeightBox = new VBox(minWeightText, minWeightTextField);
    private final VBox maxWeightBox = new VBox(maxWeightText, maxWeightTextField);
    private final VBox biasBox = new VBox(biasText, biasTextField);
    private final VBox learningRateBox = new VBox(learningRateText, learningRateTextField);

    private final VBox optionsContainerBox = new VBox(20, inputsBox, nbrHiddenBox, hiddenBox,
            outputBox, activationFunctionBox, minWeightBox, maxWeightBox, biasBox, learningRateBox);
    private final ScrollPane optionsBox = new ScrollPane(optionsContainerBox);

    private final Map<Integer, Integer> hiddenNbrs = new HashMap<>();
    private final SimpleObjectProperty<Network> networkProperty = new SimpleObjectProperty<>();

    public CreateFrame() throws IllegalArgumentException, IllegalAccessException {
        super();
        setLeft(optionsBox);

        optionsBox.setFitToWidth(true);
        optionsBox.setFitToHeight(true);
        optionsContainerBox.setPadding(new Insets(20, 50, 10, 20));

        activationFunctionCombo = new ComboBox<>(FXCollections.observableArrayList(
                Arrays.stream(ActFunction.values())
                        .collect(Collectors.toList())
        ));
        activationFunctionBox.getChildren().add(activationFunctionCombo);

        inputTextField.textProperty().addListener(createNetworkOnChange(inputTextField, true));
        nbrHiddenCombo.valueProperty().addListener((ob, oVal, nVal) -> {
            hiddenBox.getChildren().clear();
            hiddenNbrs.clear();
            final int nbr = nbrHiddenCombo.getValue();
            for (int i = 0; i < nbr; i++) {
                final int key = i;
                final TextField hiddenTextField = new TextField();
                hiddenTextField.textProperty().addListener((obs, oldVal, newVal) -> {
                    try {
                        if (Integer.valueOf(newVal) < 1) {
                            throw new RuntimeException();
                        }
                        hiddenTextField.setBackground(Background.EMPTY);
                        hiddenNbrs.put(key, Integer.valueOf(hiddenTextField.getText()));
                        createNetwork();
                    } catch (final RuntimeException nfe) {
                        hiddenTextField.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY)));
                    }
                });
                hiddenTextField.setText("1");
                final VBox hiddenLayer = new VBox(new Text("Hidden layer " + (i + 1) + " size:"), hiddenTextField);
                hiddenBox.getChildren().add(hiddenLayer);
            }
        }
        );
        outputTextField.textProperty().addListener(createNetworkOnChange(outputTextField, true));
        activationFunctionCombo.valueProperty().addListener((obs, oldVal, newVal) -> createNetwork());
        minWeightTextField.textProperty().addListener(createNetworkOnChange(minWeightTextField, false));
        maxWeightTextField.textProperty().addListener(createNetworkOnChange(maxWeightTextField, false));
        biasTextField.textProperty().addListener(createNetworkOnChange(biasTextField, false));
        learningRateTextField.textProperty().addListener(createNetworkOnChange(learningRateTextField, false));

        final EventHandler<KeyEvent> onlyNumValuesHandler = k -> {
            if (k.getCharacter().matches("[^0-9.]")) {
                k.consume();
            }
        };
        inputTextField.setOnKeyTyped(onlyNumValuesHandler);
        outputTextField.setOnKeyTyped(onlyNumValuesHandler);
        minWeightTextField.setOnKeyTyped(onlyNumValuesHandler);
        maxWeightTextField.setOnKeyTyped(onlyNumValuesHandler);

        activationFunctionCombo.getSelectionModel().selectFirst();
        nbrHiddenCombo.getSelectionModel().selectFirst();
    }

    private ChangeListener<String> createNetworkOnChange(final TextField tf, final boolean isInteger) {
        return (ObservableValue<? extends String> obs, String oldVal, String newVal) -> {
            try {
                if (isInteger) {
                    if (Integer.valueOf(newVal) < 1) {
                        throw new RuntimeException();
                    }
                } else {
                    Double.valueOf(newVal);
                }
                tf.setBackground(Background.EMPTY);
                createNetwork();
            } catch (final RuntimeException nfe) {
                tf.setBackground(new Background(new BackgroundFill(Color.ORANGERED, CornerRadii.EMPTY, Insets.EMPTY)));
            }
        };
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

    public SimpleObjectProperty<Network> getNetworkProperty() {
        return networkProperty;
    }
}
