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

import android.content.res.AssetManager;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

import static org.junit.Assert.*;

/**
 * Created by mahramf.
 */
public class HasherTest extends BaseFileTests {
    private static final String[] INPUT_STRING = {
            "This is a test",
            " ",
            "`1234567890qwertyuiopasdfghjklzxcvbnm,./';]["
    };

    private static final String[] STRING_MD5 = {
            "ce114e4501d2f4e2dcea3e17b546f339",
            "7215ee9c7d9dc229d2921a40e899ec5f",
            "0798eabaa9c64f6248661fd05eb92927"
    };

    private static final String[] STRING_SHA1 = {
            "a54d88e06612d820bc3be72877c74f257b561b19",
            "b858cb282617fb0956d960215c8e84d1ccf909c6",
            "ed9f1cfc74edf96916948babba64cdfbb3d2c7ee"
    };

    @Test
    public void testStringSHA1 () throws UnsupportedEncodingException, NoSuchAlgorithmException {
        for (int i = 0; i < INPUT_STRING.length; i++) {
            assertHashEquals (STRING_SHA1[i], Hasher.sha1 (INPUT_STRING[i]), INPUT_STRING[i]);
        }
    }

    @Test
    public void testFileSHA1 () throws IOException, NoSuchAlgorithmException {
        final File base = context.getFilesDir ();

        for (final String filename : INPUT_FILE) {
            final String expected = FILE_SHA1.get(filename);

            final File file = new File(base, filename);
            mkFile(filename, file);

            final String actual = Hasher.sha1(file);

            assertHashEquals(expected, actual, filename);
        }
    }

    @Test
    public void testStreamSHA1 () throws IOException, NoSuchAlgorithmException {
        final AssetManager assetManager = context.getResources ().getAssets ();

        for (String aINPUT_FILE : INPUT_FILE) {
            final String expected = FILE_SHA1.get(aINPUT_FILE);
            final String actual = Hasher.sha1(assetManager.open(aINPUT_FILE, AssetManager.ACCESS_STREAMING));

            assertHashEquals(expected, actual, aINPUT_FILE);
        }
    }

    @Test
    public void testStringMD5 () throws UnsupportedEncodingException, NoSuchAlgorithmException {
        for (int i = 0; i < INPUT_STRING.length; i++) {
            assertHashEquals (STRING_MD5[i], Hasher.md5 (INPUT_STRING[i]), INPUT_STRING[i]);
        }
    }

    @Test
    public void testFileMD5 () throws IOException, NoSuchAlgorithmException {
        final File base = context.getFilesDir ();

        for (String aINPUT_FILE : INPUT_FILE) {
            final String expected = FILE_MD5.get(aINPUT_FILE);

            final File file = new File(base, aINPUT_FILE);
            mkFile(aINPUT_FILE, file);

            final String actual = Hasher.md5(file);

            assertHashEquals(expected, actual, aINPUT_FILE);
        }
    }

    @Test
    public void testStreamMD5 () throws IOException, NoSuchAlgorithmException {
        final AssetManager assetManager = context.getResources ().getAssets ();

        for (String aINPUT_FILE : INPUT_FILE) {
            final String expected = FILE_MD5.get(aINPUT_FILE);
            final String actual = Hasher.md5(assetManager.open(aINPUT_FILE, AssetManager.ACCESS_STREAMING));

            assertHashEquals(expected, actual, aINPUT_FILE);
        }
    }

    private void assertHashEquals (final String expected, final String actual, final String id) {
        if (expected.equalsIgnoreCase (actual)) {
            return;
        }

        fail (String.format (Locale.ENGLISH, "[%s]: expected '%s', but received '%s'", id, expected, actual));
    }
}