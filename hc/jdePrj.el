;;;;
;;;; Created       : 1999 Dec 17 (Fri) 21:16:19 by Harold Carr.
;;;; Last Modified : 2001 Mar 26 (Mon) 16:42:11 by Harold Carr.
;;;;

(require 'hcJdeRip)

(defun hcJdeRip-sourcePathlistExtras (ps)
  (hcJde-makePath ps
		  "~/.sync/.lsync/lava/"
		  "~/.sync/.lsync/lava/testLava/"
		  ))

(defun hcJdeRip-classpathExtras (ps)
  (hcJde-makePath ps
		  "~/.sync/.lsync/lava/.classes"
		  "~/.sync/.lsync/lava/testLava"
		  "~/.sync/.lsync/lava/lib" ;; Necessary for libs
		  ))

(defun s ()
  (interactive)
  (hcJdeRip-shell "lavaProfile.Lava" ""))

(defun c ()
  (interactive)
  (hcJdeRip-shell "testLava.Test" ""))

(comment
(load-file "jdePrj.el")

stop in testLava.TestTop.testTop
stop in lavaProfile.runtime.code.CodeLiteral.<init>()
stop in lavaProfile.runtime.code.CodeLiteral.<init>(java.lang.Object, java.lang.Object)
stop in lavaProfile.runtime.procedure.primitive.lava.opt.PrimPairP.apply
stop in lavaProfile.runtime.procedure.primitive.lava.opt.PrimPairP.<init>
stop in lavaProfile.runtime.procedure.primitive.lava.PrimNewThread$LavaRunnable.run
stop in lava.lang.exceptions.LavaException newLavaException(java.lang.Throwable)
stop in lavaProfile.runtime.procedure.primitive.lava.PrimNewThread
stop in lavaProfile.runtime.procedure.primitive.lava.PrimNewThread$LavaThread.run
stop in lavaProfile.runtime.syntax.UserSyntax.compileprint 
stop in lavaProfile.runtime.code.CodeApplication.run
stop in lavaProfile.runtime.procedure.primitive.lava.PrimLoad.apply
stop in lavaProfile.runtime.procedure.primitive.lava.PrimStringAppend.apply
stop in lavaProfile.compiler.CompilerImpl.compile(java.lang.Object, lavaProfile.compiler.EnvironmentLexical, lava.runtime.LavaRuntime)
stop in lavaProfile.runtime.procedure.primitive.lava.PrimLoad.apply
stop in lava.Repl.eval
stop in lava.Repl.read(java.lang.String)
stop in lava.io.LavaReaderImpl.readToken

(load "hcChangeWords")
(defun is-ignored-p (full-path-and-name)
  (member (file-name-nondirectory full-path-and-name)
	  '("SCCS" "RCS")))
(defun is-ignored-suffix-p (full-path-and-name) nil)

(hcChangeWords
 '(
   ("case-eval-l" . "case-eval-r")
;   ("FR1" . "FR")
;   ("FC1" . "FC")
;   ("lavaProfile.compiler" . "lavaProfile.compiler")
;   ("libLava.r1" . "lavaProfile.runtime")
;   ("libLava.co" . "lava.compiler")
;   ("libLava.rt" . "lava.runtime")
;   ("define ." . "define _")
;   ("define-syntax ." . "define-syntax _")
;   (".%" . "_%")
;   ("\(." . "\(_")
;   ("\'." . "\'_")
;   ("\"." . "\"_")
   )
 "~/.sync/.lsync/lava"
 "~/.sync/.lsync/lava"
 t)


 '(
   ("(require 'java/import) ". "(require 'java/lang/import)")
   ("(require 'java/newArray) ". "(require 'java/lang/newArray)")
   ("(require 'corba/CosNaming) ". "(require 'java/corba/CosNaming)")
   ("(require 'corba/orb) ". "(require 'java/corba/orb)")
   )

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

