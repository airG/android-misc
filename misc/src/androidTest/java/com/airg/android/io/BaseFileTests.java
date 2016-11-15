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

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mahramf.
 */

class BaseFileTests {
    static final Map<String, String> FILE_SHA1;
    static final Map<String, String> FILE_MD5;
    static final String[] INPUT_FILE;

    final Context context = InstrumentationRegistry.getTargetContext();

    static {
        final HashMap<String, String> sha1 = new HashMap<>();
        final HashMap<String, String> md5 = new HashMap<>();

        sha1.put("zip_input1.txt", "8f9d238ca7ec2d29af37651e01accc3b487d38eb");
        sha1.put("zip_input2.txt", "4ff2e92a176133031a15092906ce2f18e6db0663");
        sha1.put("zip_input3.svg", "88d0f419206dbf266b38a26a5c63bd25ebc29e43");

        md5.put("zip_input1.txt", "8cb268ad456b6bb9cccafa4af908b476");
        md5.put("zip_input2.txt", "90d2e96288d10f154e00dcf5a2578d62");
        md5.put("zip_input3.svg", "17d02022dea3ed2b2f0d0d2f39b8b9eb");

        FILE_SHA1 = Collections.unmodifiableMap(sha1);
        FILE_MD5 = Collections.unmodifiableMap(md5);

        INPUT_FILE = FILE_SHA1.keySet().toArray(new String[FILE_SHA1.size()]);
    }

    void mkFile(final String name, final File out) throws IOException {
        final InputStream in = context.getResources().getAssets().open(name);
        final OutputStream os = new FileOutputStream(out, false);

        final byte[] buffer = new byte[2048];

        int read;

        while ((read = in.read(buffer)) > 0) {
            os.write(buffer, 0, read);
        }
        os.close();
    }

    String toString(final byte[] bytes) throws UnsupportedEncodingException {
        return new String(bytes, Zipper.UTF8);
    }

    String hash(final File file) throws IOException, NoSuchAlgorithmException {
        return Hasher.sha1(file);
    }
}
