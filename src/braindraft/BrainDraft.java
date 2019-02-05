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

    private static Window window;

    @Override
    public void start(final Stage stage) {
        stage.setTitle("BrainDraft");
        stage.getIcons().add(new Image("brain.png"));
        stage.setMaximized(true);

        window = new Window(stage);

        stage.show();
    }

    @Override
    public void stop() {
        window.stopRunningThreah();
    }
}
