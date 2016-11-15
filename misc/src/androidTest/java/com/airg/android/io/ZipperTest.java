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

import junit.framework.Assert;

import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by mahramf.
 */
@SuppressWarnings("ResultOfMethodCallIgnored")
public class ZipperTest extends BaseFileTests {

    @Test
    public void stringCompression() throws IOException {
        final String test = "This is the UncOmPreSSeD String!!! It includes numbers like 1,2,3,123456,567 and " +
                "punctuation characters. Punctuation characters such as %, #, @, *, $, and more.";

        Assert.assertEquals(test, toString(Zipper.gunzip(Zipper.gzip(test))));
    }

    @Test
    public void testCompDecomp() throws Exception {
        final File directory = context.getFilesDir();

        final File original;
        final File compressed;
        final File decompressed;

        original = File.createTempFile("test", ".original", directory);
        compressed = File.createTempFile("test", ".compressed", directory);
        decompressed = File.createTempFile("test", ".decompressed", directory);

        for (final String filename : INPUT_FILE) {
            if (original.exists())
                assertTrue("could not delete " + original.getAbsolutePath(), original.delete());

            if (compressed.exists())
                assertTrue("could not delete " + compressed.getAbsolutePath(), compressed.delete());

            if (decompressed.exists())
                assertTrue("could not delete " + decompressed.getAbsolutePath(), decompressed.delete());

            mkFile(filename, original);
            assertTrue("input file does not exist", original.exists());
            final long originalLength = original.length();
            final String expectedHash = FILE_SHA1.get(filename);

            final String originalHash = hash(original);

            assertEquals("hash mismatch", expectedHash, originalHash);

            Zipper.gzip(original, compressed);

            assertTrue("compressed file doesn't exist", compressed.exists());

            final long compressedFileSize = compressed.length();
            assertTrue(compressed.getPath() + " file size: " + compressedFileSize, compressedFileSize > 0);

            Zipper.gunzip(compressed, decompressed);

            final long decompLength = decompressed.length();

            assertTrue(decompressed.exists());
            assertTrue(decompLength > 0);
            assertEquals(decompLength, originalLength);

            assertEquals(expectedHash, hash(decompressed));
        }

        original.delete();
        compressed.delete();
        decompressed.delete();
    }
}