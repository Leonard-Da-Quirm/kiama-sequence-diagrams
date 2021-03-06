package kiama

import org.kiama.attribution.Attributable
import org.kiama.util.Positioned

/**
 * the AST for a sequence diagramm
 */
sealed abstract class SeqBase extends Attributable with Positioned

case class SeqDiagram(name: String, classes: List[SeqClass], objects: List[SeqObject], interactions: List[SeqInteraction]) extends SeqBase

case class SeqClass(name: String, methods: List[SeqMethod]) extends SeqBase

case class SeqMethod(name: String) extends SeqBase

case class SeqObject(name: String, seqClass: String) extends SeqBase

case class SeqInteraction(source: String, target: String, method: String) extends SeqBase