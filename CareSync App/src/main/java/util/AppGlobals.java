package util;

import Model.Clinic;
import Model.Staff;
import javafx.scene.image.Image;

import java.util.Objects;

public class AppGlobals
{
    public static Staff activeUser;
    public static Clinic currentClinic;
    public static final Image CARESYNCLOGO = new Image(Objects.requireNonNull(AppGlobals.class.getResourceAsStream("/CareSyncLogo.png")));

    public static void setActiveUser(Staff user)
    {
        activeUser = user;
        currentClinic = CareSyncDB.getClinicByID(user.getClinicID());
    }
}
