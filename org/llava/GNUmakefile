#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2001 Mar 26 (Mon) 15:12:06 by Harold Carr.
#

TOPDIR		=	..

PACKAGE		=	lava
PKGDIR		=	lava

FILES_java	= \
	$(PKGDIR)/LavaVersion.java \
	$(PKGDIR)/Repl.java \

all :: classes 

SUBDIRS		=	compiler io lang runtime

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

