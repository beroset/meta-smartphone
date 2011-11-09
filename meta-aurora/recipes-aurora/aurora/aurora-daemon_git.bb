require aurora-base.inc

DESCRIPTION = "This is the daemon responsible for the aurora user interface"

LICENSE = "GPLv2 & Other"
LIC_FILES_CHKSUM = " \
  file://COPYING.GPL;md5=751419260aa954499f7abaabaa882bbe \
  file://COPYING;md5=6ae364bb1619a9262f6854c6d96f4d07 \
"

PR = "${INC_PR}.7"

DEPENDS = "qt4-x11-free libfso-qt libfsobasics libfsoframework"

PV = "0.1+gitr${SRCPV}"

SRC_URI = "${FREESMARTPHONE_GIT}/aurora.git;protocol=git;branch=master"
SRCREV = "${AURORA_SRCREV}"
S = "${WORKDIR}/git/aurora-daemon"

inherit autotools update-alternatives

EXTRA_OECONF_append = " --with-qt-basedir=qt4"

FILES_${PN} += " ${datadir}/aurora ${libdir}/qt4/imports/Aurora ${datadir}/aurora"
FILES_${PN}-dbg += "${libdir}/qt4/imports/Aurora/*/.debug"

ALTERNATIVE_PATH = "${bindir}/aurora-daemon"
ALTERNATIVE_NAME = "x-window-manager"
ALTERNATIVE_LINK = "${bindir}/x-window-manager"
ALTERNATIVE_PRIORITY = "16"
