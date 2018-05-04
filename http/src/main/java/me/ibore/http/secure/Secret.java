package me.ibore.http.secure;

import java.security.GeneralSecurityException;

public interface Secret {

    String encrypt(String data) throws GeneralSecurityException;

    byte[] encrypt(byte[] data) throws GeneralSecurityException;

    String decrypt(String data) throws GeneralSecurityException;

    byte[] decrypt(byte[] data) throws GeneralSecurityException;
}