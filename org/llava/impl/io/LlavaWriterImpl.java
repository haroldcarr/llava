;;;;
;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;  http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;  Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA.
;;;; -------------------------------------------------------------------------

;;;;
;;;; Created       : 2004 Dec 04 (Sat) 08:47:29 by Harold Carr.
;;;; Last Modified : 2004 Dec 08 (Wed) 08:59:54 by Harold Carr.
;;;;

(package org.openhc.write)

(define (write xx)
  (cond ((null? xx)
	 (display xx))

	((instanceof xx 'java.lang.String)
	 (display "\"")
	 (display xx)
	 (display "\""))

	((instanceof xx 'org.llava.lang.types.Pair)
	 (write-pair xx))

	((instanceof xx 'java.lang.Character)
	 (display "#\\")
	 (display xx))

	((isArray (getClass xx))
	 (display "#")
	 (write (vector->list xx)))

	(else
	 (display xx)))
  xx)

(define (write-pair pair)
  (cond ((and (not (null? (cdr pair)))
	      (memq (car pair) 
		    (list 'quote 'quasiquote 'unquote 'unquote-splicing)))
	 (display
	  (case (car pair)
	    ((quote)            "'")
	    ((quasiquote)       "`")
	    ((unquote)          ",")
	    ((unquote-splicing) ",@"))
	 )
	 (write (cadr pair)))
	(else
	 (display "(")
	 (write (car pair))
	 (write-list (cdr pair)))))

(define (write-list list)
  (cond ((null? list)
	 (display ")"))
	((not (pair? list))
	 (display " . ")
	 (write list)
	 (display ")"))
	(else
	 (display " ")
	 (write (car list))
	 (write-list (cdr list)))))

(-comment-
(import org.openhc.write)

(begin
(write (list 'a 3 3.4 null true false "foo" '(b . c)  #((1 . 2) #(3 4)) #\c
	     (new 'java.util.Hashtable)
	     '`(a ,b ,@(3 4))))
null)
)
	  
;;; End of file.

