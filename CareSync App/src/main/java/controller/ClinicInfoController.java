package controller;

import core.SceneID;
import core.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import util.AppGlobals;

public class ClinicInfoController
{
    @FXML private Label clinicName;
    @FXML private Label clinicAddress;
    @FXML private Label clinicPhone;

    @FXML
    public void initialize()
    {
        clinicName.setText(AppGlobals.currentClinic.getClinicName());
        clinicAddress.setText(AppGlobals.currentClinic.getClinicAddress());
        clinicPhone.setText(AppGlobals.currentClinic.getClinicPhone());
    }

    public void backToDash(ActionEvent event)
    {
        SceneManager.INSTANCE.switchTo(SceneID.DASH);
    }
}
