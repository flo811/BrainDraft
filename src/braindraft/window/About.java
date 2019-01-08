package braindraft.window;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author flo
 */
final class About {

    private final Stage stage = new Stage();

    private final ImageView image = new ImageView("brain.png");
    private final Text text = new Text("BrainDraft\n\nVersion : 1.0\nAuthor : flo\nMail : flo811@msn.com");
    private final Button okBtn = new Button("OK !");

    private final VBox vBox = new VBox(20, text, okBtn);
    private final HBox root = new HBox(10, image, vBox);
    private final Scene scene = new Scene(root);

    About() {
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        scene.setFill(Color.TRANSPARENT);

        root.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(30), Insets.EMPTY)));
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10, 10, 10, 10));

        image.setPreserveRatio(true);
        image.setFitHeight(120);
        okBtn.setPrefSize(50, 30);
        okBtn.setFont(Font.font("Comic Sans MS", 12));
        text.setFont(Font.font("Comic Sans MS", 15));

        okBtn.setDefaultButton(true);
        okBtn.setCancelButton(true);

        stage.show();

        okBtn.setOnAction(e -> stage.close());
    }
}
