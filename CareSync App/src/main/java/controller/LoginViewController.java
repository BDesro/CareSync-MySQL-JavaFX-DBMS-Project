package controller;

import Model.Staff;
import core.SceneID;
import core.SceneManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import org.mindrot.jbcrypt.BCrypt;
import util.AppGlobals;
import util.CareSyncDB;

public class LoginViewController
{
    @FXML private ImageView logo;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    private Staff user = null;

    @FXML
    public void initialize()
    {
        logo.setImage(AppGlobals.CARESYNCLOGO);
    }

    // For testing, all passwords are '1234'
    public void checkLogin(ActionEvent e)
    {
        messageLabel.setVisible(true);
        messageLabel.setStyle("-fx-text-fill: red;");

        if(!emailField.getText().trim().isEmpty() && !passwordField.getText().trim().isEmpty())
        {
            user = CareSyncDB.getStaffByEmail(emailField.getText());

            if(user != null)
            {
                if(authenticateUser(passwordField.getText()))
                {
                    messageLabel.setStyle("-fx-text-fill: green;");
                    messageLabel.setText("Login Successful");

                    switch(user.getStaffRole().toLowerCase())
                    {
                        case "receptionist":
                            SceneManager.INSTANCE.switchTo(SceneID.RECEPTIONIST_DASH);
                            break;
                        default:
                            break;
                    }
                }
            }
            else
            {
                messageLabel.setText("Login Failed: No Account Found with Email Entered");
            }
        }
        else
        {
            messageLabel.setText("Login Failed: Please enter a valid email address and password");
        }
    }

    private boolean authenticateUser(String pass)
    {
        if(user == null)
            return false;

        if(BCrypt.checkpw(pass, user.getHashedPassword()))
        {
            AppGlobals.setActiveUser(user);
            return true;
        }
        else
        {
            messageLabel.setText("Login Failed: Incorrect password");
            return false;
        }
    }
}
