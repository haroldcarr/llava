src.org.llava:
	Llava
	lib
	(implied spi - really at org.llava level):
		llava.lva

		F
		FC
		FR

		LlavaVersion
		Repl

		Lambda
		Pair
		Procedure
		Symbol
		LlavaException
		UndefinedIdException
		WrongNumberOfArgumentsException

		compiler
			Compiler
			EnvironmentLexical
		io
			LlavaEOF
			LlavaReader
			LlavaWriter
		procedure
			GenericProcedure
			WrapJavaPrimitive
		runtime
			ActivationFrame
			EnvironmentTopLevel
			Namespace
			UndefinedIdHandler
			Engine

			BacktraceHandler
			EnvTopLevelInit
			Evaluator
			LlavaRuntime
		;;syntax
	impl
		LlavaVersionImpl
		ReplImpl

		LambdaImpl
		PairImpl
		SymbolImpl

		compiler
			CompilerImpl
			EnvironmentLexicalImpl
		io
			LlavaEOFImpl
			LlavaReaderImpl
			LlavaWriterImpl
		procedure
			DI
			GenericProcedureImpl
			PrimitiveProcedure
			WrapJavaPrimitiveImpl
			Prim* (flatten)
		runtime
			ActivationFrameImpl
			EnvironmentTopLevelImpl
			NamespaceImpl
			UndefinedIdHandlerImpl
			Code*
			EngineImpl

			BacktraceHandlerImpl
			EnvTopLevelInitImpl
			Evaluator???
			LlavaRuntimeImpl
		syntax
			SyntaxDefineSyntax
			Syntax
			UserSyntax
		util
			List



(load "hcChangeWords")
(defun is-ignored-p (full-path-and-name)
  (member (file-name-nondirectory full-path-and-name)
	  '("SCCS" "RCS")))
(defun is-ignored-suffix-p (full-path-and-name) nil)
;(setq case-fold-search nil)
;(setq default-case-fold-search nil)

(hcChangeWords
 '(
   ("org.llava.impl.compiler.EnvironmentLexical" .
    "org.llava.compiler.EnvironmentLexical")
   ("org.llava.impl.compiler.FC"  .
    "org.llava.FC")

   ("org.llava.impl.F" .
    "org.llava.F")
   ("org.llava.impl.Llava" .
    "org.llava.Llava")

   ("org.llava.impl.runtime.code" .
    "org.llava.impl.runtime")
   ("org.llava.impl.runtime.Engine" .
    "org.llava.runtime.Engine")
   ("org.llava.impl.runtime.env.ActivationFrame" .
    "org.llava.runtime.ActivationFrame")
   ("org.llava.impl.runtime.env.ActivationFrameImpl" .
    "org.llava.impl.runtime.ActivationFrameImpl")
   ("org.llava.impl.runtime.env.EnvironmentTopLevelImpl" .
    "org.llava.impl.runtime.EnvTopLevelInitImpl")
   ("org.llava.impl.runtime.env.Namespace" .
    "org.llava.runtime.Namespace")
   ("org.llava.impl.runtime.env.NamespaceImpl" .
    "org.llava.impl.runtime.NamespaceImpl")
   ("org.llava.impl.runtime.env.UndefinedIdHandlerImpl" .
    "org.llava.impl.runtime.UndefinedIdHandlerImpl")

   ("org.llava.impl.runtime.exceptions.BacktraceHandlerImpl" .
    "org.llava.impl.runtime.BacktraceHandlerImpl")
   ("org.llava.impl.runtime.exceptions.UndefinedIdException" .
    "org.llava.UndefinedIdException")
   ("org.llava.impl.runtime.exceptions.WrongNumberOfArgumentsException" .
    "org.llava.WrongNumberOfArgumentsException")
   ("org.llava.impl.runtime.FR" .
    "org.llava.FR")

   ("org.llava.impl.runtime.procedure" . 
    "org.llava.impl.procedure")
   ("org.llava.impl.runtime.procedure.generic.GenericProcedure" .
    "org.llava.procedure.generic.GenericProcedure")
   ("org.llava.impl.runtime.procedure.generic.WrapJavaPrimitive" .
    "org.llava.procedure.generic.WrapJavaPrimitive")
   ("org.llava.impl.runtime.procedure.generic" .
    "org.llava.impl.procedure.generic")
   ("org.llava.impl.runtime.procedure.Lambda" .
    "org.llava.Lambda")
   ("org.llava.impl.runtime.procedure.LambdaImpl" .
    "org.llava.impl.LambdaImpl")

   
   ("org.llava.impl.runtime.procedure.primitive.java.opt" .
    "org.llava.impl.procedure")
   ("org.llava.impl.runtime.procedure.primitive.java" .
    "org.llava.impl.procedure")
   ("org.llava.impl.runtime.procedure.primitive.llava.opt" .
    "org.llava.impl.procedure")
   ("org.llava.impl.runtime.procedure.primitive.llava" .
    "org.llava.impl.procedure")
   ("org.llava.impl.runtime.procedure.primitive.PrimitiveProcedure" .
    "org.llava.impl.procedure.PrimitiveProcedure")

   ("org.llava.impl.runtime.syntax" .
    "org.llava.impl.syntax")

   ("org.llava.io.LlavaEOFImpl" .
    "org.llava.impl.io.LlavaEOFImpl")

   ("org.llava.lang.exceptions.BacktraceHandler" .
    "org.llava.runtime.BacktraceHandler")
   ("org.llava.lang.exceptions.LlavaException" .
    "org.llava.LlavaException")

   ("org.llava.lang.types.PairImpl" . 
    "org.llava.impl.PairImpl")
   ("org.llava.lang.types.Pair" . 
    "org.llava.Pair")
   ("org.llava.lang.types.Procedure" . 
    "org.llava.Procedure")
   ("org.llava.lang.types.SymbolImpl" . 
    "org.llava.impl.SymbolImpl")
   ("org.llava.lang.types.Symbol" . 
    "org.llava.Symbol")

  )
  "~/rip/rpcMsgFramework/doc/pept2/tests"
  "~/rip/rpcMsgFramework/doc/pept2/tests"
 t)

; "~/.sync/.llava.org/.system"

;;; End of file.
