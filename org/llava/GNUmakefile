#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2000 Jan 19 (Wed) 17:39:40 by Harold Carr.
#

TOPDIR		=	..

PACKAGE		=	lava
PKGDIR		=	lava

FILES_java	= \
	$(PKGDIR)/F.java \
	$(PKGDIR)/Lava.java \
	$(PKGDIR)/LavaVersion.java \
	$(PKGDIR)/LavaVersionImpl.java \
	$(PKGDIR)/Repl.java \
	$(PKGDIR)/ReplImpl.java

all :: classes 

SUBDIRS		=	io lang util

all optimized debug clean clobber ::
	@for i in $(SUBDIRS) ; do \
	    echo ">>>Recursively making "$$i" "$@"..."; \
	    cd $$i; $(MAKE) $@ || exit 1; cd ..;  \
	    echo "<<<Finished Recursively making "$$i" "$@"." ; \
	done

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.

