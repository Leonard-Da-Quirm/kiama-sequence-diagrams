package kiama

/**
 * this class contains method to check for semantic correctness
 * and error reporting
 * important: you must call initTree(AST)
 */
object SemanticAnalysis {

	import org.kiama.attribution.Attribution._
	import org.kiama.util.Messaging._

	/**
	 * inherit the root diagram to every node
	 */
	val diagram: SeqBase => SeqDiagram =
		attr {
			case d: SeqDiagram => d
			case b: SeqBase => diagram(b.parent[SeqBase])
		}

	/**
	 * a reference of the object's class
	 */
	val clazz: SeqObject => Option[SeqClass] =
		attr {
			case o@SeqObject(_, c) => diagram(o).classes.find(_.name == c)
		}

	/**
	 * a reference of an interaction's source object
	 */
	val srcObj: SeqInteraction => Option[SeqObject] =
		attr {
			case i@SeqInteraction(s, _, _) => diagram(i).objects.find(_.name == s)
		}

	/**
	 * a reference of an interaction's target object
	 */
	val targetObj: SeqInteraction => Option[SeqObject] =
		attr {
			case i@SeqInteraction(_, t, _) => diagram(i).objects.find(_.name == t)
		}

	/**
	 * a reference of an interaction's method
	 */
	val methodObj: SeqInteraction => Option[SeqMethod] =
		attr {
			case i@SeqInteraction(_, _, m) =>
				targetObj(i) flatMap {
					clazz(_) flatMap {
						_.methods.find(_.name == m)
					}
				}
		}

	/**
	 * test if the given diagram is valid
	 */
	def isValid(d: SeqDiagram): Boolean = {
		d.classes.forall(isValid) &&
			d.objects.forall(isValid) &&
			d.interactions.forall(isValid)
	}

	/**
	 * an object is valid if its class is defined and
	 * there is no other object with the same name
	 */
	def isValid(o: SeqObject) =
		clazz(o).isDefined &&
			diagram(o).objects.forall(x => (x eq o) || x.name != o.name)

	/**
	 * a class is valid if it there is no other class with the same name
	 */
	def isValid(c: SeqClass) = diagram(c).classes.forall(x => (x eq c) || x.name != c.name)

	/**
	 * an interaction is valid if its source, target and method are defined
	 */
	def isValid(i: SeqInteraction) =
		srcObj(i).isDefined && targetObj(i).isDefined && methodObj(i).isDefined

	/**
	 * search for semantic errors and submit them to the message module
	 */
	def check(d: SeqDiagram) {
		for ((n, cs) <- d.classes.groupBy(_.name); if (cs.size > 1)) {
			message(cs.head, "There are two or more classes with the same name: " + n)
		}
		for (o <- d.objects; if (!clazz(o).isDefined)) {
			message(o, "There is no class with name " + o.seqClass)
		}
		for ((n, os) <- d.objects.groupBy(_.name); if (os.size > 1)) {
			message(os.head, "There are two or more objects with the same name: " + os.head.name)
		}
		for (i <- d.interactions) {
			if (!srcObj(i).isDefined)
				message(i, "The source object " + i.source + " does not exist1")
			if (!targetObj(i).isDefined)
				message(i, "The target object " + i.target + " does not exist!")
			else if (!methodObj(i).isDefined)
				message(i, "The method " + i.method + " does not exist in class " + targetObj(i).get.seqClass)
		}
	}
}
