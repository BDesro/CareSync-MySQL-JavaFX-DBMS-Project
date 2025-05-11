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
                    scene = new Scene(loadFXML("/view/login-view.fxml"), 600, 500);
                    stage.setTitle("Login Page");
                    break;
                case DASH:
                    scene = new Scene(loadFXML("/view/staff-dashboard-view.fxml"), 700, 720);
                    stage.setTitle("Receptionist Dashboard");
                    break;
                case PATIENTS_SCREEN:
                    scene = new Scene(loadFXML("/view/patients-view.fxml"), resX, resY);
                    stage.setTitle("Patients");
                    break;
                case VISITS_SCREEN:
                    scene = new Scene(loadFXML("/view/visits-view.fxml"), resX, resY);
                    stage.setTitle("Visits");
                    break;
                case CLINIC_INFO_SCREEN:
                    scene = new Scene(loadFXML("/view/clinic-info-view.fxml"), 700, 720);
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

    private static Parent loadFXML(String path) throws Exception
    {
        FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(path));
        return loader.load();
    }
}
