#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2001 Mar 26 (Mon) 14:06:52 by Harold Carr.
#

TOPDIR		=	../..

PACKAGE		=	lava.runtime
PKGDIR		=	lava/runtime

FILES_java	= \
	$(PKGDIR)/EnvironmentTopLevel.java \
	$(PKGDIR)/Evaluator.java \
	$(PKGDIR)/EnvTopLevelInit.java \
	$(PKGDIR)/LavaRuntime.java \
	$(PKGDIR)/UndefinedIdHandler.java

all :: classes

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.
