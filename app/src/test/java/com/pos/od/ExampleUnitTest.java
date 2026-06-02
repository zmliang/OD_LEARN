package com.pos.od;

import static com.pos.od.Base64Coder.CHARSET_UTF8;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.junit.Test;

import static org.junit.Assert.*;

import android.net.Uri;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import kotlin.io.encoding.Base64;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */



public class ExampleUnitTest {


    static {
        // 注册 Bouncy Castle 提供者
        Security.addProvider(new BouncyCastleProvider());
    }

    // AES 密钥，长度可以是 128、192 或 256 位 0123456789abcdef0123456789abcdef
    private static final String AES_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8A";



    private String plainText = "{\n" +
            "    \"special_list\": [],\n" +
            "    \"test_list\": [],\n" +
            "    \"links\": [\n" +
            "    {\n" +
            "        \"hostName\": \"8.212.104.65\",\n" +
            "        \"hostFileName\": \"tunnel1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"8.148.235.250\",\n" +
            "        \"hostFileName\": \"tunnel2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"47.122.51.248\",\n" +
            "        \"hostFileName\": \"tunnel3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"cchain.ink\",\n" +
            "        \"hostFileName\": \"cchain\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"ccline.vip\",\n" +
            "        \"hostFileName\": \"ccline\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"emxg.cn\",\n" +
            "        \"hostFileName\": \"cn\"\n" +
            "    }],\n" +
            "    \"ws_links\": [\n" +
            "    {\n" +
            "        \"hostName\": \"8.212.104.65\",\n" +
            "        \"hostFileName\": \"tunnel1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"8.148.235.250\",\n" +
            "        \"hostFileName\": \"tunnel2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"47.122.51.248\",\n" +
            "        \"hostFileName\": \"tunnel3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"cchain.ink\",\n" +
            "        \"hostFileName\": \"cchain\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"ccline.vip\",\n" +
            "        \"hostFileName\": \"ccline\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"emxg.cn\",\n" +
            "        \"hostFileName\": \"cn\"\n" +
            "    }],\n" +
            "    \"ipPortList\": [ 8001, 8002, 8003, 8004, 8005, 8006 ],\n" +
            "    \"domainPort\":  443,\n" +
            "    \"android_on\": true,\n" +
            "    \"ios_on\": true,\n" +
            "    \"retryCount\": 3,\n" +
            "    \"retryInterval\": 5,\n" +
            "    \"differance\": 500\n" +
            "}";

    private String plainText2 = "{\n" +
            "    \"special_list\": [],\n" +
            "    \"test_list\": [],\n" +
            "    \"links\": [\n" +
            "    {\n" +
            "        \"hostName\": \"8.212.104.65\",\n" +
            "        \"hostFileName\": \"tunnel1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"8.148.235.250\",\n" +
            "        \"hostFileName\": \"tunnel2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"47.122.51.248\",\n" +
            "        \"hostFileName\": \"tunnel3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"cchain.ink\",\n" +
            "        \"hostFileName\": \"cchain\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"ccline.vip\",\n" +
            "        \"hostFileName\": \"ccline\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"emxg.cn\",\n" +
            "        \"hostFileName\": \"cn\"\n" +
            "    }],\n" +
            "    \"ws_links\": [\n" +
            "    {\n" +
            "        \"hostName\": \"8.212.104.65\",\n" +
            "        \"hostFileName\": \"tunnel1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"8.148.235.250\",\n" +
            "        \"hostFileName\": \"tunnel2\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"47.122.51.248\",\n" +
            "        \"hostFileName\": \"tunnel3\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"cchain.ink\",\n" +
            "        \"hostFileName\": \"cchain\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"ccline.vip\",\n" +
            "        \"hostFileName\": \"ccline\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"hostName\": \"emxg.cn\",\n" +
            "        \"hostFileName\": \"cn\"\n" +
            "    }],\n" +
            "    \"ipPortList\": [ 8001, 8002, 8003, 8004, 8005, 8006 ],\n" +
            "    \"domainPort\":  443,\n" +
            "    \"android_on\": true,\n" +
            "    \"ios_on\": true,\n" +
            "    \"retryCount\": 3,\n" +
            "    \"retryInterval\": 5,\n" +
            "    \"differance\": 500\n" +
            "}";

