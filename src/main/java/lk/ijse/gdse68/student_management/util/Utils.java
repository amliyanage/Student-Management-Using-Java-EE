package lk.ijse.gdse68.student_management.util;

import java.util.UUID;

public class Utils {
    public static String generateId(){
        return UUID.randomUUID().toString();
    }
}
