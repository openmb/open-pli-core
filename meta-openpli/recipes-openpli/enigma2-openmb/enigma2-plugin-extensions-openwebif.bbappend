PRINC = "52"

inherit pythonnative

PACKAGE_ARCH = "${MACHINE_ARCH}"

SRC_URI = "git://github.com/oe-alliance/e2openplugin-${MODULE}.git;protocol=git"

S="${WORKDIR}/git"

python do_package_prepend () {
    boxtypes = [
        ('dm500hd', 'dm500hd.jpg', 'dm_normal.png'),
        ('dm500hdv2', 'dm500hd.jpg', 'dm_normal.png'),
        ('dm7020hd', 'dm7020hd.jpg', 'dm_normal.png'),
        ('dm8000', 'dm8000.jpg', 'dm_normal.png'),
        ('dm800se', 'dm800se.jpg', 'dm_normal.png'),
        ('dm800sev2', 'dm800se.jpg', 'dm_normal.png'),
        ('dm800', 'unknown.jpg', 'dm_normal.png'),
        ('et4x00', 'et4x00.jpg', 'et_rc13_normal.png'),
        ('et5x00', 'et5x00.jpg', 'et_rc5_normal.png'),
        ('et6x00', 'et5x00.jpg', 'et_rc5_normal.png'),
        ('et9x00', 'et9x00.jpg', 'et_rc7_normal.png'),
        ('odinm7', 'odinm7.jpg', 'odinm7.png'),
        ('odinm9', 'odinm9.jpg', 'odinm9.png'),
        ('tmtwin', 'tmtwin.jpg', 'tm.png'),
        ('tm2t', 'tm2t.jpg', 'tm.png'),
        ('tmsingle', 'tmsingle.jpg', 'tm.png'),
        ('tmnano', 'tmnano.jpg', 'tm.png'),
        ('tmnano2t', 'tmnano2t.jpg', 'tm.png'),
        ('vuduo', 'duo.jpg', 'vu_normal.png'),
        ('vuduo2', 'duo2.jpg', 'vu_normal.png'),
        ('vusolo', 'solo.jpg', 'vu_normal.png'),
        ('vusolo2', 'solo2.jpg', 'vu_normal.png'),
        ('vuultimo', 'ultimo.jpg', 'vu_ultimo.png'),
        ('vuuno', 'uno.jpg', 'vu_normal.png'),
        ('gb800se', 'gb800se.jpg', 'gigablue_black.png'),
        ('gb800solo', 'gb800solo.jpg', 'gigablue_black.png'),
        ('gb800ue', 'gb800ue.jpg', 'gigablue_black.png'),
        ('gbquad', 'gbquad.jpg', 'gigablue_black.png'),
        ('gbquadplus', 'gbquad.jpg', 'gigablue_black.png'),
        ('gb800seplus', 'gb800seplus.jpg', 'gigablue_new.png'),
        ('gb800ueplus', 'gb800ueplus.jpg', 'gigablue_new.png'),
        ('mbtwin', 'ini-3000.jpg', 'ini-3000.png'),
        ('mbmini', 'ini-3000.jpg', 'ini-3000.png'),
        ('inihdp', 'ini-3000.jpg', 'ini-3000.png'),
        ('xp1000', 'xp1000.jpg', 'xp_rc14_normal.png'),
        ('xp1000s', 'xp1000.jpg', 'xp_rc14_normal.png'),
        ('ebox5000', 'ebox5000.jpg', 'ebox5000.png'),
        ('ebox5100', 'ebox5100.jpg', 'ebox5000.png'),
        ('eboxlumi', 'eboxlumi.jpg', 'ebox5000.png'),
        ('ebox7358', 'ebox7358.jpg', 'ebox5000.png'),
        ('ixussone', 'ixussone.jpg', 'ixussone.png'),
        ('ixusszero', 'ixusszero.jpg', 'ixusszero.png'),
        ('ixussduo', 'ixussone.jpg', 'ixussone.png'),
        ('iqonios100hd', 'ios100hd.jpg', 'iqon.png'),
        ('iqonios200hd', 'ios200hd.jpg', 'iqon.png'),
        ('iqonios300hd', 'ios300hd.jpg', 'iqon.png'),
        ('force1', 'ios200hd.jpg', 'iqon.png'),		
        ('mbtwin', 'ini-3000.jpg', 'ini-3000.png'),
        ('mediabox', 'ios100hd.jpg', 'iqon.png'),
        ('optimussos1', 'optimussos1.jpg', 'optimuss.png'),
        ('optimussos2', 'optimussos2.jpg', 'optimuss.png'),
        ('azboxme', 'me.jpg', 'me.png'),
        ('azboxhd', 'premium.jpg', 'premium.png'),
        ('azboxminime', 'minime.jpg', 'me.png'),
        ('e3hd', 'e3hd.jpg', 'e3hd.png'),
        ('cube', 'cube.jpg', 'cube.png'),
        ('sogno8800hd', 'sogno8800hd.jpg', 'sogno.png'),
    ]
}

python populate_packages_prepend() {
    enigma2_plugindir = bb.data.expand('${libdir}/enigma2/python/Plugins', d)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/[a-zA-Z0-9_]+.*$', 'enigma2-plugin-%s', '%s', recursive=True, match_path=True, prepend=True, extra_depends="enigma2")
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.py$', 'enigma2-plugin-%s-src', '%s (source files)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.la$', 'enigma2-plugin-%s-dev', '%s (development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\.a$', 'enigma2-plugin-%s-staticdev', '%s (static development)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/(.*/)?\.debug/.*$', 'enigma2-plugin-%s-dbg', '%s (debug)', recursive=True, match_path=True, prepend=True)
    do_split_packages(d, enigma2_plugindir, '^(\w+/\w+)/.*\/.*\.po$', 'enigma2-plugin-%s-po', '%s (translations)', recursive=True, match_path=True, prepend=True)
}
