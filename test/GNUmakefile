#
# Created       : 1999 Dec 18 (Sat) 16:15:20 by Harold Carr.
# Last Modified : 2000 Oct 23 (Mon) 18:06:53 by .
#

TOPDIR		=	..

PACKAGE		=	testLava
PKGDIR		=	testLava

# The CLIENT name is misleading.
# I am reusing some of my CORBA client/server rules.

#OCLIENT		=	$(PACKAGE).Test
CLIENT		=	$(PACKAGE).Test

FILES_java	= \
	$(PKGDIR)/Namespace.java \
	$(PKGDIR)/Test.java \
	$(PKGDIR)/TestCompilerAndEngine.java \
	$(PKGDIR)/TestEnv.java \
	$(PKGDIR)/TestGeneric.java \
	$(PKGDIR)/TestReader.java \
	$(PKGDIR)/TestReflection.java \
	$(PKGDIR)/TestTop.java 

all :: classes

hcMakefiles=$(shell hcMakefiles)
#include $(hcMakefiles)/javaDefs.gmk
#include $(hcMakefiles)/javaRules.gmk
# Following just to get "c" rule
include $(hcMakefiles)/ripDefs.gmk
include $(hcMakefiles)/ripRules.gmk

# End of file.

