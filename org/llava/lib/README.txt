;;;;
;;;; Created       : 1999 Feb 13 (Sat) 18:16:40 by Harold Carr.
;;;; Last Modified : 2000 Feb 21 (Mon) 17:08:56 by Harold Carr.
;;;;

REVISIT - make macro definitions hygenic.

cl
	program		(eg defun)
	predicates	(eg consp, numberp)
	control		(eg setq typecase dotimes mapcar values)
	macros		(eg defmacro
	symbols		(eg symbol-name make-symbol gensym gentemp)
	numbers
	characters
	sequences
	lists
	hashTables
	arrays
	strings
	structures
	evaluator
	streams
	io
	fileSystem
	errors
	loop
	pp
	clos
	conditions
	series
	generatorsAndGathers
	backquote
		
java
	corba
	jini
	jndi
	lang		(eg newArray import)
	rmi

lava
	program
	arrays
	control		(eg aif map3)
	macros		(eg with-gensyms)
	symbols
	strings		(eg map-toString)
	fileSystem
	errors	

scm
	equivalence	(eg eqv?)
	numbers		(eg number? integer? zero? max number->string)
	booleans	(eg boolean?)
	lists		(eg set-car! list? list-tail memq assq)
	symbols		(eg symbol? symbol->string)
	characters	(eg char? char=? char-alphabetic? char->integer char-upcase)
	strings		(eg string? make-string string-ref substring string->list)
	vectors		(eg vector? make-vector vector-ref vector->list)
	control		(eg procedure? map for-each call/cc values
			    call-with-values dynamic-wind
			    eval interaction-environment)
	io		(eg call-with-input-file input-port?
			    current-input-port with-input-from-file
			    write ("foo")
			    display (foo)
			    newline)

;;; End of file.

