# Copyright (c) 1997 - 2004 Harold Carr
#
# This work is licensed under the Creative Commons Attribution License.
# To view a copy of this license, visit 
#   http://creativecommons.org/licenses/by/2.0/
# or send a letter to
#   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
#-----------------------------------------------------------------------------

#
# Created       : 2000 Jan 18 (Tue) 04:28:19 by Harold Carr.
# Last Modified : 2004 Dec 10 (Fri) 21:07:44 by Harold Carr.
#

TOPDIR		=	../../..

PACKAGE		=	org.llava.derived
PKGDIR		=	org/llava/derived

# Note: Anything that uses let, let* or letrec MUST
# come AFTER DerivedLlavaBinding.

RESOURCE_FILES	= \
	$(PKGDIR)/DerivedJavaFirst.lva \
	$(PKGDIR)/DerivedScmTypeProcs.lva \
	$(PKGDIR)/DerivedLlavaControl.lva \
	$(PKGDIR)/DerivedLlavaConditional.lva \
	$(PKGDIR)/DerivedLlavaQuasiquote.lva \
	$(PKGDIR)/DerivedLlavaBinding.lva \
	$(PKGDIR)/DerivedLlavaIO.lva \
	$(PKGDIR)/DerivedLlavaCase.lva \
	$(PKGDIR)/DerivedLlavaIteration.lva \
	$(PKGDIR)/DerivedLlavaMember.lva \
	$(PKGDIR)/DerivedJavaTry.lva \
	$(PKGDIR)/DerivedJavaSecond.lva \
	$(PKGDIR)/DerivedLlavaDefineD.lva \
	$(PKGDIR)/DerivedScmVectors.lva \
	$(PKGDIR)/DerivedTest.lva \
	$(PKGDIR)/import.lva 

COLLECTED_RESOURCES	=	$(PKGDIR)/DerivedAll.lva

all :: resources resources_collected

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk
include $(hcMakefiles)/llavaRules.gmk

# End of file.

