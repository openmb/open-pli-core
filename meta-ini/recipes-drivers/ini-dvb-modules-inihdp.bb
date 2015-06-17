KV = "3.14.2"
SRCDATE = "20150610"

SRC_URI[md5sum] = "41fe0aa02e46e9a39cbb1584c6d5edbe"
SRC_URI[sha256sum] = "c6410f7503950281bb14a342b0d59e067d1f0d28a58b5346176fc9368793aebe"

SRC_URI = "http://code-ini.com/software/drivers/ini-800-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