    //公钥
    private String pub_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhq2atk9qMIoQaN9k8DkMvxNl6ZexVnLv7x76wLtdgI+DOKiIQao92nF9GxNUCPBA2GXEF0Kj6uzl9zkoi8ZmGKNab/yCr6D3FL/l8tDy6hJsafvzQSxvfbXbAZ9Bj9qqp/LtlG+MLmSNoMN+khyqvFdCf3FOD1x/7tfD0nzvfussSyOhbnDb3Wu3faAj8LpUU/YAS96dktCjOQ4T6fcp9tUF82HvAo8JuzcJJYiuHtXvptNMm8zUY1J5XQpgggWJoU5LfpHphQ6O3T6ag6XTFBQyZX6S/VacIvfETV3yWdOOFSDoHc3PFvHbwFMfFtfti4aeUSU1LHWucJQ9duOzaQIDAQAB";

    //私钥
    private String pri_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCGrZq2T2owihBo32TwOQy/E2Xpl7FWcu/vHvrAu12Aj4M4qIhBqj3acX0bE1QI8EDYZcQXQqPq7OX3OSiLxmYYo1pv/IKvoPcUv+Xy0PLqEmxp+/NBLG99tdsBn0GP2qqn8u2Ub4wuZI2gw36SHKq8V0J/cU4PXH/u18PSfO9+6yxLI6FucNvda7d9oCPwulRT9gBL3p2S0KM5DhPp9yn21QXzYe8Cjwm7NwkliK4e1e+m00ybzNRjUnldCmCCBYmhTkt+kemFDo7dPpqDpdMUFDJlfpL9Vpwi98RNXfJZ044VIOgdzc8W8dvAUx8W1+2Lhp5RJTUsda5wlD1247NpAgMBAAECggEAcoj7tSXuoh8Ye4fYuO9bprhhiHoISAPxh0OqbvQpPadPKoUxc97LQZYYlkkmw39HNoG9dSaobqdPn6ONlumAdF2gEPMHVUOoPY5BifInU+edKbkqk/etTZS2DzgYMrX9Ql23rO/8oIvFYr4/SxV0Ij53gBilES1SY5Y9pCm6KA2sDDIZI2MdJc1pM+LumX9flXUU9EZHCtGe4cassKH8nK0QSJfsUGppiPHuWf/PTSn544wt3XgBT/iaJ9rzITqmK6Zcr0znIqgbEmVB/6HUgc96/YDeRP4hXxp7TQQuNqy/2+PEmGm7EKMuvqElyRV1fsKz9toIR/CuD0FNLlVCnQKBgQDTwtPuCP35DAkd1Ko7Zv2lGUX5w3mtcQrQV1mOqB5kzNO1OcslsM5nIqjnOhEab7C9ByyXX912nMQnkWlPrxPMJFK70fcFG4N0jCQoNj5lKpgPLINi/O3V+d9OgrRoN8f7kTvcftlo0MdjgmYrPoW2iB5i910hO4ORpA2cjo1tAwKBgQCi0FAnaaxfILAraK7nrVkU04NMzCDs8sugVQJnmQWbhiU/cnHNe9mfRyvfyakhk4jWRpIxZxooQGdecDTSwqYVl8kyFeHb9VJJXxjfssDC2ubUGc7cI+dF0ThFQN6mN1y8VUHnNcdnFdvDmlFNU/V1N6+GCs1XTyuLoK/kOzNEIwKBgFGhBa75emV5dT4xj1mMrvPolSfdaVcpf0Cmm1g5iFXU+zsdxyiFSltJahCQOq2VJm0HifHkO2uQrLCQBid4VspXqijKnE00uOzBZOK3XkADNlCLGS6cT1YvlxwdmEugTQ+1gXzSzR3xdRrv1lCPLl4ZhJUjw5ue5G3M7o5OCUhfAoGAIF95QIzzeCrAvLDg7jH6n3h7B/8ioNqaAHRGuSClC8p9h4MS+7+fgtsc7y9i0MlMEQcRNH+CpPRBWXbIGgwYB/0ZRTsyoJDD3tDo2jnpuBTchtnm1sDQ0IIMRUDjxHsHlm8BXp7D2kxvWRJLDiweh0MeRHHLaIvWZbn6n0/iHq8CgYBdsEq2oYyXZ6XtK7UbfbTw67rzgoCyHoeAaKK8E2w2TkplhA6ZZIli5+O+wT+7VFYOYxcMEUTo917ZiZ2G+sDxRmxY407Q2f+NUfruicEgIwHt2tDq4nVapwL+adzmELCWmonrSOp4FJfsLvHjIhrjtqZPmBMt+gXacY6ITOKboA==";


    @Test
    public void testEncryptDecrypt() throws Exception {
        String a =

                "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";

        byte[] encryptedBytes = Base64.Default.decode(a,0,a.length());
        byte[] encryptedText = AES.decrypt(encryptedBytes,AES_KEY.getBytes(),null);
        System.out.println("Decrypted: " + new String(encryptedText,CHARSET_UTF8));
    }



}