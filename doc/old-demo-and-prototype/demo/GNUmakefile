# Copyright (c) 1997 - 2004 Harold Carr
#
# This work is licensed under the Creative Commons Attribution License.
# To view a copy of this license, visit 
#   http://creativecommons.org/licenses/by/2.0/
# or send a letter to
#   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
#-----------------------------------------------------------------------------


# Created       : 1999 Nov 16 (Tue) 20:12:28 by Harold Carr.
# Last Modified : 2004 Sep 03 (Fri) 15:38:40 by Harold Carr.


TOPDIR		=	../..

PKGDIR		=	hc/llava

FILES_java	= \
	$(PKGDIR)/Baz.java \
	$(PKGDIR)/Foo.java \
	$(PKGDIR)/FooImplGen.java \
	$(PKGDIR)/SomeException.java \
	$(PKGDIR)/Wombat.java

all :: classes

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.

