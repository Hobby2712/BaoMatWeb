package Util;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class KeyGenerator2 {
    private static SecretKey secretKey;

    
    private static final int KEY_CHANGE_INTERVAL = 60; // Thay đổi key sau mỗi 60 giây

    static {
        try {
            secretKey = generateSecretKey();
            startKeyChangeTask();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static SecretKey generateSecretKey() throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        return keyGenerator.generateKey();
    }

    private static void startKeyChangeTask() {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            try {
                SecretKey newSecretKey = generateSecretKey();
                secretKey = newSecretKey;
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }, KEY_CHANGE_INTERVAL, KEY_CHANGE_INTERVAL, TimeUnit.SECONDS);
    }

    public static SecretKey getSecretKey() {
        return secretKey;
    }
}