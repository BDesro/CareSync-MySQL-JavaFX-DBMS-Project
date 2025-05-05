package core;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public enum SceneManager
{
    INSTANCE;

    private static Stage stage;
    private static int resX = 1280;
    private static int resY = 720;

    public void setStage(Stage s)
    {
        stage = s;
    }

    public void switchTo(SceneID id)
    {
        Scene scene = null;

        try
        {
            switch(id)
            {
                case LOGIN_SCREEN:
                    // scene = new Scene()
            }

            if(scene != null)
            {
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private Parent loadFXML(String path) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
        return loader.load();
    }
}
