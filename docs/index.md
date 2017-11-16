 [ ![Download](https://api.bintray.com/packages/airgoss/airGOss/misc/images/download.svg) ](https://bintray.com/airgoss/airGOss/misc/_latestVersion)

# Miscellaneous Android Utilities
The airG android 'misc' library is a group of utilities for easier checksum calculation, compression, and decompression as well some utility types. 

For a more complete reference, check out the [Javadocs](https://airg.github.io/android-misc/javadoc/).

## ArrayHash
The `ArrayHash` data structure is a HashMap that preserves insertion order, allowing for fast random as well as sequential lookup.

## Zipper
The `Zipper` class provides compression and decompression functionality for files, strings, and streams via gzip.

## Hasher
The `Hasher` class provides checksum calculation methods for SHA1 and MD5 algorithms, but also allows you to specify your preferred algorithms as well.

## Toaster
The `Toaster` class provides fire & forget accessibility to toasts by always including the `.show()` (because you know you always forget it) and making sure the toasts are running on the main thread.

# Usage
To use the _android-misc_ library in your builds, add the following line to your Gradle build script:

`compile 'com.airg.android:misc:+@aar'`

Or download the library from the download link at the top of this page.

# Contributions
Please refer to the [contribution instructions](https://airg.github.io/#contribute).