package braindraft.window;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Separator;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 *
 * @author flo
 */
public final class MyMenu extends VBox {

    private final MenuItem newItem = new MenuItem("New Network");
    private final MenuItem openItem = new MenuItem("Open Network");
    private final MenuItem saveItem = new MenuItem("Save Network");
    private final MenuItem closeItem = new MenuItem("Close Network");
    private final MenuItem quitItem = new MenuItem("Quit");

    private final MenuItem trainDataItem = new MenuItem("Training Datas");
    private final MenuItem testDataItem = new MenuItem("Testing Datas");

    private final MenuItem trainItem = new MenuItem("Train");
    private final MenuItem continueItem = new MenuItem("Continue");
    private final MenuItem pauseItem = new MenuItem("Pause");
    private final MenuItem stopItem = new MenuItem("Stop");
    private final MenuItem testItem = new MenuItem("Test");
    private final MenuItem predictItem = new MenuItem("Predict");

    private final MenuItem aboutItem = new MenuItem("?");

    private final MenuBar menuBar = new MenuBar();
    private final ToolBar toolBar = new ToolBar();

    public MyMenu(final SimpleObjectProperty<?> trainerProperty,
            final SimpleObjectProperty<?> networkProperty,
            final SimpleBooleanProperty modifiedProperty,
            final SimpleBooleanProperty runningProperty,
            final SimpleBooleanProperty pausedProperty) {
        super();
        this.getChildren().addAll(menuBar, toolBar);

        final Button newBtn = new Button("", new ImageView("new.png"));
        final Button openBtn = new Button("", new ImageView("open.png"));
        final Button saveBtn = new Button("", new ImageView("save.png"));
        final Button trainDataBtn = new Button("", new ImageView("trainData.png"));
        final Button trainBtn = new Button("", new ImageView("train.png"));
        final Button continueBtn = new Button("", new ImageView("continue.png"));
        final Button pauseBtn = new Button("", new ImageView("pause.png"));
        final Button stopBtn = new Button("", new ImageView("stop.png"));
        final Button predictBtn = new Button("", new ImageView("predict.png"));
        final Button testDataBtn = new Button("", new ImageView("testData.png"));
        final Button testBtn = new Button("", new ImageView("test.png"));

        final class MyTooltip extends Tooltip {

            MyTooltip(final String text) {
                super(text);
                setFont(Font.font("Comic Sans MS"));
                setStyle("-fx-background-color:rgba(0, 128, 255, 0.7)");
            }
        }

        newBtn.setTooltip(new MyTooltip("Create a new network."));
        openBtn.setTooltip(new MyTooltip("Open an existing network."));
        saveBtn.setTooltip(new MyTooltip("Save the current network."));
        trainDataBtn.setTooltip(new MyTooltip("Open datas for training."));
        trainBtn.setTooltip(new MyTooltip("Train the network."));
        continueBtn.setTooltip(new MyTooltip("Continue training the network."));
        pauseBtn.setTooltip(new MyTooltip("Pause training the network."));
        stopBtn.setTooltip(new MyTooltip("Stop training the network."));
        testDataBtn.setTooltip(new MyTooltip("Open datas for testing."));
        testBtn.setTooltip(new MyTooltip("Test the network."));
        predictBtn.setTooltip(new MyTooltip("Ask the network a prediction."));

        final class MySeparator extends Separator {

            MySeparator() {
                super();
                setPadding(new Insets(0, 15, 0, 15));
            }
        }

        menuBar.getMenus().addAll(
                new Menu("File", null, newItem, new SeparatorMenuItem(), openItem, closeItem,
                        new SeparatorMenuItem(), saveItem, new SeparatorMenuItem(), quitItem),
                new Menu("Network", null, trainItem, continueItem, pauseItem, stopItem,
                        new SeparatorMenuItem(), trainDataItem, testDataItem,
                        new SeparatorMenuItem(), testItem, new SeparatorMenuItem(), predictItem),
                new Menu("About", null, aboutItem));
        toolBar.getItems().addAll(newBtn, openBtn, saveBtn, new MySeparator(), trainDataBtn, trainBtn, continueBtn,
                pauseBtn, stopBtn, new MySeparator(), testDataBtn, testBtn, new MySeparator(), predictBtn);

        saveItem.disableProperty().bind(modifiedProperty.not().or(runningProperty));
        closeItem.disableProperty().bind(networkProperty.isNull().or(runningProperty));
        trainDataItem.disableProperty().bind(trainerProperty.isNull().or(runningProperty));
        trainItem.disableProperty().bind(trainerProperty.isNull().or(runningProperty));
        continueItem.disableProperty().bind(pausedProperty.not());
        pauseItem.disableProperty().bind(runningProperty.not().and(pausedProperty.not()));
        stopItem.disableProperty().bind(runningProperty.not().and(pausedProperty.not()));
        testDataItem.disableProperty().bind(trainerProperty.isNull().or(runningProperty));
        testItem.disableProperty().bind(trainerProperty.isNull().or(runningProperty));
        predictItem.disableProperty().bind(trainerProperty.isNull().or(runningProperty));

        saveBtn.disableProperty().bind(saveItem.disableProperty());
        trainDataBtn.disableProperty().bind(trainDataItem.disableProperty());
        trainBtn.disableProperty().bind(trainItem.disableProperty());
        continueBtn.disableProperty().bind(continueItem.disableProperty());
        pauseBtn.disableProperty().bind(pauseItem.disableProperty());
        stopBtn.disableProperty().bind(stopItem.disableProperty());
        testDataBtn.disableProperty().bind(testDataItem.disableProperty());
        testBtn.disableProperty().bind(testItem.disableProperty());
        predictBtn.disableProperty().bind(predictItem.disableProperty());

        newBtn.onActionProperty().bind(newItem.onActionProperty());
        openBtn.onActionProperty().bind(openItem.onActionProperty());
        saveBtn.onActionProperty().bind(saveItem.onActionProperty());
        trainDataBtn.onActionProperty().bind(trainDataItem.onActionProperty());
        trainBtn.onActionProperty().bind(trainItem.onActionProperty());
        continueBtn.onActionProperty().bind(continueItem.onActionProperty());
        pauseBtn.onActionProperty().bind(pauseItem.onActionProperty());
        stopBtn.onActionProperty().bind(stopItem.onActionProperty());
        testDataBtn.onActionProperty().bind(testDataItem.onActionProperty());
        testBtn.onActionProperty().bind(testItem.onActionProperty());
        predictBtn.onActionProperty().bind(predictItem.onActionProperty());

        newItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCodeCombination.CONTROL_DOWN));
        openItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCodeCombination.CONTROL_DOWN));
        saveItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCodeCombination.CONTROL_DOWN));
        quitItem.setAccelerator(new KeyCodeCombination(KeyCode.ESCAPE));
        trainDataItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCodeCombination.CONTROL_DOWN));
        trainItem.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCodeCombination.CONTROL_DOWN));
        testDataItem.setAccelerator(new KeyCodeCombination(KeyCode.D, KeyCodeCombination.ALT_DOWN));
        testItem.setAccelerator(new KeyCodeCombination(KeyCode.T, KeyCodeCombination.CONTROL_DOWN, KeyCodeCombination.SHIFT_DOWN));
        predictItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCodeCombination.CONTROL_DOWN));
        aboutItem.setAccelerator(new KeyCodeCombination(KeyCode.F1));
    }

    public ObjectProperty<EventHandler<ActionEvent>> getNewItemActionProperty() {
        return newItem.onActionProperty();
    }

    public void setNewItemAction(final EventHandler<ActionEvent> event) {
        newItem.setOnAction(event);
    }

    public ObjectProperty<EventHandler<ActionEvent>> getOpenItemActionProperty() {
        return openItem.onActionProperty();
    }

    public void setOpenItemAction(final EventHandler<ActionEvent> event) {
        openItem.setOnAction(event);
    }

    public void setSaveItemAction(final EventHandler<ActionEvent> event) {
        saveItem.setOnAction(event);
    }

    public void setCloseItemAction(final EventHandler<ActionEvent> event) {
        closeItem.setOnAction(event);
    }

    public void setQuitItemAction(final EventHandler<ActionEvent> event) {
        quitItem.setOnAction(event);
    }

    public void setTrainDataItemAction(final EventHandler<ActionEvent> event) {
        trainDataItem.setOnAction(event);
    }

    public void setTrainItemAction(final EventHandler<ActionEvent> event) {
        trainItem.setOnAction(event);
    }

    public void setContinueItemAction(final EventHandler<ActionEvent> event) {
        continueItem.setOnAction(event);
    }

    public void setPauseItemAction(final EventHandler<ActionEvent> event) {
        pauseItem.setOnAction(event);
    }

    public void setStopItemAction(final EventHandler<ActionEvent> event) {
        stopItem.setOnAction(event);
    }

    public void setTestDataItemAction(final EventHandler<ActionEvent> event) {
        testDataItem.setOnAction(event);
    }

    public void setTestItemAction(final EventHandler<ActionEvent> event) {
        testItem.setOnAction(event);
    }

    public void setPredictItemAction(final EventHandler<ActionEvent> event) {
        predictItem.setOnAction(event);
    }

    public void setAboutItemAction(final EventHandler<ActionEvent> event) {
        aboutItem.setOnAction(event);
    }
}
