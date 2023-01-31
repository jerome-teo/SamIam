##############################################################################
#
#  Sample Makefile for C++ applications
#    Works for single and multiple file programs.
#    for use with Unix/Linux and 
#    "A Computer Science Tapestry: 
#     Exploring Programming and Computer Science with C++" (second edition)
#     Owen Astrachan/McGraw-Hill
#
##############################################################################

-include auto_detect_platform.mak
-include ../../../../../auto_detect_platform.mak

# For use with multiple file projects consider 
# using 'make depend' to generate dependencies
#
# use /usr/ccs/bin/nm | /usr/ccs/bin/dump | /usr/bin/strings to analyze object files

##############################################################################
# Application specific variables
# EXEC is the name of the executable file
# SRC_FILES is a list of all source code files that must be linked
#           to create the executable
##############################################################################

ifdef __sun__
EXEC   	  = libcallsmile.so
endif

ifdef __linux__
EXEC   	  = libcallsmile.so
endif

ifdef __macosx__
EXEC   	  = libcallsmile.jnilib
endif

SRC_FILES = SMILEReader.cpp

##############################################################################
# Where to find course related files
# for CS machines
#

#LIBXML_DIR	 = /u/guest/keith/smile/smilexml/xml/package
#SMILE_INCLUDE	 = /u/guest/keith/smile/smilegcc2.95.1
#SMILE_INCLUDE	 = /u/guest/keith/smile/smilegcc6.2
#SMILEXML_INCLUDE = /u/guest/keith/smile/smilexml/xml/package

ifdef __sun__
LIB_DIR          = /u/guest/keith/smile/smilexml
SMILE_INCLUDE    = /u/guest/keith/smile/smilexml
JNI_DIR          = /usr/local/j2sdk1.4.2_12/include
SOLARIS_DIR      = /usr/local/j2sdk1.4.2_12/include/solaris
endif

ifdef __linux__
LIB_DIR		 = /home/keith/smile
SMILE_INCLUDE    = /home/keith/smile
JNI_DIR		 = /usr/java/j2sdk1.4.2_07/include
#JNI_DIR          = /usr/local/j2sdk1.4.1_01/include
SOLARIS_DIR      = /usr/java/j2sdk1.4.2_07/include/linux
#SOLARIS_DIR      = /usr/local/j2sdk1.4.1_01/include/linux
endif

ifdef __macosx__
#LIB_DIR          = /Users/kgc/source/smile
#SMILE_INCLUDE    = /Users/kgc/source/smile
LIB_DIR          = /u/guest/keith/mac/smile
SMILE_INCLUDE    = /u/guest/keith/mac/smile
#JNI_DIR          = /System/Library/Frameworks/JavaVM.framework/Versions/1.3.1/Headers
#SOLARIS_DIR      = /System/Library/Frameworks/JavaVM.framework/Versions/1.3.1/Headers
JNI_DIR          = /System/Library/Frameworks/JavaVM.framework/Headers
SOLARIS_DIR      = /System/Library/Frameworks/JavaVM.framework/Headers
endif

##############################################################################
# Compiler specifications
# These match the variable names given in /usr/share/lib/make/make.rules
# so that make's generic rules work to compile our files.
# gmake prefers CXX and CXXFLAGS for c++ programs
##############################################################################
# Which compiler should be used

ifdef __sun__
#	gcc 2.95 on the cs system
#CCC		= /usr/local/bin/g++

#	gcc 3.2 on the share drive, broken as of 9/11/03
#CCC		= /r/share1/src/gcc-3.2/gcc/g++

#	gcc 3.2 (9/17/03) on Mark Hopkins computer, built by Blai Bonet (2002)
#CCC		= /net/peking/caracas/usr/local/bin/g++

#       gcc 3.3.2 on effect.cs.ucla.edu (2/4/04)
#CCC		= /space/gcc/3.3.2/bin/g++-3.3.2

#       gcc 3.4.3 on effect.cs.ucla.edu (11/30/04)
CCC		= /space/usr/gcc/3.4.3/bin/g++
endif

ifdef __linux__
#       gcc 2.96 on solution.cs.ucla.edu (1/27/04)
CCC             = /usr/bin/g++

#       gcc 3.2.3 on solution.cs.ucla.edu (1/28/04)
#CCC             = /usr/keith/gcc/3.2.3/bin/g++-3.2.3

#       gcc 3.3.2 on solution.cs.ucla.edu (1/28/04)
#CCC             = /usr/keith/gcc/3.3.2/bin/g++-3.3.2
endif

ifdef __macosx__
CCC             = /usr/bin/g++
endif

CXX		= $(CCC)

# What flags should be passed to the compiler
#
#

#	gcc 3.3.1 optomization options (9/11/03)
# from http://gcc.gnu.org/onlinedocs/gcc-3.3.1/gcc/Optimize-Options.html#Optimize%20Options
# -Os
# Optimize for size. -Os enables all -O2 optimizations that do not typically increase code size. It also performs further optimizations designed to reduce code size.

#	gcc 3.3.2 options
#
# -fpic  If supported for the target machines, generate po-
#        sition-independent code,  suitable  for  use  in  a
#        shared library.
# -fPIC  If supported for the target machine, emit position-
#        independent code,  suitable  for  dynamic  linking,
#        even if branches need large displacements.

