package helper

object Parser extends App {
  val ERR = -1

  def extractStatus(in: String) = {
    try {
      extract(in, ".*status\":(\\d+),\".*").toInt
    } catch {
      case e: Throwable => ERR
    }
  }

  def extractConfidence(in: String) = {
    try {
      extract(in, ".*confidence\":(\\d.\\d+).*").toDouble
    } catch {
      case e: Throwable => ERR
    }
  }

  def extractUtterance(in: String) = {
    extract(in, ".*utterance\":\"([a-zA-Z ]+)\",\"confidence.*")
  }

  def extract(in: String, pattern: String): String = {
    var ret = ""
    pattern.r.findAllIn(in).matchData foreach {
      m => ret = m.group(1)
    }
    ret
  }

  def test() = {
    val in = "{\"status\":0,\"id\":\"d7cfe0647045cb1462f892e468fa3134-1\",\"hypotheses\":[{\"utterance\":\"rar rar rar rar rar rar rar\",\"confidence\":0.5146254}]}"
    println(in)

    println(extractStatus(in))
    println(extractConfidence(in))
    println(extractUtterance(in))
  }
}