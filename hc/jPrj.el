;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;   http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA
;;;;---------------------------------------------------------------------------

;;;;
;;;; Created       : 1999 Dec 17 (Fri) 21:16:19 by Harold Carr.
;;;; Last Modified : 2004 Dec 07 (Tue) 18:23:17 by Harold Carr.
;;;;

(require 'hcJde)

(defvar baseDir "~/.sync/.llava.org/.system")

(defun hcJdeRipCommon-sourcePathlistExtras (ps)
  (hcMakePath ps
	      (concat baseDir "/")
	      (concat baseDir "/testLlava/")
	      ))

(defun hcJdeRipCommon-classpathExtras (ps)
  (hcMakePath ps
	      (concat baseDir "/.classes")
	      (concat baseDir "/lib") ;; Necessary for libs
	      ))

(defun s ()
  (interactive)
  (hcJdeRip-shell "org.llava.Llava" ""))

(defun c ()
  (interactive)
  (hcJdeRip-shell "test.Test" ""))

(comment
(load-file "jPrj.el")
)

;;; End of file.
