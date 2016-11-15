 [ ![Download](https://api.bintray.com/packages/airgoss/airGOss/misc/images/download.svg) ](https://bintray.com/airgoss/airGOss/misc/_latestVersion)

#Miscellaneous Android Utilities
The airG android 'misc' library is a group of utilities for easier checksum calculation, compression, and decompression as well some utility types. 

##ArrayHash
The `ArrayHash` data structure is a HashMap that preserves insertion order, allowing for fast random as well as sequential lookup.

##Zipper
The `Zipper` class provides compression and decompression functionality for files, strings, and streams via gzip.

##Hasher
The `Hasher` class provides checksum calculation methods for SHA1 and MD5 algorithms, but also allows you to specify your preferred algorithms as well.

##Toaster
The `Toaster` class provides fire & forget accessibility to toasts by always including the `.show()` (because you know you always forget it) and making sure the toasts are running on the main thread.

##Contributions
Contributions are appreciated and welcome. In order to contribute to this repo please follow these steps:

1. Fork the repo
1. Add this repo as the `upstream` repo in your fork (`git remote add upstream git@github.com:airG/android-misc.git`)
1. Contribute (Be sure to format your code according to th included code style settings)
1. IMPORTANT: Rebase with upstream (`git pull --rebase upstream`)
1. Submit a pull request
