#
# Created       : 2000 Jan 18 (Tue) 04:28:19 by Harold Carr.
# Last Modified : 2000 Feb 21 (Mon) 17:07:08 by Harold Carr.
#

TOPDIR		=	../../..

PACKAGE		=	libLava.r1.derived
PKGDIR		=	libLava/r1/derived

# N.B.: This order *MUST* be kept in sync with order in
#       EnvTopLevelInitImpl.

RESOURCE_FILES	= \
	$(PKGDIR)/DerivedJavaFirst.lva \
	$(PKGDIR)/DerivedScmTypeProcs.lva \
	$(PKGDIR)/DerivedLavaControl.lva \
	$(PKGDIR)/DerivedLavaConditional.lva \
	$(PKGDIR)/DerivedLavaQuasiquote.lva \
	$(PKGDIR)/DerivedLavaBinding.lva \
	$(PKGDIR)/DerivedLavaCase.lva \
	$(PKGDIR)/DerivedLavaIteration.lva \
	$(PKGDIR)/DerivedLavaMember.lva \
	$(PKGDIR)/DerivedJavaTry.lva \
	$(PKGDIR)/DerivedJavaSecond.lva \
	$(PKGDIR)/RequireProvide.lva \
	$(PKGDIR)/DerivedTest.lva

COLLECTED_RESOURCES	=	$(PKGDIR)/DerivedAll.lva

all :: resources resources_collected

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk
include $(hcMakefiles)/lavaRules.gmk

# End of file.

