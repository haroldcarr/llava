;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;   http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA
;;;;---------------------------------------------------------------------------

;;;;
;;;; Created       : 2004 Dec 05 (Sun) 08:17:24 by Harold Carr.
;;;; Last Modified : 2004 Dec 07 (Tue) 18:23:25 by Harold Carr.
;;;;

(defun hcLlavaCmdVmArgsExtras    (ps) 
  (concat
   ""
   ;;" -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,server=y,suspend=y"
   ))

(defun hcLlavaCmdMainClass       (ps) 
  "org.llava.Llava"
  ;;"test.Test"
  )

(comment
(load-file "jswatPrj.el")
)

;;; End of file.
