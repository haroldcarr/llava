#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2000 Feb 15 (Tue) 21:24:46 by Harold Carr.
#

TOPDIR		=	../..

PACKAGE		=	libLava.rt
PKGDIR		=	libLava/rt

FILES_java	= \
	$(PKGDIR)/EnvironmentTopLevel.java \
	$(PKGDIR)/Evaluator.java \
	$(PKGDIR)/EnvTopLevelInit.java \
	$(PKGDIR)/FR.java \
	$(PKGDIR)/LavaRuntime.java \
	$(PKGDIR)/UndefinedIdHandler.java

all :: classes

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.
