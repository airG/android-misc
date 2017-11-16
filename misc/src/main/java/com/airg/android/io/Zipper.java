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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Compression and decompression methods via <code>gzip</code>
 *
 * @author Mahram Z. Foadi
 */
@SuppressWarnings({"UnusedDeclaration", "WeakerAccess", "TryFinallyCanBeTryWithResources"})
@NoArgsConstructor(access = AccessLevel.PRIVATE) // no instance
public final class Zipper {
    private final static int BUFFER_SIZE = 1024;
    static final String UTF8 = "UTF8";

    /**
     * Compress a given file into another
     *
     * @param original   Input file (uncompressed)
     * @param compressed Output file (compressed)
     * @throws IOException If unable to find/read input file or unable to write output file
     */
    public static void gzip(final File original, final File compressed) throws IOException {
        final InputStream orig = new FileInputStream(original);
        final FileOutputStream comp = new FileOutputStream(compressed);

        gzip(orig, comp);
        orig.close();
        comp.flush();
        comp.close();
    }

    /**
     * Compress the contents of a stream and write to another. This method <b>does not close</b> either stream.
     *
     * @param in  Input stream (uncompressed)
     * @param out Output stream (compressed)
     * @throws IOException if unable to read/write either stream
     */
    public static void gzip(final InputStream in, final OutputStream out) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];

        final BufferedInputStream bis = new BufferedInputStream(in);
        final GZIPOutputStream zos = new GZIPOutputStream(new BufferedOutputStream(out));

        int read;

        while ((read = bis.read(buffer)) > 0) {
            zos.write(buffer, 0, read);
        }

        zos.flush();
        zos.close();
    }

    /**
     * Compress a string
     *
     * @param string Input string (uncompressed)
     * @return Compressed string bytes
     * @throws IOException if unable to write to compressed stream
     */
    public static byte[] gzip(final String string) throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream(string.length());
        GZIPOutputStream gos = new GZIPOutputStream(bos);

        gos.write(string.getBytes(UTF8));
        gos.close();
        bos.flush();
        bos.close();
        return bos.toByteArray();
    }

    /**
     * Decompress a file into another
     *
     * @param compressed input file (compressed)
     * @param output     output file (decompressed)
     * @throws IOException if unable to read or write
     */
    public static void gunzip(final File compressed, final File output) throws IOException {
        final FileInputStream comp = new FileInputStream(compressed);
        final FileOutputStream decomp = new FileOutputStream(output);
        gunzip(comp, decomp);
        comp.close();
        decomp.close();
    }

    /**
     * Decompress the contents of a stream and write to another. This method <b>does not close</b> either stream.
     *
     * @param in  Input stream (compressed)
     * @param out Output stream (decompressed)
     * @throws IOException If unable to read/write
     */
    public static void gunzip(final InputStream in, final OutputStream out) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];

        final GZIPInputStream zis = new GZIPInputStream(new BufferedInputStream(in));
        final BufferedOutputStream bos = new BufferedOutputStream(out);

        int read;

        while ((read = zis.read(buffer)) > 0) {
            bos.write(buffer, 0, read);
        }

        bos.flush();
        bos.close();
    }

    /**
     * Unzip an array of compressed bytes
     *
     * @param cmopressed byte array input (compressed)
     * @return decompressed byte array
     * @throws IOException if unable to read/write
     */
    public static byte[] gunzip(final byte[] cmopressed) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];

        final ByteArrayInputStream bis = new ByteArrayInputStream(cmopressed);
        final GZIPInputStream zis = new GZIPInputStream(new BufferedInputStream(bis));
        final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int read;

        while ((read = zis.read(buffer)) > 0) {
            bos.write(buffer, 0, read);
        }

        zis.close();
        bis.close();

        final byte[] bytes = bos.toByteArray();
        bos.close();
        return bytes;
    }
}
