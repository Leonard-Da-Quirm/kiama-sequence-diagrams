package kiama

import org.kiama.util.PositionedParserUtilities

/**
 * the parser for the defined syntax of sequence diagrams
 * uses Scala's DSL for parser combinators and some enhancements from Kiama
 */
trait Parser extends PositionedParserUtilities {

	lazy val start =
		phrase(_diagram)

	private lazy val _diagram: PackratParser[SeqDiagram] =
		"diagram" ~> name ~ _classes ~ _objects ~ _interactions ^^ SeqDiagram

	private lazy val _classes: PackratParser[List[SeqClass]] =
		"classes" ~> ":" ~>  (_class +)

	private lazy val _class: PackratParser[SeqClass] =
		"class" ~> name ~ (":" ~> (_method *)) ^^ SeqClass

	private lazy val _method: PackratParser[SeqMethod] =
		"method" ~> name <~ ";" ^^ SeqMethod

	private lazy val _objects: PackratParser[List[SeqObject]] =
		"objects" ~> ":" ~> (_object +)

	private lazy val _object: PackratParser[SeqObject] =
		name ~ name <~ ";" ^^ {
			case t ~ n => SeqObject(n, t)
		}

	private lazy val _interactions: PackratParser[List[SeqInteraction]] =
		"interactions" ~> ":" ~> (_interaction *)

	// name '->' name '.' name;
	private lazy val _interaction: PackratParser[SeqInteraction] =
		name ~ ("->" ~> name) ~ ("." ~> name) <~ ";" ^^ SeqInteraction

	// a valid name identifier
	private lazy val name =
		"""([a-zA-Z])(\w)*""".r
}
