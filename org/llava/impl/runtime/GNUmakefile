#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2000 Jan 29 (Sat) 15:48:32 by Harold Carr.
#

TOPDIR		=	../..

PACKAGE		=	libLava.r1
PKGDIR		=	libLava/r1

FILES_java	= \
	$(PKGDIR)/Engine.java \
	$(PKGDIR)/EngineImpl.java \
	$(PKGDIR)/EngineStepImpl.java \
	$(PKGDIR)/FR1.java \
	$(PKGDIR)/EnvTopLevelInitImpl.java \
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

