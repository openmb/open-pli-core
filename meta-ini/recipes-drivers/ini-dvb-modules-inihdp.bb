KV = "3.14.2"
SRCDATE = "20141210"

SRC_URI[md5sum] = "a9c00cd52bed5039ea4f1a6c60adafc2"
SRC_URI[sha256sum] = "ed9a133dfecfb342f1fa93517ab6816117f2ea630057e5e21c516ec133c4f932"

SRC_URI = "http://code-ini.com/software/drivers/ini-800-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
