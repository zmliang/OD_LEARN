package com.pos.od;

import android.content.Context;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import kotlin.io.encoding.Base64;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    //公钥
    private String pub_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhq2atk9qMIoQaN9k8DkMvxNl6ZexVnLv7x76wLtdgI+DOKiIQao92nF9GxNUCPBA2GXEF0Kj6uzl9zkoi8ZmGKNab/yCr6D3FL/l8tDy6hJsafvzQSxvfbXbAZ9Bj9qqp/LtlG+MLmSNoMN+khyqvFdCf3FOD1x/7tfD0nzvfussSyOhbnDb3Wu3faAj8LpUU/YAS96dktCjOQ4T6fcp9tUF82HvAo8JuzcJJYiuHtXvptNMm8zUY1J5XQpgggWJoU5LfpHphQ6O3T6ag6XTFBQyZX6S/VacIvfETV3yWdOOFSDoHc3PFvHbwFMfFtfti4aeUSU1LHWucJQ9duOzaQIDAQAB";

    //私钥
    private String pri_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCGrZq2T2owihBo32TwOQy/E2Xpl7FWcu/vHvrAu12Aj4M4qIhBqj3acX0bE1QI8EDYZcQXQqPq7OX3OSiLxmYYo1pv/IKvoPcUv+Xy0PLqEmxp+/NBLG99tdsBn0GP2qqn8u2Ub4wuZI2gw36SHKq8V0J/cU4PXH/u18PSfO9+6yxLI6FucNvda7d9oCPwulRT9gBL3p2S0KM5DhPp9yn21QXzYe8Cjwm7NwkliK4e1e+m00ybzNRjUnldCmCCBYmhTkt+kemFDo7dPpqDpdMUFDJlfpL9Vpwi98RNXfJZ044VIOgdzc8W8dvAUx8W1+2Lhp5RJTUsda5wlD1247NpAgMBAAECggEAcoj7tSXuoh8Ye4fYuO9bprhhiHoISAPxh0OqbvQpPadPKoUxc97LQZYYlkkmw39HNoG9dSaobqdPn6ONlumAdF2gEPMHVUOoPY5BifInU+edKbkqk/etTZS2DzgYMrX9Ql23rO/8oIvFYr4/SxV0Ij53gBilES1SY5Y9pCm6KA2sDDIZI2MdJc1pM+LumX9flXUU9EZHCtGe4cassKH8nK0QSJfsUGppiPHuWf/PTSn544wt3XgBT/iaJ9rzITqmK6Zcr0znIqgbEmVB/6HUgc96/YDeRP4hXxp7TQQuNqy/2+PEmGm7EKMuvqElyRV1fsKz9toIR/CuD0FNLlVCnQKBgQDTwtPuCP35DAkd1Ko7Zv2lGUX5w3mtcQrQV1mOqB5kzNO1OcslsM5nIqjnOhEab7C9ByyXX912nMQnkWlPrxPMJFK70fcFG4N0jCQoNj5lKpgPLINi/O3V+d9OgrRoN8f7kTvcftlo0MdjgmYrPoW2iB5i910hO4ORpA2cjo1tAwKBgQCi0FAnaaxfILAraK7nrVkU04NMzCDs8sugVQJnmQWbhiU/cnHNe9mfRyvfyakhk4jWRpIxZxooQGdecDTSwqYVl8kyFeHb9VJJXxjfssDC2ubUGc7cI+dF0ThFQN6mN1y8VUHnNcdnFdvDmlFNU/V1N6+GCs1XTyuLoK/kOzNEIwKBgFGhBa75emV5dT4xj1mMrvPolSfdaVcpf0Cmm1g5iFXU+zsdxyiFSltJahCQOq2VJm0HifHkO2uQrLCQBid4VspXqijKnE00uOzBZOK3XkADNlCLGS6cT1YvlxwdmEugTQ+1gXzSzR3xdRrv1lCPLl4ZhJUjw5ue5G3M7o5OCUhfAoGAIF95QIzzeCrAvLDg7jH6n3h7B/8ioNqaAHRGuSClC8p9h4MS+7+fgtsc7y9i0MlMEQcRNH+CpPRBWXbIGgwYB/0ZRTsyoJDD3tDo2jnpuBTchtnm1sDQ0IIMRUDjxHsHlm8BXp7D2kxvWRJLDiweh0MeRHHLaIvWZbn6n0/iHq8CgYBdsEq2oYyXZ6XtK7UbfbTw67rzgoCyHoeAaKK8E2w2TkplhA6ZZIli5+O+wT+7VFYOYxcMEUTo917ZiZ2G+sDxRmxY407Q2f+NUfruicEgIwHt2tDq4nVapwL+adzmELCWmonrSOp4FJfsLvHjIhrjtqZPmBMt+gXacY6ITOKboA==";


    private String encryptTxt = "/h6c3KZEWw51prSRwCIYa+o1H5Gic466Jm/o0eHnFvcgvALjlIhfh/RHkQ1tsforUWem++L7q+rDsEJxG+G4RyiqvD3XEK1MWbVYzOp9vXMXxG2OJY/OHG/lBrRnoxEFSu4R/0tIkwfePrlc/zGiigm9e7cUs+GHkU2Ve1X1oNwvIfUlQhGvKPqC2mn8ONrdp9KoGSNxjd8fPX0a8x2uyxe5VKD8A/XKR1EmY9A165ZPG58XHpgACvHblRiiuEBx8qmB30dchuK4bN/PcECKVjkuWenNCPltxp5ak4pGxjsMJIZ/lE39pNp8ZZP2NPE/UtOEWhlwZd1C+qcTZuHKiJw6gqLyqIRkLQtnhfmXoU6DLA+Tkh1clX6xdSeA+lziqQmoTE3l0XKONi1XQ5gk66sLyTBPPD+b0ilNMugdHAT5zxxdgbm3ya4cFWQ0P7sR5SHa0bYzk8OkzohrlxUhJPauu84j4HWx/1TiHPTiGuPm1rTeMey/t+t8sVi8drp5qwvJME88P5vSKU0y6B0cBPnPHF2BubfJrhwVZDQ/uxGzu1AseTtzoP49/BhPpOkR9q67ziPgdbH/VOIc9OIa40mFIPur9TRLapwiJFRWlfKrC8kwTzw/m9IpTTLoHRwE+c8cXYG5t8muHBVkND+7EYt8nPkgFDYVNPAWk9GHYt5G9DtLW1ZDNXNRinSzSBNSJyYRK2yrTHJrMlgMbnuW7NBzuk1XvszBaKFiKZDebsOHDaZmFQJE8r4gANl6hGW6UtOEWhlwZd1C+qcTZuHKiIzoMcUi32az45iSjzORCQ5OzZWOecId2ffCRcECxZLYo6n1TqOGHJIQ188SS8rGp/ScSNX7Iy57ho/yM4oKX57cYwqF3/38nCM3zd6o8Df9q6Fvi0MN1uuNKP96nt35r1FTdz4E+1QPopf/MpvE4MAX2dPVRq+p5QTDFrBMpAVk4PBEbXyT6lkn1WVtuh+04abXKhntpooVl3JEcKN59ciK4b9l/NPoLxTzAKlScL4de+Un5RVASTp1/palOnFxVo11uil3WApqxu68Iy2zPN+iWTpX2sB7KPdL90fgs5JlEpkk3ImCsdgqaxPgGBu9y4R14uEmHk6lIYA3lZ8prFkV3o1MrO6rpG3U4yFork9hCb17txSz4YeRTZV7VfWg3BoPUNojN4P9zyEVpxUrvLMSmSTciYKx2CprE+AYG73LcVPM+1JuNmJxAvxROnt+Hg2KIWNCvbhoHoc9Tm4GUq0JvXu3FLPhh5FNlXtV9aDczQtB+G891z91egIlIQ6Q5xKZJNyJgrHYKmsT4BgbvctpSH9REJfxqgC87vgDC/PA5gMPXeCxgf3EO5khrx38UYbpNKFG9TVLPsAmypvhJO5NgCo1Jc6hU0O988jyddaajFssnUrMyhRc7iuGlitkBkLqWgVMdiwyO3AhoHS31zdgvSWrRFrAT/ps5VYPCY9YqNnglVcornq8w2pq50mATAZu1N/GcBXCieuUZsoZEAnzqaOnFTTpOOYL5KCen2NHOpYgXaINqm59JPg6DzTgM4o1fc7XTkJ7c/aShcprcmmaJAHqQbWhhyMrsa+oAii9Pj2aDUUCrTIbgoR6PTePkqooLjJJGRyBIOXXnBjObo4ZO90skF56fM/Trgm90xtF";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.example.od", appContext.getPackageName());
    }

    // 加密方法
    public static String encrypt(String plainText, String publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, stringToPublicKey(publicKey));

        // 计算最大加密块长度
        int maxEncryptBlock = 245; // 2048 位密钥使用 PKCS1Padding 时的最大加密块长度
        byte[] plainBytes = plainText.getBytes("UTF-8");
        int inputLen = plainBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 分块加密
        while (inputLen - offset > 0) {
            if (inputLen - offset > maxEncryptBlock) {
                cache = cipher.doFinal(plainBytes, offset, maxEncryptBlock);
            } else {
                cache = cipher.doFinal(plainBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * maxEncryptBlock;
        }
        byte[] encryptedBytes = out.toByteArray();
        out.close();
        return Base64.Default.encode(encryptedBytes,0,encryptedBytes.length);

    }

    // 解密方法
    public static String decrypt(String encryptedText, String privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, stringToPrivateKey(privateKey));

        // 计算最大解密块长度
        int maxDecryptBlock = 256; // 2048 位密钥使用 PKCS1Padding 时的最大解密块长度
        byte[] encryptedBytes = Base64.Default.decode(encryptedText,0,encryptedText.length());
        int inputLen = encryptedBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offset = 0;
        byte[] cache;
        int i = 0;
        // 分块解密
        while (inputLen - offset > 0) {
            if (inputLen - offset > maxDecryptBlock) {
                cache = cipher.doFinal(encryptedBytes, offset, maxDecryptBlock);
            } else {
                cache = cipher.doFinal(encryptedBytes, offset, inputLen - offset);
            }
            out.write(cache, 0, cache.length);
            i++;
            offset = i * maxDecryptBlock;
        }
        byte[] decryptedBytes = out.toByteArray();
        out.close();
        return new String(decryptedBytes, "UTF-8");

    }

    // 将 Base64 编码的公钥字符串转换为 PublicKey 对象
    public static PublicKey stringToPublicKey(String publicKeyStr) throws Exception {
        byte[] keyBytes = Base64.Default.decode(publicKeyStr,0,publicKeyStr.length());
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePublic(spec);
    }

    // 将 Base64 编码的私钥字符串转换为 PrivateKey 对象
    public static PrivateKey stringToPrivateKey(String privateKeyStr) throws Exception {
        byte[] keyBytes = Base64.Default.decode(privateKeyStr, 0, privateKeyStr.length());
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(spec);
    }

}