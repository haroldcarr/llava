/*
 * Created       : 2001 Mar 10 (Sat) 16:34:48 by Harold Carr.
 * Last Modified : 2002 Mar 07 (Thu) 12:59:05 by Harold Carr.
 */

package org.jdom.output;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.io.StringWriter;
import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
   
import org.jdom.Attribute;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Entity;
import org.jdom.Namespace;
import org.jdom.ProcessingInstruction;

import org.jdom.output.NamespaceStack;
import org.jdom.output.XMLOutputter;

public class LXMLOutputter extends XMLOutputter {

    protected String lineSeparator = "\n";

    public LXMLOutputter() {
    }

    protected void printDeclaration(Document doc,
                                    Writer out,
                                    String encoding) throws IOException {

        // Only print of declaration is not suppressed
        if (!suppressDeclaration) {
            // Assume 1.0 version
            if (encoding.equals("UTF8")) {
                out.write("(? xml (version \"1.0\")");
                if (!omitEncoding) {
                    out.write(" (encoding \"UTF-8\")");
                }
                out.write(")");
            } else {
                out.write("(? xml (version \"1.0\")");
                if (!omitEncoding) {
                    out.write(" (encoding \"" + encoding + ")\"");
                }
                out.write(")");
            }

            // Print new line after decl always, even if no other new lines
            // Helps the output look better and is semantically
            // inconsequential
	    String lineSeparator = "\n";
            out.write(lineSeparator);
        }        
    }    

    protected void printDocType(DocType docType, Writer out)
                        throws IOException {
        if (docType == null) {
            return;
        }

        String publicID = docType.getPublicID();
        String systemID = docType.getSystemID();
        boolean hasPublic = false;

        out.write("(! DOCTYPE ");
        out.write(docType.getElementName());
        if ((publicID != null) && (!publicID.equals(""))) {
            out.write(" PUBLIC \"");
            out.write(publicID);
            out.write("\"");
            hasPublic = true;
        }
        if ((systemID != null) && (!systemID.equals(""))) {
            if (!hasPublic) {
                out.write(" SYSTEM");
            }
            out.write(" \"");
            out.write(systemID);
            out.write("\"");
        }
        out.write(")");
	//        maybePrintln(out);
	String lineSeparator = "\n";
	out.write(lineSeparator);
    }

    protected void printComment(Comment comment,
                             Writer out, int indentLevel) throws IOException
    {
        indent(out, indentLevel);
        out.write("(!-- ");
	String sf = comment.getSerializedForm();
	out.write("\"" + sf.substring(4, sf.length() - 4) + "\"");
	out.write(")");
	//        maybePrintln(out);
	String lineSeparator = "\n";
	out.write(lineSeparator);
    }
      
    protected void printProcessingInstruction(ProcessingInstruction pi,
                        Writer out, int indentLevel) throws IOException {
                                        
        indent(out, indentLevel);
	String sf = pi.getSerializedForm();
	out.write("(? ");
	// REVISIT - still has =
        out.write(sf.substring(2, sf.length() - 3));
	out.write(")");
	//        maybePrintln(out);
	String lineSeparator = "\n";
	out.write(lineSeparator);
    }
    
    protected void printCDATASection(CDATA cdata,
                        Writer out, int indentLevel) throws IOException {
        
        indent(out, indentLevel);
	out.write("(");
	String sf = cdata.getSerializedForm();
        out.write(sf.substring(1, sf.length() - 2)); // REVISIT
	out.write(")");
        //maybePrintln(out);
	String lineSeparator = "\n";
	out.write(lineSeparator);

    }

