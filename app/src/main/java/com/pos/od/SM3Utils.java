package com.pos.od;


import android.content.Context;
import android.content.res.AssetManager;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Security;


public class SM3Utils {

    public static String sm3Digest(Context context) throws IOException {
        AssetManager assetManager = context.getAssets();
        InputStream inputStream;
        try {
            inputStream = assetManager.open("20240617490.zip");
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Security.addProvider(new BouncyCastleProvider());
        Digest digest = new SM3Digest();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }
        byte[] digestBytes = new byte[digest.getDigestSize()];
        digest.doFinal(digestBytes, 0);

        return bytesToHex(digestBytes);
    }

    static String bytesToHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }


    public static byte[] calculateSM3Digest(File file) throws IOException {
        Security.addProvider(new BouncyCastleProvider());
        Digest digest = new SM3Digest();
        updateDigest(digest, file);
        byte[] digestBytes = new byte[digest.getDigestSize()];
        digest.doFinal(digestBytes, 0);
        return digestBytes;
    }

    private static void updateDigest(Digest digest, File file) throws IOException {
        byte[] buffer = new byte[1024];
        try (FileInputStream fis = new FileInputStream(file)) {
            int read = 0;
            while ((read = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, read);
            }
        }
    }

    private static void updateDigest(Digest digest, InputStream is) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = is.read(buffer)) != -1) {
            digest.update(buffer, 0, bytesRead);
        }
    }



}
