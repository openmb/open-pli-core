SSUMMARY = "Hardware drivers for ${MACHINE}"
SECTION = "base"
PRIORITY = "required"
LICENSE = "CLOSED"
require conf/license/license-close.inc

KV = "3.14.2"
SRCDATE = "20150909"

require ceryon-dvb-modules.inc
SRC_URI[md5sum] = "e3c0f76875b61f58b733bd1b56a0671a"
SRC_URI[sha256sum] = "399c3535f96aa7fb42f92e0ba7fdf7d7b1425a940cc5e81205c6375fccd042a6"
