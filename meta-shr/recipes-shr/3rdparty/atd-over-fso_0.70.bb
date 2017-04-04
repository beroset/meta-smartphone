DESCRIPTION = "Lightweight At Daemon working on top of org.freesmartphone.otimed"
SECTION = "base"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://atd.c;endline=16;md5=db6c20c0a8b3c7a2d903bb30a11ac52a"
RCONFLICTS_${PN} = "at atd"
RREPLACES_${PN} = "at atd"
RPROVIDES_${PN} = "atd"
RCONFLICTS_${PN}-dbg = "at-dbg atd-dbg"
RREPLACES_${PN}-dbg = "at-dbg atd-dbg"
RPROVIDES_${PN}-dbg = "atd-dbg"
RCONFLICTS_${PN}-dev = "at-dev atd-dev"
RREPLACES_${PN}-dev = "at-dev atd-dev"
RPROVIDES_${PN}-dev = "atd-dev"
DEPENDS = "dbus-glib pkgconfig"
RDEPENDS_${PN} += "dbus dbus-glib frameworkd"

PR = "r5"

SRC_URI = "${HANDHELDS_CVS};module=apps/atd;tag=ATD-0_70 \
           file://atd-startup.patch;striplevel=0 \
           file://atd-startup-restart.patch;striplevel=0 \
           file://atd-alarm-glue.patch;striplevel=0 \
           file://atd-over-fso.conf.patch;striplevel=0 \
           file://run-over-fso.patch"
S = "${WORKDIR}/atd"

inherit update-rc.d

INITSCRIPT_NAME = "atd"
INITSCRIPT_PARAMS = "defaults 97"

EXTRA_OEMAKE = "-e MAKEFLAGS="

do_compile() {
    export CFLAGS="$CFLAGS `${STAGING_BINDIR_NATIVE}/pkg-config --cflags dbus-glib-1`"
    export LDFLAGS="$LDFLAGS `${STAGING_BINDIR_NATIVE}/pkg-config --libs dbus-glib-1`"
    oe_runmake
}

do_install() {
    install -d ${D}${sbindir}
    install atd ${D}${sbindir}/atd
    install -d ${D}${sysconfdir}/init.d
    install -d ${D}${sysconfdir}/dbus-1/system.d
    install dist/etc/init.d/atd ${D}${sysconfdir}/init.d/atd
    install dist/etc/dbus-1/system.d/atd-over-fso.conf ${D}${sysconfdir}/dbus-1/system.d/atd-over-fso.conf
}

updatercd_postinst_prepend() {
    if [ -z "$D" ]; then
        if type update-rc.d >/dev/null 2>/dev/null; then
            if [ -f /etc/init.d/dbus-1 ]; then
                /etc/init.d/dbus-1 reload || true
            fi
        fi
    fi
}

updatercd_postrm_append() {
    if [ -z "$D" ]; then
        if type update-rc.d >/dev/null 2>/dev/null; then
            if [ -f /etc/init.d/dbus-1 ]; then
                /etc/init.d/dbus-1 reload || true
            fi
        fi
    fi
}

PNBLACKLIST[atd-over-fso] ?= "Fails to build with RSS http://errors.yoctoproject.org/Errors/Details/130540/ - the recipe will be removed on 2017-09-01 unless the issue is fixed"
