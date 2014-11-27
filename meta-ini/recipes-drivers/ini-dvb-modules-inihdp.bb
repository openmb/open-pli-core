KV = "3.14.2"
SRCDATE = "20141105"

SRC_URI[md5sum] = "65fe02feeb4c252b2b78e592e8f2454b"
SRC_URI[sha256sum] = "50360a41d91ca3760f0173231f4445cad944c7c13b4ba7b7abf34836ebca1b44"

SRC_URI = "http://code-ini.com/software/drivers/ini-800-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
