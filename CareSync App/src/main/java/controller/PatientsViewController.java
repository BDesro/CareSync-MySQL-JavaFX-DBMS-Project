package controller;

import core.SceneID;
import core.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.AppGlobals;

public class PatientsViewController
{
    @FXML private Label userLastFirstName;

    @FXML
    public void initialize()
    {
        userLastFirstName.setText(AppGlobals.activeUser.getLastName() + ", " + AppGlobals.activeUser.getFirstName());
    }

    public void onBackClick(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.RECEPTIONIST_DASH);
    }
}
