src.org.llava:
	F
	FC
	FR
	Lambda
	Llava
	LlavaException
	LlavaVersion
	Pair
	Procedure
	Repl
	Symbol
	UndefinedIdException
	WrongNumberOfArgumentsException
	llava.lva

	compiler
		Compiler
		EnvironmentLexical
	derived
	io
		LlavaEOF
		LlavaReader
		LlavaWriter
	lib
	procedure
		GenericProcedure
		WrapJavaPrimitive
	runtime
		ActivationFrame
		BacktraceHandler
		Engine
		EnvTopLevelInit
		EnvironmentTopLevel
		Evaluator
		LlavaRuntime
		Namespace
		UndefinedIdHandler
	;;syntax

	impl
		LambdaImpl
		LlavaVersionImpl
		PairImpl
		ReplImpl
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
			BacktraceHandlerImpl
			Code*
			EngineImpl
			EnvTopLevelInitImpl
			EnvironmentTopLevelImpl
			LlavaRuntimeImpl
			NamespaceImpl
			UndefinedIdHandlerImpl
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

   ("org.llava.impl.runtime.env.ActivationFrameImpl" .
    "org.llava.impl.runtime.ActivationFrameImpl")
   ("org.llava.impl.runtime.env.EnvironmentTopLevelImpl" .
    "org.llava.impl.runtime.EnvTopLevelInitImpl")
   ("org.llava.impl.runtime.env.NamespaceImpl" .
    "org.llava.impl.runtime.NamespaceImpl")
   ("org.llava.impl.runtime.env.UndefinedIdHandlerImpl" .
    "org.llava.impl.runtime.UndefinedIdHandlerImpl")
   ("org.llava.impl.runtime.Engine" .
    "org.llava.runtime.Engine")
   ("org.llava.impl.runtime.env.ActivationFrame" .
    "org.llava.runtime.ActivationFrame")
   ("org.llava.impl.runtime.env.Namespace" .
    "org.llava.runtime.Namespace")
   ("org.llava.impl.runtime.code" .
    "org.llava.impl.runtime")

   ("org.llava.impl.runtime.exceptions.BacktraceHandlerImpl" .
    "org.llava.impl.runtime.BacktraceHandlerImpl")
   ("org.llava.impl.runtime.exceptions.UndefinedIdException" .
    "org.llava.UndefinedIdException")
   ("org.llava.impl.runtime.exceptions.WrongNumberOfArgumentsException" .
    "org.llava.WrongNumberOfArgumentsException")

   ("org.llava.impl.runtime.procedure.generic.GenericProcedureImpl" .
    "org.llava.impl.procedure.generic.GenericProcedureImpl")
   ("org.llava.impl.runtime.procedure.generic.WrapJavaPrimitiveImpl" .
    "org.llava.impl.procedure.generic.WrapJavaPrimitiveImpl")
   ("org.llava.impl.runtime.procedure.generic.GenericProcedure" .
    "org.llava.procedure.generic.GenericProcedure")
   ("org.llava.impl.runtime.procedure.generic.WrapJavaPrimitive" .
    "org.llava.procedure.generic.WrapJavaPrimitive")
   ("org.llava.impl.runtime.procedure.generic" .
    "org.llava.impl.procedure.generic")
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
   ("org.llava.impl.runtime.procedure.LambdaImpl" .
    "org.llava.impl.LambdaImpl")
   ("org.llava.impl.runtime.procedure.Lambda" .
    "org.llava.Lambda")
   ("org.llava.impl.runtime.procedure" . 
    "org.llava.impl.procedure")


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
   ("org.llava.lang.types.SymbolImpl" . 
    "org.llava.impl.SymbolImpl")
   ("org.llava.lang.types.Pair" . 
    "org.llava.Pair")
   ("org.llava.lang.types.Procedure" . 
    "org.llava.Procedure")
   ("org.llava.lang.types.Symbol" . 
    "org.llava.Symbol")

   ("org.llava.impl.compiler.FC"  .
    "org.llava.FC")
   ("org.llava.impl.runtime.FR" .
    "org.llava.FR")
   ("org.llava.impl.F" .
    "org.llava.F")
   ("org.llava.impl.Llava" .
    "org.llava.Llava")


  )
  "~/.sync/.llava.org/.system"
  "~/.sync/.llava.org/.system"
 t)

;;; End of file.
