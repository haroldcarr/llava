#
# Created       : 1999 Dec 15 (Wed) 16:30:21 by Harold Carr.
# Last Modified : 2004 Dec 08 (Wed) 17:14:12 by Harold Carr.
#

TOPDIR		=	.

PWD		=	$(shell pwd)
PWDCOLON	=	$(shell hcMakeDriveColon $(PWD))

#
# Subdirectories
#

SUBDIRS		=	lava lavaProfile testLava

all optimized debug clean clobber ::
	@for i in $(SUBDIRS) ; do \
	    echo ">>>Recursively making "$$i" "$@"..."; \
	    cd $$i; $(MAKE) $@ || exit 1; cd ..;  \
	    echo "<<<Finished Recursively making "$$i" "$@"." ; \
	done

c :
	cd testLava; $(MAKE) $@ || exit 1; cd ..;

#
# Create jar files.
#

JARDIR		=	$(PWDCOLON)/jars

$(JARDIR) : FORCE
	mkdir -p $@

scrub :: FORCE
	rm -rf $(JARDIR)

jr jar: $(JARDIR)
	cd $(CLASSDESTDIR); \
	$(JAR) -cvf $(JARDIR)/lava.jar lava libLava

jrs jarsrc: $(JARDIR)
	$(JAR) -cvf $(JARDIR)/lavasrc.jar lava libLava

#
# Create javadoc
#

JAVADOCDIR	=	$(PWDCOLON)/doc/javadoc

jd javadoc:
	$(JAVADOC) -verbose -d $(JAVADOCDIR) -sourcepath $(PWDCOLON) \
	lava \
	lava.comprun \
	lavaProfile.runtime.code \
	lavaProfile.compiler \
	lava.compiler.c1 \
	lavaProfile.runtime \
	lavaProfile.runtime.env \
	lava.lang \
	lava.io \
	lava.lang.types

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.

