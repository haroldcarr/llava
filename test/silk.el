;;;;
;;;; Created       : 2000 Jan 12 (Wed) 05:32:38 by Harold Carr.
;;;; Last Modified : 2000 Jan 12 (Wed) 05:36:56 by Harold Carr.
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

