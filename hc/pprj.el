;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;   http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA
;;;;---------------------------------------------------------------------------


(require 'hcJde)

(defun hcJdeRipCommon-sourcePathlistExtras (ps)
  (hcMakePath ps
	      "/export/home/carr/.sync/.llava.org/.system/"))

(defun hcJdeRipCommon-classpathExtras (ps)
  (hcMakePath ps
	      "/export/home/carr/.sync/.llava.org/.system/.classes"))

(defun hcJdeRipCommon-vmArgsExtras ()
  (concat
   " -Duser.home=/export/home/carr"))

(defun test ()
  (interactive)
  (hcJdeRip-shell "test.Test" "dummyArg"))

(defun repl ()
  (interactive)
  (hcJdeRip-shell "org.llava.Llava" "dummyArg"))

(comment
(load-file "pprj.el")
(repl)

(custom-set-variables

 '(semanticdb-default-save-directory "/tmp")
 '(jde-project-context-switching-enabled-p nil)
 '(jde-expand-classpath-p nil)

 `(jde-jdk-registry (quote (("1.4.0" . ,(BOOTDIR)))))
 '(jde-jdk (quote ("1.4.0")))

 '(jde-global-classpath
   (quote ("/export/home/carr/.sync/.lsync/llava/.classes")))

 '(jde-db-source-directories (quote ("/export/home/carr/.sync/.lsync/llava/")))
 '(jde-debugger (quote ("JDEbug" "" . "Executable")))
 ;'(jde-debugger (quote ("jdb" "" . "Executable")))

 '(jde-run-option-vm-args (quote ("-Duser.home=/export/home/carr")))
 ;'(jde-run-application-class "testLlava.Test")
 '(jde-run-application-class "org.llava.Llava")
 '(jde-run-option-application-args (quote ("1" "two")))

 )





)

;;; End of file.
