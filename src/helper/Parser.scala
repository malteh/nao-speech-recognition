package helper

import java.util.regex.Pattern

/** Der Parser für die Antworten von Google auf die Audio-Aufnahmen */
object Parser {
  val ERR = -1

  /**
   * Parst den Status als Int aus einem String
   * @param in Der String aus dem der Wert extrahiert werden soll
   * @return Der extrahierte Wert als Int, -1 im Fehlerfall
   */
  def extractStatus(in: String) = {
    try {
      extract(in, ".*status\":(\\d+),\".*").toInt
    } catch {
      case e: Throwable => ERR
    }
  }

  /**
   * Parst die Zufriedenheit der Spracherkennunga als Int aus einem String
   * @param in Der String aus dem der Wert extrahiert werden soll
   * @return Der extrahierte Wert als Int, -1 im Fehlerfall
   */
  def extractConfidence(in: String) = {
    try {
      extract(in, ".*confidence\":(\\d.\\d+).*").toDouble
    } catch {
      case e: Throwable => ERR
    }
  }

  /**
   * Parst das Ergebnis der Spracherkennung aus einem String
   * @param in Der String aus dem der Text extrahiert werden soll
   * @return Der erkannte Text als String, ein leerer String im Fehlerfall
   */
  def extractUtterance(in: String) = {
    extract(in, ".*utterance\":\"(.*)\",\"confidence.*")
  }

  /**
   * Extrahiert mit Regex einen String aus einem anderen String
   * @param in Der String aus dem der Text extrahiert werden soll
   * @return Der erkannte Text als String, ein leerer String im Fehlerfall
   */
  def extract(in: String, regex: String): String = {

    var ret = "";
    val p = Pattern.compile(regex);
    val m = p.matcher(in);

    if (m.find()) {
      ret = m.group(1);
    }
    ret
  }
  
  /**
   * Beispielaufruf
   */
   def main(args: Array[String]) = {
    val in = "{\"status\":0,\"id\":\"d7cfe0647045cb1462f892e468fa3134-1\",\"hypotheses\":[{\"utterance\":\"rar rar rar rar rar rar rar\",\"confidence\":0.5146254}]}"
    println(in)

    println(extractStatus(in))
    println(extractConfidence(in))
    println(extractUtterance(in))
  }
}