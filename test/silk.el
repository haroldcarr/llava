;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;   http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA
;;;;---------------------------------------------------------------------------

;;;;
;;;; Created       : 2000 Jan 12 (Wed) 05:32:38 by Harold Carr.
;;;; Last Modified : 2004 Dec 07 (Tue) 20:40:27 by Harold Carr.
;;;;

(require 'hcJdeRip)

(defun hcJdeRip-sourcePathlistExtras (ps)
  (hcJde-makePath ps
		  "d:/usr/local/hc/java/silk/v3.0-99-10-31/silk/src/"
		  ))

(defun hcJdeRip-classpathExtras (ps)
  (hcJde-makePath ps
		  "d:/usr/local/hc/java/silk/v3.0-99-10-31/silk/jar/scheme.jar"
		  ))

(defun dsilk ()
  (interactive)
  (hcJdeRip-shell "silk.Scheme" "bq.silk"))

(comment
(load-file "silk.el")
)

;;; End of file.

