/*
 * ****************************************************************************
 *   Copyright  2016 airG Inc.                                                 *
 *                                                                             *
 *   Licensed under the Apache License, Version 2.0 (the "License");           *
 *   you may not use this file except in compliance with the License.          *
 *   You may obtain a copy of the License at                                   *
 *                                                                             *
 *       http://www.apache.org/licenses/LICENSE-2.0                            *
 *                                                                             *
 *   Unless required by applicable law or agreed to in writing, software       *
 *   distributed under the License is distributed on an "AS IS" BASIS,         *
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  *
 *   See the License for the specific language governing permissions and       *
 *   limitations under the License.                                            *
 * ***************************************************************************
 */

package com.airg.android.io;

import android.support.annotation.NonNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Calculate MD5, SHA1, or other checksum of data
 *
 * @author Mahram Z. Foadi
 */
@SuppressWarnings({"UnusedDeclaration", "WeakerAccess", "TryFinallyCanBeTryWithResources"})
@NoArgsConstructor(access = AccessLevel.PRIVATE) // no instance
public final class Hasher {

    /**
     * Calculate the SHA1 checksum of input string
     *
     * @param string input string
     * @return SHA1 checksum of the string
     * @throws UnsupportedEncodingException if the string encoding is unsupported
     * @throws NoSuchAlgorithmException     if the local implementation does not support sha1
     */
    public static String sha1(@NonNull final String string)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return hashStringWithAlgorithm(string, SHA_1_ALGORITHM);
    }

    /**
     * Calculate the SHA1 checksum of file contents
     *
     * @param file input file
     * @return SHA1 checksum of file contents
     * @throws IOException              errors reading, finding, or accessing the input file
     * @throws NoSuchAlgorithmException if the local implementation does not support sha1
     */
    public static String sha1(@NonNull final File file)
            throws IOException, NoSuchAlgorithmException {
        return hashFileWithAlgorithm(file, SHA_1_ALGORITHM);
    }

    /**
     * Calculate the SHA1 checksum of stream content
     *
     * @param in input stream
     * @return SHA1 checksum of the stream
     * @throws IOException              errors reading from the stream
     * @throws NoSuchAlgorithmException if the local implementation does not support sha1
     */
    public static String sha1(@NonNull final InputStream in)
            throws IOException, NoSuchAlgorithmException {
        return hashStreamWithAlgorithm(in, SHA_1_ALGORITHM);
    }

    /**
     * Calculate the MD5 checksum of input string
     *
     * @param string input string
     * @return MD5 checksum of the string
     * @throws UnsupportedEncodingException if the string encoding is unsupported
     * @throws NoSuchAlgorithmException     if the local implementation does not support md5
     */
    public static String md5(@NonNull final String string)
            throws UnsupportedEncodingException, NoSuchAlgorithmException {
        return hashStringWithAlgorithm(string, MD5_ALGORITHM);
    }

    /**
     * Calculate the MD5 checksum of file contents
     *
     * @param file input file
     * @return MD5 checksum of file contents
     * @throws IOException              errors reading, finding, or accessing the input file
     * @throws NoSuchAlgorithmException if the local implementation does not support md5
     */
    public static String md5(@NonNull final File file)
            throws IOException, NoSuchAlgorithmException {
        return hashFileWithAlgorithm(file, MD5_ALGORITHM);
    }

    /**
     * Calculate the MD5 checksum of stream content
     *
     * @param in input stream
     * @return MD5 checksum of the stream
     * @throws IOException              errors reading from the stream
     * @throws NoSuchAlgorithmException if the local implementation does not support md5
     */
    public static String md5(@NonNull final InputStream in)
            throws IOException, NoSuchAlgorithmException {
        return hashStreamWithAlgorithm(in, MD5_ALGORITHM);
    }

    /**
     * Calculate the checksum of given string via the specified algorithm
     *
     * @param string    input string
     * @param algorithm name of algorithm to use, e.g. <code>SHA-1</code>
     * @return calculated checksum of string
     * @throws NoSuchAlgorithmException     if the local implementation does not support the given algorithm
     * @throws UnsupportedEncodingException if the string encoding is not supported
     */
    public static String hashStringWithAlgorithm(@NonNull final String string, @NonNull final String algorithm)
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest digest = MessageDigest.getInstance(algorithm);
        return encodeHexString(digest.digest(string.getBytes(UTF8_CHARSET)));
    }

    /**
     * Calculate the checksum of the given file via the specified algorithm
     *
     * @param file      input file
     * @param algorithm name of algorithm to use, e.g. <code>SHA-1</code>
     * @return calculated checksum of the file
     * @throws IOException              errors reading, finding, or accessing the input file
     * @throws NoSuchAlgorithmException if the local implementation does not support md5
     */
    private static String hashFileWithAlgorithm(@NonNull final File file, @NonNull final String algorithm)
            throws IOException, NoSuchAlgorithmException {
        return hashStreamWithAlgorithm(new FileInputStream(file), algorithm);
    }

    /**
     * Calculate the checksum of stream content via the specified algorithm
     *
     * @param in        input stream
     * @param algorithm name of algorithm to use, e.g. <code>SHA-1</code>
     * @return calculated checksum of the stream
     * @throws IOException              errors reading from the stream
     * @throws NoSuchAlgorithmException if the local implementation does not support md5
     */
    private static String hashStreamWithAlgorithm(@NonNull final InputStream in, @NonNull final String algorithm)
            throws NoSuchAlgorithmException, IOException {

        final MessageDigest digest = MessageDigest.getInstance(algorithm);
        final byte[] buffer = new byte[1024];

        int read;

        while ((read = in.read(buffer)) > 0) {
            digest.update(buffer, 0, read);
        }
        in.close();
        return encodeHexString(digest.digest());
    }

    /**
     * Obtain a hexadecimal representation of the input byte array
     *
     * @param bytes intput
     * @return hexadecimal representation
     */
    public static String encodeHexString(@NonNull final byte[] bytes) {
        final char[] buf = new char[bytes.length * 2];

        byte b;
        int c = 0;
        for (final byte aByte : bytes) {
            b = aByte;
            buf[c++] = HEX_CHAR[(b >> 4) & 0xf];
            buf[c++] = HEX_CHAR[b & 0xf];
        }

        return new String(buf);
    }

    private final static char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    static final String SHA_1_ALGORITHM = "SHA-1";
    static final String MD5_ALGORITHM = "MD5";

    static final String UTF8_CHARSET = "UTF-8";
}
