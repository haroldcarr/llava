# Copyright (c) 1997 - 2004 Harold Carr
#
# This work is licensed under the Creative Commons Attribution License.
# To view a copy of this license, visit 
#   http://creativecommons.org/licenses/by/2.0/
# or send a letter to
#   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
#-----------------------------------------------------------------------------


#SHELL		= /bin/sh
#SHELL		= /mksnt/sh

srcdir		= .
top_srcdir 	= ../..

JAVA 		= java
JAVAC 		= javac
JCFLAGS 	= -g

SUFFIXES 	= .java .class
.SUFFIXES:
.SUFFIXES: .class .java

OBJ		= \
	Except.class \
	Redef.class \
	TheRestOfIt.class \
	Undef.class

# Following only works for GNU make, but is only needed for make dist or TAGS.
SOURCES = $(OBJ:.class=.java)

CLEANFILES 	= *.class

CLASSPATH = /usr/local/kawa/kawa-1.6.1;/usr/local/java/jdk1.1.5/lib/classes.zip;

.java.class:
	$(JAVAC) $(JCFLAGS) -classpath "$(CLASSPATH)" $<

all: $(OBJ)

test: test-Except test-Redef test-TheRestOfIt test-Undef

test-Except: Except.class
	$(JAVA) -classpath "$(CLASSPATH)" Except

test-Redef: Redef.class
	$(JAVA) -classpath "$(CLASSPATH)" Redef

test-TheRestOfIt: TheRestOfIt.class
	$(JAVA) -classpath "$(CLASSPATH)" TheRestOfIt

test-Undef: Undef.class
	$(JAVA) -classpath "$(CLASSPATH)" Undef

clean:
	rm -f $(CLEANFILES)

# Tell versions [3.59,3.63) of GNU make to not export all variables.
# Otherwise a system limit (for SysV at least) may be exceeded.
.NOEXPORT:

### End of file.
