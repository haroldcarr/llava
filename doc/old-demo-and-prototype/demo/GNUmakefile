#
# Created       : 1999 Nov 16 (Tue) 20:12:28 by Harold Carr.
# Last Modified : 1999 Nov 17 (Wed) 19:20:44 by Harold Carr.
#

TOPDIR		=	../..

PKGDIR		=	hc/lava

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

