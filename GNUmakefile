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
# Last Modified : 2004 Dec 08 (Wed) 17:14:12 by Harold Carr.
#

TOPDIR		=	.

PWD		=	$(shell pwd)
PWDCOLON	=	$(shell hcMakeDriveColon $(PWD))

DOC_DIR		  =	./doc
DOC_DIR_GENERATED =	$(DOC_DIR)/.generated
SITE_DIR	  =	../../haroldcarr.net/llavaorg

#
# Subdirectories
#

SUBDIRS		=	org test

all optimized debug clean clobber ::
	@for i in $(SUBDIRS) ; do \
	    echo ">>>Recursively making "$$i" "$@"..."; \
	    cd $$i; $(MAKE) $@ || exit 1; cd ..;  \
	    echo "<<<Finished Recursively making "$$i" "$@"." ; \
	done

c :
	cd test; $(MAKE) $@ || exit 1; cd ..;

site : copy-to-local-mirror upload

copy-to-local-mirror :
	cp -p ChangeLog $(SITE_DIR)
	cp -p COPYRIGHT.txt $(SITE_DIR)
	cp -p $(DOC_DIR)/index.html $(SITE_DIR)/index.html
	cp -p $(DOC_DIR_GENERATED)/llava-site.html $(SITE_DIR)/llava-site.html
	cp -p $(DOC_DIR_GENERATED)/package-index.html $(SITE_DIR)/package-index.html
	cp -p $(DOC_DIR_GENERATED)/procedure-index.html $(SITE_DIR)/procedure-index.html
	rm -f $(SITE_DIR)/LICENSE.*
	cp -p LICENSE.* $(SITE_DIR)
	cp -p TODO $(SITE_DIR)
	cp -p VERSION $(SITE_DIR)
	cp -p $(JARDIR)/llava-*    $(SITE_DIR)/archive
	cp -p $(JARDIR)/llava[!-]* $(SITE_DIR)

upload :
	(cd $(SITE_DIR)/../.manage; make u)
                  
docs : 
	(cd $(DOC_DIR); $(MAKE))

LLAVAVERSION	= $(JAVA) -classpath $(CLASSDESTDIR) org.llava.impl.LlavaVersionImpl

FILES_java	= \
	org/llava/impl/LlavaVersionImpl.java

makeVersion : classes
	`$(LLAVAVERSION) > VERSION`
	`$(LLAVAVERSION) dateOnly > VERSIONDATEONLY`

release : clean scrub makeVersion zip-src zip-lib all c jar docs release_end

release_end :
	date=`$(LLAVAVERSION) dateOnly`; \
	zip -9 $(JARDIR)/llava.zip $(JARDIR)/llava.jar; \
	cp $(JARDIR)/llava.zip $(JARDIR)/llava-$$date.zip; \
	cp $(JARDIR)/llava.jar $(JARDIR)/llava-$$date.jar; \
	cp $(JARDIR)/llavalib.zip $(JARDIR)/llava-$$date-lib.zip; \
	cp $(JARDIR)/llavasrc.zip $(JARDIR)/llava-$$date-src.zip
	-@echo "Release `cat VERSION` complete."

#
# Create jar files.
#

JARDIR		=	$(PWDCOLON)/jars

$(JARDIR) : FORCE
	mkdir -p $@

scrub :: FORCE
	rm -rf $(JARDIR)

jar: $(JARDIR)
	cd $(CLASSDESTDIR); \
	$(JAR) -cvf $(JARDIR)/llava.jar org

zip-lib: $(JARDIR)
	TMPFILE=/tmp/flist; \
	rm -f $TMPFILE; \
	touch $TMPFILE; \
	find ./org/llava/lib -name "*" -print >> $TMPFILE; \
	zip -9 $(JARDIR)/llavalib.zip -@ <$TMPFILE; \
	rm -f $TMPFILE

zip-src: $(JARDIR)
	TMPFILE=/tmp/flist; \
	rm -f $TMPFILE; \
	touch $TMPFILE; \
	find . -name "*" -print >> $TMPFILE; \
	zip -9 $(JARDIR)/llavasrc.zip -@ <$TMPFILE; \
	rm -f $TMPFILE

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.