    protected void printElement(Element element, Writer out,
                                int indentLevel, NamespaceStack namespaces)
                                throws IOException {

        List mixedContent = element.getMixedContent();

        boolean empty = mixedContent.size() == 0;
        boolean stringOnly =
            !empty &&
            mixedContent.size() == 1 &&
            mixedContent.get(0) instanceof String;

        // Print beginning element tag
        /* maybe the doctype, xml declaration, and processing instructions 
           should only break before and not after; then this check is 
           unnecessary, or maybe the println should only come after and 
           never before.  Then the output always ends with a newline */
           
        indent(out, indentLevel);

        // Print the beginning of the tag plus attributes and any
        // necessary namespace declarations


	boolean hasAttributes = element.getAttributes().size() > 0;
	boolean hasNamespaceDeclarations = false;

        Namespace ns = element.getNamespace();
        if (ns != Namespace.XML_NAMESPACE &&
            !(ns == Namespace.NO_NAMESPACE && namespaces.getURI("") == null)) {
            String prefix = ns.getPrefix();        
            String uri = namespaces.getURI(prefix);
            if (!ns.getURI().equals(uri)) { // output a new namespace decl
		hasNamespaceDeclarations = true;
            }
        }

        List additionalNamespaces = element.getAdditionalNamespaces();
        if (additionalNamespaces != null) {
            for (int i=0; i<additionalNamespaces.size(); i++) {
                Namespace additional = (Namespace)additionalNamespaces.get(i);
                String prefix = additional.getPrefix();        
                String uri = namespaces.getURI(prefix);
                if (!additional.getURI().equals(uri)) {
		    hasNamespaceDeclarations = true;
                }
            }
        }

        // handle "" string same as empty
        if (stringOnly) {
            String elementText =
                trimText ? element.getTextTrim() : element.getText();
            if (elementText == null ||
                elementText.equals("")) {
                empty = true;
            }
        }

	// Begin element.

	out.write("(");

        out.write(element.getQualifiedName());
        int previouslyDeclaredNamespaces = namespaces.size();

	if (hasAttributes || hasNamespaceDeclarations) {
	    out.write(" (@");
	}

        ns = element.getNamespace();

        // Add namespace decl only if it's not the XML namespace and it's 
        // not the NO_NAMESPACE with the prefix "" not yet mapped
        // (we do output xmlns="" if the "" prefix was already used and we
        // need to reclaim it for the NO_NAMESPACE)
        if (ns != Namespace.XML_NAMESPACE &&
            !(ns == Namespace.NO_NAMESPACE && namespaces.getURI("") == null)) {
            String prefix = ns.getPrefix();        
            String uri = namespaces.getURI(prefix);
            if (!ns.getURI().equals(uri)) { // output a new namespace decl
                namespaces.push(ns);
                printNamespace(ns, out);
            }
        }

        // Print out additional namespace declarations
        additionalNamespaces = element.getAdditionalNamespaces();
        if (additionalNamespaces != null) {
            for (int i=0; i<additionalNamespaces.size(); i++) {
                Namespace additional = (Namespace)additionalNamespaces.get(i);
                String prefix = additional.getPrefix();        
                String uri = namespaces.getURI(prefix);
                if (!additional.getURI().equals(uri)) {
                    namespaces.push(additional);
                    printNamespace(additional, out);
                }
            }
        }

        printAttributes(element.getAttributes(), element, out, namespaces);
	if (hasAttributes || hasNamespaceDeclarations) {
	    out.write(")");
	}

        if (empty) {
	    out.write(")");
            maybePrintln(out);
        } else {
            // we know it's not null or empty from above

	    out.write(" ");

            if (stringOnly) {
                // if string only, print content on same line as tags
                printElementContent(element, out, indentLevel,
                                    namespaces, mixedContent);
            }
            else {
                maybePrintln(out);
                printElementContent(element, out, indentLevel,
                                    namespaces, mixedContent);
                indent(out, indentLevel);               
            }

            out.write(")");

            maybePrintln(out);
        }

        // remove declared namespaces from stack
        while (namespaces.size() > previouslyDeclaredNamespaces) {
            namespaces.pop();
        }
    }
    
