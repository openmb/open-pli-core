KV = "3.14.2"
SRCDATE = "20150317"

SRC_URI[md5sum] = "8c4e7dc6735d00d767fe9aed5529ca8c"
SRC_URI[sha256sum] = "d8beae988956c35d5e649fbd804e1e70d89ff17c71802e83de52da416ef08edd"

SRC_URI = "http://code-ini.com/software/drivers/ini-800-drivers-${KV}-${SRCDATE}.zip"

require ini-dvb-modules.inc
