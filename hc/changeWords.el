(load "hcChangeWords")
(defun is-ignored-p (full-path-and-name)
  (member (file-name-nondirectory full-path-and-name)
	  '("SCCS" "RCS")))
(defun is-ignored-suffix-p (full-path-and-name) nil)
;(setq case-fold-search nil)
;(setq default-case-fold-search nil)

(hcChangeWords
 '(
    ("org.llava.Repl"                  . "org.llava.spi.io.Repl")
    ("org.llava.compiler.Compiler"     . "org.llava.spi.compiler.Compiler")

    ("org.llava.io.LlavaEOFImpl"       . "org.llava.impl.io.LlavaEOFImpl")
    ("org.llava.io.LlavaEOF"           . "org.llava.spi.io.LlavaEOF")
    ("org.llava.io.LlavaReader"        . "org.llava.spi.io.LlavaReader")

    ("org.llava.lang.exceptions.BacktraceHandler.java" .
                        "org.llava.spi.exceptions.BacktraceHandler.java")
    ("org.llava.lang.exceptions.LlavaException.java" .
                        "org.llava.spi.exceptions.LlavaException.java")

    ("org.llava.lang.types.PairImpl"   . "org.llava.impl.types.PairImpl")
    ("org.llava.lang.types.Pair"       . "org.llava.spi.types.Pair")
    ("org.llava.lang.types.Procedure"  . "org.llava.spi.types.Procedure")
    ("org.llava.lang.types.SymbolImpl" . "org.llava.impl.types.SymbolImpl")
    ("org.llava.lang.types.Symbol"     . "org.llava.spi.types.Symbol")

    ("org.llava.runtime"               . "org.llava.spi.runtime")
    ("org.llava.impl.runtime.Engine"   . "org.llava.spi.runtime.Engine")

    ("org.llava.impl.ReplImpl"         . "org.llava.impl.io.ReplImpl")
    ("org.llava.impl.Llava"            . "org.llava.Llava")

  )
  "~/rip/rpcMsgFramework/doc/pept2/tests"
  "~/rip/rpcMsgFramework/doc/pept2/tests"
 t)

; "~/.sync/.llava.org/.system"

;;; End of file.
