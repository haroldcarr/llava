# Copyright (c) 1997 - 2004 Harold Carr
#
# This work is licensed under the Creative Commons Attribution License.
# To view a copy of this license, visit 
#   http://creativecommons.org/licenses/by/2.0/
# or send a letter to
#   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
#-----------------------------------------------------------------------------

#
# Created       : 2002 Oct 14 (Mon) 09:32:58 by Harold Carr.
# Last Modified : 2004 Sep 04 (Sat) 08:21:08 by Harold Carr.
#

TOPDIR		=	..

PWD		=	$(shell pwd)
PWDCOLON	=	$(shell hcMakeDriveColon $(PWD))

#
# Subdirectories
#

SUBDIRS		=	llava

all optimized debug clean clobber ::
	@for i in $(SUBDIRS) ; do \
	    echo ">>>Recursively making "$$i" "$@"..."; \
	    cd $$i; $(MAKE) $@ || exit 1; cd ..;  \
	    echo "<<<Finished Recursively making "$$i" "$@"." ; \
	done

hcMakefiles=$(shell hcMakefiles)
include $(hcMakefiles)/javaDefs.gmk
include $(hcMakefiles)/javaRules.gmk

# End of file.

