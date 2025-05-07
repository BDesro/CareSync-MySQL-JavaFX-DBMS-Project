package util;

import Model.Staff;
import javafx.scene.image.Image;

import java.util.Objects;

public class AppGlobals
{
    public static Staff activeUser;
    public static final Image CARESYNCLOGO = new Image(Objects.requireNonNull(AppGlobals.class.getResourceAsStream("/CareSyncLogo.png")));
}
