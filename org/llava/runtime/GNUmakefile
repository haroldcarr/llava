# Copyright (c) 1997 - 2004 Harold Carr
#
# This work is licensed under the Creative Commons Attribution License.
# To view a copy of this license, visit 
#   http://creativecommons.org/licenses/by/2.0/
# or send a letter to
#   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
#-----------------------------------------------------------------------------

#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2004 Dec 07 (Tue) 20:06:32 by Harold Carr.
#

TOPDIR		=	../../..

PACKAGE		=	org.llava.runtime
PKGDIR		=	org/llava/runtime

FILES_java	= \
	$(PKGDIR)/ActivationFrame.java \
	$(PKGDIR)/BacktraceHandler.java \
	$(PKGDIR)/EnvironmentTopLevel.java \
	$(PKGDIR)/Evaluator.java \
	$(PKGDIR)/EnvTopLevelInit.java \
	$(PKGDIR)/LlavaRuntime.java \
	$(PKGDIR)/Namespace.java \
	$(PKGDIR)/UndefinedIdHandler.java

all :: classes

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.
