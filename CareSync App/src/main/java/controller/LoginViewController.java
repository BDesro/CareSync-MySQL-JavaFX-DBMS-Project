package controller;

import Model.Staff;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;

public class LoginViewController
{
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label messageLabel;

    public void checkLogin(ActionEvent e)
    {
        if(emailField.getText() == null || emailField.getText().trim().equals("") ||
           passwordField.getText() == null || passwordField.getText().trim().equals(""))
        {
            Staff user ;
        }
    }

    private void authenticateStaff(String email, String pass)
    {

    }
}
