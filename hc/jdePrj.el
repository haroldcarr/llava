;;;;
;;;; Created       : 1999 Dec 17 (Fri) 21:16:19 by Harold Carr.
;;;; Last Modified : 2000 Apr 14 (Fri) 18:16:30 by .
;;;;

(require 'hcJdeRip)

(defun hcJdeRip-sourcePathlistExtras (ps)
  (hcJde-makePath ps
		  "~/.sync/.esync/java/"
		  "~/.sync/.esync/java/lava/"
		  ))

(defun hcJdeRip-classpathExtras (ps)
  (hcJde-makePath ps
		  "~/.sync/.esync/java/.classes"
		  "~/.sync/.esync/java/lava/.classes"
		  ))

(defun s ()
  (interactive)
  (hcJdeRip-shell "lava.Lava" ""))

(defun c ()
  (interactive)
  (hcJdeRip-shell "testLava.Test" ""))

(comment
(load-file "jdePrj.el")

;stop in testLava.TestTop.testTop
;stop in libLava.r1.code.CodeLiteral.<init>()
;stop in libLava.r1.code.CodeLiteral.<init>(java.lang.Object, java.lang.Object)
;stop in libLava.r1.procedure.primitive.lava.opt.PrimPairP.apply
;stop in libLava.r1.procedure.primitive.lava.opt.PrimPairP.<init>
;stop in libLava.r1.procedure.primitive.lava.PrimNewThread$LavaRunnable.run
;stop in lava.lang.exceptions.LavaException newLavaException(java.lang.Throwable)
;stop in libLava.r1.procedure.primitive.lava.PrimNewThread
;stop in libLava.r1.procedure.primitive.lava.PrimNewThread$LavaThread.run
;stop in libLava.r1.syntax.UserSyntax.compileprint 
;stop in libLava.r1.code.CodeApplication.run
;stop in libLava.r1.procedure.primitive.lava.PrimLoad.apply
;stop in libLava.r1.procedure.primitive.lava.PrimStringAppend.apply
;stop in libLava.c1.CompilerImpl.compile(java.lang.Object, libLava.c1.EnvironmentLexical, libLava.rt.LavaRuntime)
;stop in libLava.r1.procedure.primitive.lava.PrimLoad.apply
;stop in lava.Repl.eval
;stop in lava.Repl.read(java.lang.String)
;stop in lava.io.LavaReaderImpl.readToken

(load "hcChangeWords")
(defun is-ignored-p (full-path-and-name)
  (member (file-name-nondirectory full-path-and-name)
	  '("SCCS" "RCS")))
(defun is-ignored-suffix-p (full-path-and-name) nil)

(hcChangeWords
 '(("master". "leader")
   ("slave". "follower")
   )
 "~/rip/.ripsync/examples/corba/sync/InterSync"
 "~/rip/.ripsync/examples/corba/sync/InterSync"
 t)


(hcChangeWords
 '(("(require 'java/import) ". "(require 'java/lang/import)")
   ("(require 'java/newArray) ". "(require 'java/lang/newArray)")
   ("(require 'corba/CosNaming) ". "(require 'java/corba/CosNaming)")
   ("(require 'corba/orb) ". "(require 'java/corba/orb)")
   )
 "~/.sync/.esync/java/lava"
 "~/.sync/.esync/java/lava"
 t)


 '(
   ;;  Syntax
   (".begin" . "begin")
   (".define" . "define")
   (".lambda" . "lambda")
   (".quote" . "quote")

   ;; Procedures
   (".append" . "append")
   (".call/cc" . "call/cc")
   (".cons" . "cons")
   (".eval" . "eval")
   (".list" . "list")
   (".load" . "load")
   (".define-syntax" . "define-syntax")

   ;; UserSyntax
   (".map" . "map")
   (".cond" . "cond")
   (".or" . "or")
   (".and" . "and")
   (".not" . "not")
   (".quasiquote" . "quasiquote")
   (".unquoteSplicing" . "unquote-splicing")
   (".unquote" . "unquote")
   (".let" . "let")
   )
)

;;; End of file.

