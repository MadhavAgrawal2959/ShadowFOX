import javafx.application.Application;
import javafx.stage.Stage;

public class CheckJavaFXVersion extends Application {
    @Override
    public void start(Stage primaryStage) {
        String version = System.getProperty("javafx.version");
        System.out.println("JavaFX Version: " + version);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
