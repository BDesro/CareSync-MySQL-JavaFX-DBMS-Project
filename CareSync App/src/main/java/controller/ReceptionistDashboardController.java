package controller;

import core.SceneID;
import core.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import util.AppGlobals;

public class ReceptionistDashboardController
{
    // Register New Patients

    // Schedule appointments and assign Doctors/Rooms

    // Generate invoices for patient visits

    // Limited access to recent_visits (no diagnoses or treatments)

    @FXML private Label userLastFirstName;
    @FXML private ImageView logo;

    @FXML
    public void initialize()
    {
        userLastFirstName.setText(AppGlobals.activeUser.getLastName() + ", " + AppGlobals.activeUser.getFirstName());
        logo.setImage(AppGlobals.CARESYNCLOGO);
    }

    public void onPatientsClick(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.PATIENTS_SCREEN);
    }

    public void onVisitsClick(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.VISITS_SCREEN);
    }

    public void logout(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.LOGIN_SCREEN);
    }
}
