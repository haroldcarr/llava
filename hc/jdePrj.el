;;;; Copyright (c) 1997 - 2004 Harold Carr
;;;;
;;;; This work is licensed under the Creative Commons Attribution License.
;;;; To view a copy of this license, visit 
;;;;   http://creativecommons.org/licenses/by/2.0/
;;;; or send a letter to
;;;;   Creative Commons, 559 Nathan Abbott Way, Stanford, California 94305, USA
;;;;---------------------------------------------------------------------------


;;;;
;;;; Created       : 1999 Dec 17 (Fri) 21:16:19 by Harold Carr.
;;;; Last Modified : 2004 Sep 03 (Fri) 15:45:53 by Harold Carr.
;;;;

(require 'hcJdeRip)

(defun hcJdeRip-sourcePathlistExtras (ps)
  (hcJde-makePath ps
		  "~/.sync/.lsync/llava/"
		  "~/.sync/.lsync/llava/testLlava/"
		  ))

(defun hcJdeRip-classpathExtras (ps)
  (hcJde-makePath ps
		  "~/.sync/.lsync/llava/.classes"
		  "~/.sync/.lsync/llava/testLlava"
		  "~/.sync/.lsync/llava/lib" ;; Necessary for libs
		  ))

(defun s ()
  (interactive)
  (hcJdeRip-shell "llavaProfile.Llava" ""))

(defun c ()
  (interactive)
  (hcJdeRip-shell "testLlava.Test" ""))

(comment
(load-file "jdePrj.el")

stop in testLlava.TestTop.testTop
stop in llavaProfile.runtime.code.CodeLiteral.<init>()
stop in llavaProfile.runtime.code.CodeLiteral.<init>(java.lang.Object, java.lang.Object)
stop in llavaProfile.runtime.procedure.primitive.llava.opt.PrimPairP.apply
stop in llavaProfile.runtime.procedure.primitive.llava.opt.PrimPairP.<init>
stop in llavaProfile.runtime.procedure.primitive.llava.PrimNewThread$LlavaRunnable.run
stop in llava.lang.exceptions.LlavaException newLlavaException(java.lang.Throwable)
stop in llavaProfile.runtime.procedure.primitive.llava.PrimNewThread
stop in llavaProfile.runtime.procedure.primitive.llava.PrimNewThread$LlavaThread.run
stop in llavaProfile.runtime.syntax.UserSyntax.compileprint 
stop in llavaProfile.runtime.code.CodeApplication.run
stop in llavaProfile.runtime.procedure.primitive.llava.PrimLoad.apply
stop in llavaProfile.runtime.procedure.primitive.llava.PrimStringAppend.apply
stop in llavaProfile.compiler.CompilerImpl.compile(java.lang.Object, llavaProfile.compiler.EnvironmentLexical, llava.runtime.LlavaRuntime)
stop in llavaProfile.runtime.procedure.primitive.llava.PrimLoad.apply
stop in llava.Repl.eval
stop in llava.Repl.read(java.lang.String)
stop in llava.io.LlavaReaderImpl.readToken

;==============================================================================

(load "hcChangeWords")
(defun is-ignored-p (full-path-and-name)
  (member (file-name-nondirectory full-path-and-name)
	  '("SCCS" "RCS")))
(defun is-ignored-suffix-p (full-path-and-name) nil)

(hcChangeWords
 '(

   ;; Library code.

   ("(package llava "       . "(_namespace org.llava.lib.")
   ("(import llava."        . "(import org.llava.lib.")
   ("provide 'llava/"       . "provide 'org/llava/lib/")
   ("require 'llava/"       . "require 'org/llava/lib/")

   ("(package cl "          . "(_namespace org.llava.lib.cl.")
   ("import cl."            . "import org.llava.lib.cl.")
   ("provide 'cl/"          . "provide 'org/llava/lib/cl/")
   ("require 'cl/"          . "require 'org/llava/lib/cl/")

   ("(package image "       . "_namespace org.llava.lib.image.")
   ("import image."         . "import org.llava.lib.image.")

   ("(package java "        . "(_namespace org.llava.lib.java.")
   ("(package java.corba "  . "(_namespace org.llava.lib.java.corba.")
   ("(import java."         . "(import org.llava.lib.java.")
   ("provide 'java/"        . "provide 'org/llava/lib/java/")
   ("require 'java/"        . "require 'org/llava/lib/java/")

   ("(package scm "         . "(_namespace org.llava.lib.scm.")
   ("(import scm."          . "(import org.llava.lib.scm.")
   ("provide 'scm/"         . "provide 'org/llava/scm/")
   ("require 'scm/"         . "require 'org/llava/scm/")

   ("provide 'test/"        . "provide 'org/llava/lib/test/")
   ("require 'test/"        . "require 'org/llava/lib/test/")

   ("(package xml "         . "(_namespace org.llava.lib.xml.")
   ("(import xml."          . "(import org.llava.lib.xml.")
   ("provide 'scm/"         . "provide 'org/llava/scm/")
   ("require 'scm/"         . "require 'org/llava/scm/")

   ("(package "             . "(_namespace ")

   ;; Java code.

   ("package llava"         . "package org.llava")
   ("import llava."         . "import org.llava.")
   ("llavaProfile"          . "org.llava.impl")
   ("package testLlava"     . "package test")


;   ("lava" . "llava")
;   ("lllava" . "llava")
;   ("FR1" . "FR")
;   ("FC1" . "FC")
;   ("llavaProfile.compiler" . "llavaProfile.compiler")
;   ("libLlava.r1" . "llavaProfile.runtime")
;   ("libLlava.co" . "llava.compiler")
;   ("libLlava.rt" . "llava.runtime")
;   ("define ." . "define _")
;   ("define-syntax ." . "define-syntax _")
;   (".%" . "_%")
;   ("\(." . "\(_")
;   ("\'." . "\'_")
;   ("\"." . "\"_")
   )
 "~/.sync/.llava.org/.system"
 "~/.sync/.llava.org/.system"
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

