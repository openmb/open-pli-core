do_compile_prepend() {
    test -f ${B}/src/Makefile.bkp || \
    cp ${B}/src/Makefile ${B}/src/Makefile.bkp && \
    sed "/DEFAULT_INCLUDES =/s|\(.*\)|\\1 -I\$(top_builddir)/src/libbluray|p" ${B}/src/Makefile.bkp > ${B}/src/Makefile
}
