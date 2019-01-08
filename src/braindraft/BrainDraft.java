package braindraft;

import braindraft.window.Window;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author flo
 */
public final class BrainDraft extends Application {

    @Override
    public void start(final Stage stage) {
        stage.setTitle("BrainDraft");
        stage.getIcons().add(new Image("brain.png"));
        stage.setMaximized(true);

        new Window(stage);

        stage.show();
    }
}