    protected void printElementContent(Element element, Writer out,
                                       int indentLevel,
                                       NamespaceStack namespaces,
                                       List mixedContent) throws IOException {
        // get same local flags as printElement does
        // a little redundant code-wise, but not performance-wise
        boolean empty = mixedContent.size() == 0;
        boolean stringOnly =
            !empty &&
            (mixedContent.size() == 1) &&
            mixedContent.get(0) instanceof String;

        if (stringOnly) {
            // Print the tag  with String on same line
            // Example: <tag name="value">content</tag>
            String elementText =
                trimText ? element.getTextTrim() : element.getText();
            
            out.write("\"" + escapeElementEntities(elementText) + "\"");
            
        } else {
            /**
             * Print with children on future lines
             * Rather than check for mixed content or not, just print
             * Example: <tag name="value">
             *             <child/>
             *          </tag>
             */
            // Iterate through children
            Object content = null;
           Class justOutput = null;
            for (int i=0, size=mixedContent.size(); i<size; i++) {
                content = mixedContent.get(i);
                // See if text, an element, a PI or a comment
                if (content instanceof Comment) {
                    printComment((Comment) content, out, indentLevel + 1);
                   justOutput = Comment.class;
                } else if (content instanceof String) {
		   if (padText && (justOutput == Element.class))
                       out.write(padTextString);
                   printString((String)content, out);
                   justOutput = String.class;
                } else if (content instanceof Element) {
		   if (padText && (justOutput == String.class))
                       out.write(padTextString);
                    printElement((Element) content, out,
                                 indentLevel + 1, namespaces);
                   justOutput = Element.class;
                } else if (content instanceof Entity) {
                    printEntity((Entity) content, out);
                   justOutput = Entity.class;
                } else if (content instanceof ProcessingInstruction) {
                    printProcessingInstruction((ProcessingInstruction) content,
                                               out, indentLevel + 1);
                   justOutput = ProcessingInstruction.class;
                } else if (content instanceof CDATA) {
                    printCDATASection((CDATA)content, out, indentLevel + 1);
                   justOutput = CDATA.class;
                }
                // Unsupported types are *not* printed
            }
        }
    }  // printElementContent


    protected void printEntity(Entity entity, Writer out) throws IOException {
	String sf = entity.getSerializedForm();
        out.write("(&; ");
	out.write(sf.substring(1, sf.length() - 1));
	out.write(")");
    }
    
    protected void printNamespace(Namespace ns, Writer out) throws IOException {
	out.write(" (");
        out.write("xmlns");
        String prefix = ns.getPrefix();
        if (!prefix.equals("")) {
            out.write(":");
            out.write(prefix);
        }
        out.write(" \"");
        out.write(ns.getURI());
        out.write("\"");
	out.write(")");
    }

    protected void printAttributes(List attributes, Element parent, 
                                   Writer out, NamespaceStack namespaces) 
      throws IOException {

        // I do not yet handle the case where the same prefix maps to
        // two different URIs. For attributes on the same element
        // this is illegal; but as yet we don't throw an exception
        // if someone tries to do this
        Set prefixes = new HashSet();

        for (int i=0, size=attributes.size(); i < size; i++) {
            Attribute attribute = (Attribute)attributes.get(i);
            Namespace ns = attribute.getNamespace();
            if (ns != Namespace.NO_NAMESPACE && ns != Namespace.XML_NAMESPACE) {
                String prefix = ns.getPrefix();           
                String uri = namespaces.getURI(prefix);
                if (!ns.getURI().equals(uri)) { // output a new namespace decl
                    printNamespace(ns, out);
                    namespaces.push(ns);
                }
            }
            
            out.write(" (");
            out.write(attribute.getQualifiedName());
            out.write(" ");

            out.write("\"");
            out.write(escapeAttributeEntities(attribute.getValue()));
            out.write("\")");
        }
        
    }
}