#attempt to deal with __eh_pc
#as described in:
#http://www.delorie.com/djgpp/v2faq/faq8_11.html
#
#EXTRA_CCFLAGS   = -O -fno-exceptions -fno-rtti

#EXTRA_CCFLAGS   = -Wall -O3
#DEBUG_LEVEL	= -g

ifdef __sun__
	EXTRA_CCFLAGS   = -Os
endif

ifdef __linux__
	EXTRA_CCFLAGS   = -O -fno-exceptions -fno-rtti
endif

ifdef __macosx__
        EXTRA_CCFLAGS   = -O -fno-exceptions -fno-rtti
endif

#CCFLAGS	= $(EXTRA_CCFLAGS) $(VERBOSE) $(DEBUG_LEVEL)
CCFLAGS		= $(EXTRA_CCFLAGS)

CXXFLAGS	= $(CCFLAGS)

# What flags should be passed to the C pre-processor
#   In other words, where should we look for files to include - note,
#   you should never need to include compiler specific directories here
#   because each compiler already knows where to look for its system
#   files (unless you want to override the defaults)

CPPFLAGS  	= -I. \
		  -I$(SMILE_INCLUDE) \
		  -I$(JNI_DIR) \
		  -I$(SOLARIS_DIR)

#                 -I$(LIB_DIR) \
#		  -I$(SMILEXML_INCLUDE) \

# What flags should be passed to the linker
#   In other words, where should we look for libraries to link with - note,
#   you should never need to include compiler specific directories here
#   because each compiler already knows where to look for its system files.

# gcc 3.3.1 link options (9/11/03)
# from http://gcc.gnu.org/onlinedocs/gcc-3.3.1/gcc/Link-Options.html#Link%20Options
# -s 
# Remove all symbol table and relocation information from the executable.
# -static
# On systems that support dynamic linking, this prevents linking with the shared libraries. On other systems, this option has no effect.
# -static-libgcc
# On systems that provide libgcc as a shared library, these options force the use of either the shared or static version respectively. If no shared version of libgcc was built when the compiler was configured, these options have no effect.
# -Xlinker option
# Pass option as an option to the linker. You can use this to supply system-specific linker options which GCC does not know how to recognize.

# ld options (Solaris) (9/11/03)
# from ld man page
#  -z defs
#  Force a fatal error if any undefined symbols remain at
#  the  end of the link. This is the default when an exe-
#  cutable is built. It is also useful  when  building  a
#  shared  object  to  assure  that  the  object is self-
#  contained, that is, that all its  symbolic  references
#  are resolved internally.

ifdef __sun__
	EXTRA_LDFLAGS   = -G -static-libgcc -Xlinker -Bstatic -Xlinker -z -Xlinker defs
endif

ifdef __linux__
	EXTRA_LDFLAGS   = -shared -Xlinker -Bstatic -Xlinker -zdefs
endif

ifdef __macosx__
	EXTRA_LDFLAGS   = -dynamiclib -static-libgcc -dynamic
endif

# -Xlinker -Dargs,detail
# -static-libgcc
# -Xlinker -Dsupport
# -nostdlib
# -G
# -Xlinker -b
# -Xlinker -zcombreloc
# -Xlinker -zdefs
# -Xlinker -Bstatic
# -Xlinker -s #strip!!

LDFLAGS		= -L. \
                  -L$(LIB_DIR) \
		  $(EXTRA_LDFLAGS)

#		  -L$(LIBXML_DIR) \

# What libraries should be linked with

ifdef __sun__
	LDLIBS		= -lsmilexml -lsmile
endif

ifdef __linux__
	LDLIBS		= -static -lsmilexml -lsmile
# /home/keith/smile/libsmile.so
# /home/keith/smile/libsmilexml.so
# -lsmile -lsmilexml
endif

ifdef __macosx__
#	LDLIBS		= -lsmile
	LDLIBS		= -lsmilexml -lsmile
#	LDLIBS		= -static -lsmilexml -lsmile
endif

# -lstdc++ -lm -lc
# -lgcc
# -lgcc -lstdc++ -lm -lc -lsmile
# -lsmilexml
# -static -lgcc -lstdc++
# -lsmile -lsmilexml -lgcc -lstdc++ -lm -lc -lg++

# All source files have associated object files
LIBOFILES		= $(LIB_FILES:%.cpp=%.o)
OFILES                  = $(SRC_FILES:%.cpp=%.o)

.SUFFIXES: .cpp

.cpp.o:
	$(LINK.cc) -c $<

.cpp:
	$(LINK.cc) $< -o $@ $(LDLIBS)



###########################################################################
# Additional rules make should know about in order to compile our files
###########################################################################
# all is the default rule
all	: $(EXEC)

# exec depends on the object files
$(EXEC) : $(OFILES)
	$(LINK.cc) $(OFILES) $(LDLIBS) -o $(EXEC)

# to use 'makedepend', the target is 'depend'
# uncomment the two lines below
depend:
	makedepend -- $(CPPFLAGS) -- -Y $(SRC_FILES) -fsmilereader.mak

# clean up after you're done
.PHONY: clean
clean	:
	-$(RM) -i $(OFILES) $(EXEC) core *.rpo


# DO NOT DELETE THIS LINE -- make depend depends on it.
