SUMMARY = "DVD access multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=64e753fa7d1ca31632bc383da3b57c27"
PV = "5.0.1"
PR = "r0"

inherit autotools pkgconfig git-project

SRCREV = "46ef934304b3a326615ae6dafc63b337f2f2628f"
SRC_URI = "git://git.videolan.org/libdvdread.git"
