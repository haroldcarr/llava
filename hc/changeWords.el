(load "hcChangeWords")
(defun is-ignored-p (full-path-and-name)
  (member (file-name-nondirectory full-path-and-name)
	  '("SCCS" "RCS")))
(defun is-ignored-suffix-p (full-path-and-name) nil)
;(setq case-fold-search nil)
;(setq default-case-fold-search nil)

(hcChangeWords
 '(
   ("org.llava.F" . "org.llava.F")
  )
  "~/.sync/.llava.org/.system"
  "~/.sync/.llava.org/.system"
 t)

;;; End of file.
