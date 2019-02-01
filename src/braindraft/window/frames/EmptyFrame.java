package braindraft.window.frames;

import javafx.beans.property.ObjectProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

/**
 *
 * @author flo
 */
public final class EmptyFrame extends VBox {

    private static final Background BACKGROUND = new Background(new BackgroundFill(
            Color.CADETBLUE, CornerRadii.EMPTY, Insets.EMPTY));

    private final Button openBtn = new Button("Open Network");
    private final Button createBtn = new Button("Create Network");

    public EmptyFrame(final ObjectProperty<EventHandler<ActionEvent>> openItemActionProperty,
            final ObjectProperty<EventHandler<ActionEvent>> newItemActionProperty) {
        super(20);

        getChildren().addAll(openBtn, createBtn);
        setBackground(BACKGROUND);

        final Rectangle rectangle = new Rectangle(100, 50);
        rectangle.setArcWidth(10);
        rectangle.setArcHeight(10);
        openBtn.setShape(rectangle);
        createBtn.setShape(rectangle);
        openBtn.setPrefSize(150, 40);
        createBtn.setPrefSize(150, 40);
        openBtn.setFont(Font.font("Comic Sans MS", 16));
        createBtn.setFont(Font.font("Comic Sans MS", 16));
        openBtn.setTextFill(Color.DIMGRAY);
        createBtn.setTextFill(Color.DIMGRAY);

        openBtn.onActionProperty().bind(openItemActionProperty);
        createBtn.onActionProperty().bind(newItemActionProperty);

        setAlignment(Pos.CENTER);
    }
}
