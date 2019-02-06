package braindraft.window.graphicalnetwork;

import braindraft.model.ActivationFunctions;
import braindraft.model.network.VirtualNeuron;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 *
 * @author flo
 */
public class NeuronParametersWindow extends ComponentParametersWindow {

    private final Text activationFunctionTxt = new Text("Activation Function :");
    private final ComboBox<ActivationFunctions> activationFunctionCombo = new ComboBox<>(
            FXCollections.observableArrayList(Arrays.stream(ActivationFunctions.values()).collect(Collectors.toList())
            ));
    private final HBox activationFunctionVBox = new HBox(ELEMENT_SPACING, activationFunctionTxt, activationFunctionCombo);

    private final Text learningRateTxt = new Text("Learning Rate :");
    private final TextField learningRateTF = new TextField();
    private final HBox learningRateVBox = new HBox(ELEMENT_SPACING, learningRateTxt, learningRateTF);

    private final Text biasTxt = new Text("Bias :");
    private final TextField biasTF = new TextField();
    private final HBox biasVBox = new HBox(ELEMENT_SPACING, biasTxt, biasTF);

    private final Text biasWeightTxt = new Text("Bias Weight :");
    private final TextField biasWeightTF = new TextField();
    private final HBox biasWeightVBox = new HBox(ELEMENT_SPACING, biasWeightTxt, biasWeightTF);

    private final List<TextField> weightsTF = new ArrayList<>();
    private final VBox weightsVBox = new VBox(ELEMENT_SPACING);

    private final VirtualNeuron neuron;

    public NeuronParametersWindow(final VirtualNeuron neuron) {
        super(neuron);

        this.neuron = neuron;
        vBox.getChildren().clear();
        vBox.getChildren().addAll(valueVBox, activationFunctionVBox, learningRateVBox, biasVBox, biasWeightVBox, weightsVBox, validateButton);

        activationFunctionVBox.setAlignment(Pos.CENTER_LEFT);
        learningRateVBox.setAlignment(Pos.CENTER_LEFT);
        biasVBox.setAlignment(Pos.CENTER_LEFT);
        biasWeightVBox.setAlignment(Pos.CENTER_LEFT);

        activationFunctionCombo.setValue(neuron.getActivationFunction());
        learningRateTF.setText(GraphicalComponent.formatValue(neuron.getLearningRate()));
        biasTF.setText(GraphicalComponent.formatValue(neuron.getBias()));
        biasWeightTF.setText(GraphicalComponent.formatValue(neuron.getBiasWeight()));
        neuron.getWeights().forEach(entry -> {
            final TextField tf = new TextField(GraphicalComponent.formatValue(entry.getValue()));
            weightsTF.add(tf);
            final HBox hBox = new HBox(ELEMENT_SPACING, new Text("Weight with " + entry.getKey().getName() + " :"), tf);
            hBox.setAlignment(Pos.CENTER_LEFT);
            weightsVBox.getChildren().add(hBox);
        });

        learningRateTF.setOnKeyTyped(getOnlyNumValuesHandler());
        biasTF.setOnKeyTyped(getOnlyNumValuesHandler());
        biasWeightTF.setOnKeyTyped(getOnlyNumValuesHandler());

        learningRateTF.textProperty().addListener(getChangeListener(learningRateTF));
        biasTF.textProperty().addListener(getChangeListener(biasTF));
        biasWeightTF.textProperty().addListener(getChangeListener(biasWeightTF));

        validateButton.setOnAction(e -> {
            neuron.setActivationFunction(activationFunctionCombo.getValue());
            neuron.setLearningRate(Double.valueOf(learningRateTF.getText()));
            neuron.setBias(Double.valueOf(biasTF.getText()));
            neuron.setBiasWeight(Double.valueOf(biasWeightTF.getText()));
            stage.close();
        });
    }
}
