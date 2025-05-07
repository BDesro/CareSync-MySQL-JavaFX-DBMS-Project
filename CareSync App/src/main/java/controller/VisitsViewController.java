package controller;

import core.SceneID;
import core.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.AppGlobals;

public class VisitsViewController
{
    @FXML Label lastFirstLabel;

    @FXML
    public void initialize()
    {
        lastFirstLabel.setText(AppGlobals.activeUser.getLastName() + ", " + AppGlobals.activeUser.getFirstName());
    }

    public void onBackClick(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.RECEPTIONIST_DASH);
    }
}
