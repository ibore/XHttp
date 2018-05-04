package me.ibore.http.secure;

import java.security.GeneralSecurityException;

public class SafeSecret implements Secret {
    @Override
    public String encrypt(String data) throws GeneralSecurityException {
        return data;
    }

    @Override
    public byte[] encrypt(byte[] data) throws GeneralSecurityException {
        return data;
    }

    @Override
    public String decrypt(String data) throws GeneralSecurityException {
        return data;
    }

    @Override
    public byte[] decrypt(byte[] data) throws GeneralSecurityException {
        return data;
    }
}