#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2001 Mar 26 (Mon) 14:56:46 by Harold Carr.
#

TOPDIR		=	../..

PACKAGE		=	lavaProfile.runtime
PKGDIR		=	lavaProfile/runtime

FILES_java	= \
	$(PKGDIR)/Engine.java \
	$(PKGDIR)/EngineImpl.java \
	$(PKGDIR)/EngineStepImpl.java \
	$(PKGDIR)/EnvTopLevelInitImpl.java \
	$(PKGDIR)/FR.java \
	$(PKGDIR)/LavaRuntimeImpl.java

all :: classes

SUBDIRS		=	code derived exceptions env procedure syntax

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

