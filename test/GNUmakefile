# Copyright (c) 1997 - 2004 Harold Carr
#
# This work is licensed under the Creative Commons Attribution License.
# To view a copy of this license, visit 
#   http://creativecommons.org/licenses/by/2.0/
# or send a letter to
#   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
#-----------------------------------------------------------------------------


#
# Created       : 1999 Dec 18 (Sat) 16:15:20 by Harold Carr.
# Last Modified : 2004 Sep 03 (Fri) 15:39:58 by Harold Carr.
#

TOPDIR		=	..

PACKAGE		=	test
PKGDIR		=	test

# The CLIENT name is misleading.
# I am reusing some of my CORBA client/server rules.

#OCLIENT		=	$(PACKAGE).Test
CLIENT		=	$(PACKAGE).Test

FILES_java	= \
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

