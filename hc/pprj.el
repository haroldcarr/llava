(require 'hcJde)

(defun repl ()
  (hcJde-debug
   "."
   "/export/home/carr/.sync/.lsync/lava/"
   (BOOTDIR)
   "-Duser.home=/export/home/carr"
   "/export/home/carr/.sync/.lsync/lava/.classes"
   ;;"testLava.Test"
   "lavaProfile.Lava"
   "1"
   ))

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
   (quote ("/export/home/carr/.sync/.lsync/lava/.classes")))

 '(jde-db-source-directories (quote ("/export/home/carr/.sync/.lsync/lava/")))
 '(jde-debugger (quote ("JDEbug" "" . "Executable")))
 ;'(jde-debugger (quote ("jdb" "" . "Executable")))

 '(jde-run-option-vm-args (quote ("-Duser.home=/export/home/carr")))
 ;'(jde-run-application-class "testLava.Test")
 '(jde-run-application-class "lavaProfile.Lava")
 '(jde-run-option-application-args (quote ("1" "two")))

 )





)

;;; End of file.
