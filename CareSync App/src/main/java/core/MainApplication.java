package core;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApplication extends Application
{
    public void start(Stage stage) throws Exception
    {
        SceneManager.INSTANCE.setStage(stage);
        SceneManager.INSTANCE.switchTo(SceneID.LOGIN_SCREEN);
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
