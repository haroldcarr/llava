#
# Created       : 2004 Aug 24 (Tue) 18:23:06 by Harold Carr.
# Last Modified : 2004 Nov 26 (Fri) 09:29:57 by Harold Carr.
#

TEMP		=	./
GENERATED	=	./.generated

LastModifiedTxt = LastModified.txt

LAST_MODIFIED_FILES = \
	$(TEMP)/ch01-QuickStart.xml \
	$(TEMP)/llavasite.xml

TRANSFORMFILES = \
	$(GENERATED)/llavasite.html

all : $(LastModifiedTxt) transform

$(LastModifiedTxt) : $(LAST_MODIFIED_FILES)
	rm -f $(LastModifiedTxt)
	echo "Last Modified : `date` by `whoami`." > $(LastModifiedTxt)

update-site : FORCE
	cp $(GENERATED)/llavasite.html ../../index.html

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/docbookDefs.gmk
include $(hcMakefiles)/docbookRules.gmk

# End of file.
