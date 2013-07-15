package kiama

import io.Source
import java.io.{Reader, File}
import org.kiama.util.Emitter

object Main extends Parser {

	import kiama._
	import org.kiama.attribution.Attribution.initTree
	import PrettyPrinter.{pretty, pretty_any}
	import SemanticAnalysis._

	/**
	 * Read an process an input file
	 */
	def main(args: Array[String]): Unit = {
		// path must be adapted
		val path = "res/TestInput.txt"
		val input = Source.fromFile(path)
		processLine(input.reader())
		input.close()
	}

	/**
	 * Process a user input line by parsing it to get a value of type `T`,
	 * then passing it to the `process` method.
	 */
	def processLine(input: Reader) {
		parseAll(start, input) match {
			case Success(e, in) if in.atEnd =>
				process(e)
			case Success(_, in) =>
				println("extraneous input at " + in.pos)
			case f =>
				println(f)
		}
	}

	def process(e: SeqDiagram) {
		// this method is necessary
		initTree (e)

		// print the AST as an object hierarchy
//		println("e = " + e)
//		println("parsed AST:")
//		println(pretty_any(e))

		// print the AST in the dsl syntax
		println("AST pretty-printed:")
		println(pretty(e))
		println()

		// check for valid semantic and print errors
		println("Semanticly correct: " + isValid(e))
		println()

		showErrors(e)
	}

	/**
	 * search for semantic errors and report them
	 */
	def showErrors(e: SeqDiagram) {
		import org.kiama.util.Messaging._
		import org.kiama.util.Emitter

		resetmessages()
		check(e)
		if (messagecount > 0)
			report (new Emitter)
	}

}