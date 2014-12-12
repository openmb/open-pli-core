KV = "3.14.2"
SRCDATE = "20141205"

SRC_URI[md5sum] = "a37cd81d721d6f14e893b25fc00dd3db"
SRC_URI[sha256sum] = "b3b1e49474eacc4b119fd996fbc1e452ee933fbd9cea9b486743ed8199fc7c96"

SRC_URI = "http://code-ini.com/software/drivers/ini-800-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
