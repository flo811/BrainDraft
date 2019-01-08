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
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
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
        activationFunctionCombo.getSelectionModel().selectFirst();
        activationFunctionBox.getChildren().add(activationFunctionCombo);

        inputTextField.textProperty().addListener((observable, oldValue, newValue) -> createNetwork());
        nbrHiddenCombo.valueProperty().addListener((observable, oldValue, newValue) -> {
            hiddenBox.getChildren().clear();
            hiddenNbrs.clear();
            final int nbr = nbrHiddenCombo.getValue();
            for (int i = 0; i < nbr; i++) {
                final int key = i;
                final TextField hiddenTextField = new TextField();
                hiddenTextField.textProperty().addListener((obs, oldVal, newVal) -> {
                    hiddenNbrs.put(key, Integer.valueOf(hiddenTextField.getText()));
                    createNetwork();
                });
                hiddenTextField.setText("1");
                final VBox hiddenLayer = new VBox(new Text("Hidden layer " + (i + 1) + " size:"), hiddenTextField);
                hiddenBox.getChildren().add(hiddenLayer);
            }
            createNetwork();
        });
        nbrHiddenCombo.getSelectionModel().selectFirst();

        outputTextField.textProperty().addListener((observable, oldValue, newValue) -> createNetwork());
        activationFunctionCombo.valueProperty().addListener((observable, oldValue, newValue) -> createNetwork());
        minWeightTextField.textProperty().addListener((observable, oldValue, newValue) -> createNetwork());
        maxWeightTextField.textProperty().addListener((observable, oldValue, newValue) -> createNetwork());
        biasTextField.textProperty().addListener((observable, oldValue, newValue) -> createNetwork());
        learningRateTextField.textProperty().addListener((observable, oldValue, newValue) -> createNetwork());
        
        inputTextField.setOnKeyTyped(k -> {
            if (k.getCharacter().matches("[^0-9]")) {
                k.consume();
            }
        });
    }

    private void createNetwork() {
        final int input = Integer.valueOf(inputTextField.getText());
        final int output = Integer.valueOf(outputTextField.getText());
        final int min = Integer.valueOf(minWeightTextField.getText());
        final int max = Integer.valueOf(maxWeightTextField.getText());
        final int bias = Integer.valueOf(biasTextField.getText());
        final int rate = Integer.valueOf(learningRateTextField.getText());

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
