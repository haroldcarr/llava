(load "hcChangeWords")
(defun is-ignored-p (full-path-and-name)
  (member (file-name-nondirectory full-path-and-name)
	  '("SCCS" "RCS")))
(defun is-ignored-suffix-p (full-path-and-name) nil)
;(setq case-fold-search nil)
;(setq default-case-fold-search nil)

(hcChangeWords
 '(
   ("org.llava.lib.Exceptions" . "org.llava.lib.Exception")
   ("org.llava.lib.Lists"    . "org.llava.lib.List")
   ("org.llava.lib.Macros"   . "org.llava.lib.Macro")
   ("org.llava.lib.Strings" . "org.llava.lib.String")
   ("org.llava.lib.Threads" . "org.llava.lib.Thread")
   ("org.llava.lib.Vectors"   . "org.llava.lib.Vector")
   ("org.llava.lib.cl.Macros"  . "org.llava.lib.cl.Macro")
   ("org.llava.lib.cl.Sequences"  . "org.llava.lib.cl.Sequence")
   ("org.llava.lib.cl.Symbols"  . "org.llava.lib.cl.Symbol")
   ("org.llava.lib.scm.Lists"  . "org.llava.lib.scm.List")
   ("org.llava.lib.scm.Predicates"  . "org.llava.lib.scm.Predicate")
   ("org.llava.lib.scm.Strings"  . "org.llava.lib.scm.String")
  )
 "~/.sync/.esync"
 "~/.sync/.esync"
 t)

;  "~/.sync/.llava.org/.system"
;  "~/.sync/.llava.org/.system"


;;; End of file.
