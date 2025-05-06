package controller;

import core.SceneID;
import core.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import util.AppGlobals;

public class ReceptionistDashboardController
{
    // Register New Patients

    // Schedule appointments and assign Doctors/Rooms

    // Generate invoices for patient visits

    // Limited access to recent_visits (no diagnoses or treatments)

    @FXML private Label userLastFirstName;
    @FXML private Button logoutButton;
    @FXML private Button patientsButton;

    @FXML
    public void initialize()
    {
        userLastFirstName.setText(AppGlobals.activeUser.getLastName() + ", " + AppGlobals.activeUser.getFirstName());
    }

    public void onPatientsClick(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.PATIENTS_SCREEN);
    }

    public void logout(ActionEvent e)
    {
        SceneManager.INSTANCE.switchTo(SceneID.LOGIN_SCREEN);
    }
}
