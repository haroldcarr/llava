#
# Created       : 2004 Aug 24 (Tue) 18:23:06 by Harold Carr.
# Last Modified : 2004 Nov 27 (Sat) 18:11:22 by Harold Carr.
#

# TODO: <hr> -> <br>
# DB var names - DB_TEMP to DB_XML_SRC

# Next two reletive to ../

DOC_IN_DIR	=	./org
DOC_OUT_DIR	=	./doc/.generated

L2X_IN_DIR	=	./
L2X_OUT_DIR	=	/tmp

DB_IN_DIR	=	./book
DB_OUT_DIR	=	./.generated

L2X_FILES	= \
	$(L2X_OUT_DIR)/make-package-index.xml \
	$(L2X_OUT_DIR)/make-procedure-index.xml

DB_FILES	= \
	$(DB_OUT_DIR)/llava-site.html

LastModifiedTxt = $(DB_OUT_DIR)/LastModified.txt

LAST_MODIFIED_FILES = \
	$(DB_IN_DIR)/ap-Motivation.xml \
	$(DB_IN_DIR)/ch-Download.xml \
	$(DB_IN_DIR)/llava-site.xml

all : $(LastModifiedTxt) cp-index llava-doc force-db db sed-db force-pi pi

$(LastModifiedTxt) : $(DB_OUT_DIR) $(LAST_MODIFIED_FILES)
	rm -f $(LastModifiedTxt)
	echo "Last Modified : `date` by `whoami`." > $(LastModifiedTxt)

cp-index : FORCE
	cp -p index.html $(DB_OUT_DIR)

ld llava-doc: FORCE
	rm -rf $(DB_OUT_DIR)/org
	(cd ..; hcLlavaDoc $(DOC_IN_DIR) $(DOC_OUT_DIR))

fdb force-db : FORCE
	rm -f $(DB_FILES)

# REVISIT - how to make $$ into process number

sed-db : FORCE
	rm -f /tmp/sed-db.$$
	mv $(DB_OUT_DIR)/llava-site.html /tmp/sed-db.$$
	cat /tmp/sed-db.$$ | sed 's/<hr>/<br>/g' > $(DB_OUT_DIR)/llava-site.html

force-pi : FORCE
	rm -f $(DB_OUT_DIR)/package-index.html \
	      $(DB_OUT_DIR)/procedure-index.html

pi : $(DB_OUT_DIR)/package-index.html $(DB_OUT_DIR)/procedure-index.html

$(DB_OUT_DIR)/package-index.html : $(DB_IN_DIR)/llava-site.xml \
			           $(L2X_OUT_DIR)/make-package-index.xml
	$(XALAN) -IN  $(DB_IN_DIR)/llava-site.xml \
		 -XSL $(L2X_OUT_DIR)/make-package-index.xml \
		 -OUT $@

$(DB_OUT_DIR)/procedure-index.html : $(DB_IN_DIR)/llava-site.xml \
			             $(L2X_OUT_DIR)/make-procedure-index.xml
	$(XALAN) -IN  $(DB_IN_DIR)/llava-site.xml \
		 -XSL $(L2X_OUT_DIR)/make-procedure-index.xml \
		 -OUT $@

clean : FORCE
	rm -rf $(DB_OUT_DIR)

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/docbookDefs.gmk
include $(hcMakefiles)/docbookRules.gmk
include $(hcMakefiles)/l2xRules.gmk

# End of file.
