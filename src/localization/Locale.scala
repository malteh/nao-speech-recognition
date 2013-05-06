package localization

trait Locale {
  def stand: String
  def sit: String
  def notRecognized(text: String): String
}

object De extends Locale {
  val stand = "aufstehen"
  val sit = "hinsetzen"
  val say = "sage"
  def notRecognized(text: String) = "ich habe " + text + " nicht verstanden"
}