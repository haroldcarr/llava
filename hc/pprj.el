(require 'hcJde)

(defun repl ()
  (hcJde-debug
   "."
   "/export/home/carr/.sync/.lsync/lava"
   (BOOTDIR)
   "-Duser.home=/export/home/carr"
   "/export/home/carr/.sync/.lsync/lava/.classes"
   ;;"testLava.Test"
   "lavaProfile.Lava"
   "1"
   ))

(comment
(load-library "hcJde.el")
(load-file "pprj.el")

(repl)

  (hcJde-debug
   "."
   "/export/home/carr/.sync/.lsync/lava"
   (BOOTDIR)
   "-Duser.home=/export/home/carr"
   "/export/home/carr/.sync/.lsync/lava/.classes"
   ;;"testLava.Test"
   "lavaProfile.Lava"
   "1 two")

(custom-set-variables

 '(jde-global-classpath (quote ("/export/home/carr/.sync/.lsync/lava/.classes")))

 `(jde-run-java-vm ,(concat (BOOTDIR) "/bin/java"))
 '(jde-run-option-vm-args (quote ("-Duser.home=/export/home/carr")))
 '(jde-run-option-properties (quote (("Name" . "value"))))

 '(jde-run-application-class "testLava.Test")
 ;;'(jde-run-application-class "lavaProfile.Lava")
 '(jde-run-option-application-args (quote ("1" "two")))


 '(jde-db-debugger (quote ("JDEbug" "" . "Executable")))
 '(jde-bug-vm-includes-jpda-p t)
 ;;To find tool.jar which contains JPDA classes.
 `(jde-bug-jdk-directory ,(concat (BOOTDIR) "/"))
 ;;'(jde-db-debugger (quote ("jdb" "" . "Executable")))

 '(jde-db-source-directories (quote ("/export/home/carr/.sync/.lsync/lava/")))

 )

(custom-set-variables
 '(jde-project-context-switching-enabled-p nil)
 '(jde-run-application-class "testLava.Test")
 '(semanticdb-default-save-directory "/tmp")
 '(jde-debugger (quote ("JDEbug" "" . "Executable")))
 '(jde-db-source-directories (quote ("/export/home/carr/.sync/.lsync/lava/")))
 '(jde-run-option-application-args (quote ("1" "two")))
 '(jde-run-option-vm-args (quote ("-Duser.home=/export/home/carr")))
 '(jde-expand-classpath-p nil)
 '(jde-jdk (quote ("1.4")))
 '(jde-global-classpath (quote ("/export/home/carr/.sync/.lsync/lava/.classes")))
 `(jde-jdk-registry (quote (("1.4.0" . ,(BOOTDIR)))))
)





)

;;; End of file.
