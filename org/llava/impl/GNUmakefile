#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2001 Mar 26 (Mon) 15:12:22 by Harold Carr.
#

TOPDIR		=	..

PACKAGE		=	lavaProfile
PKGDIR		=	lavaProfile

FILES_java	= \
	$(PKGDIR)/F.java \
	$(PKGDIR)/Lava.java \
	$(PKGDIR)/LavaVersionImpl.java \
	$(PKGDIR)/ReplImpl.java

all :: classes 

SUBDIRS		=	compiler io runtime util

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
