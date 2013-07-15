package kiama

/**
* AST pretty-printing.
*/
object PrettyPrinter extends org.kiama.output.PrettyPrinter {

	/**
	 * Return a pretty-printed version of a node.
	 */
	def pretty(t: SeqBase): String =
		super.pretty(show(t))

	/**
	 * Convert an expression node to a pretty-printing document.
	 */
	def show(t: SeqBase): Doc = t match {
		case SeqDiagram(n, cs, os, is) =>
			"diagram" <+> n <@>
				showList("classes:", cs) <@>
				showList("objects:", os) <@>
				showList("interactions:", is)
		case SeqClass(n, ms) => showList("class" <+> n <> colon, ms)
		case SeqMethod(n) => "method" <+> n <> semi
		case SeqObject(n, c) => c <+> n <> semi
		case SeqInteraction(s,t,m) => s <+> "->" <+> t <> dot <> m
	}

	def showList(t: Doc, xs: List[SeqBase]) = t <> nest(line <> vsep(xs map show))
}
