# Copyright (c) 1997 - 2004 Harold Carr
#
# This work is licensed under the Creative Commons Attribution License.
# To view a copy of this license, visit 
#   http://creativecommons.org/licenses/by/2.0/
# or send a letter to
#   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
#-----------------------------------------------------------------------------

#
# Created       : 2004 Nov 30 (Tue) 22:46:11 by Harold Carr.
# Last Modified : 
#

TOPDIR		=	../../..

PACKAGE		=	org.llava.lib
PKGDIR		=	org/llava/lib

RESOURCE_FILES	= \
	$(PKGDIR)/Control.lva \
	$(PKGDIR)/Exceptions.lva \
	$(PKGDIR)/Files.lva \
	$(PKGDIR)/Lists.lva \
	$(PKGDIR)/LlavaDoc.lva \
	$(PKGDIR)/Macros.lva \
	$(PKGDIR)/Program.lva \
	$(PKGDIR)/SimpleRecord.lva \
	$(PKGDIR)/Strings.lva \
	$(PKGDIR)/Threads.lva \
	$(PKGDIR)/Vectors.lva \
	$(PKGDIR)/cl/Control.lva \
	$(PKGDIR)/cl/Macros.lva \
	$(PKGDIR)/cl/Sequences.lva \
	$(PKGDIR)/cl/Symbols.lva \
	$(PKGDIR)/io/FileWalker.lva \
	$(PKGDIR)/java/Lang.lva \
	$(PKGDIR)/reflect/Namespace.lva \
	$(PKGDIR)/reflect/Reflect.lva \
	$(PKGDIR)/scm/Lists.lva \
	$(PKGDIR)/scm/Predicates.lva \
	$(PKGDIR)/scm/Strings.lva \
	$(PKGDIR)/test/Test.lva \
	$(PKGDIR)/xml/L2X.lva \
	$(PKGDIR)/xml/XSLT.lva

all :: resources

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk
include $(hcMakefiles)/llavaRules.gmk

# End of file.

