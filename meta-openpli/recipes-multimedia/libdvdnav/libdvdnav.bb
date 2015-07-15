SUMMARY = "DVD navigation multimeda library"
SECTION = "libs/multimedia"
LICENSE = "GPLv2+"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"
DEPENDS = "libdvdread"
PV = "5.0.2"

inherit autotools pkgconfig git-project

SRCREV = "5fb919774444aa382f6e02cb63801c82f2c38325"
SRC_URI = "git://git.videolan.org/libdvdnav.git"
