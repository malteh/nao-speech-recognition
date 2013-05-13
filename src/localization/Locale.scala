package localization

trait Locale {
  val stand: String
  val sit: String
  val say: String
  val randomEyes: String
  val rotateEyes: String
  val loose: String
  val stiff: String
  val kill: String
  val wave: String
  val hello:String
  def notRecognized(text: String): String
}

object De extends Locale {
  val stand = "aufstehen"
  val sit = "hinsetzen"
  val say = "sage"
  val randomEyes = "Augen"
  val rotateEyes = "Augen rotieren"
  val loose = "locker"
  val stiff = "unbeweglich"
  val kill = "terminieren"
  val wave = "winken"
    val hello = "hallo"
  def notRecognized(text: String) = "ich habe " + text + " nicht verstanden"
}