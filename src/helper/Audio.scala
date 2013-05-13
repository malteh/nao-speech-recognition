package helper

import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import java.io.File

/** Helper für Aufgaben rund um Audio-Dateien */
object Audio {

  /**
   * Liest die Sample Rate aus eimer WAV aus
   * @param wavFile Die WAV-Datei
   * @return Die Sample Rate
   */
  def sampleRate(wavFile: File): Int = {
    val audioInputStream = AudioSystem.getAudioInputStream(wavFile)
    val audioFormat = audioInputStream.getFormat
    audioFormat.getSampleRate.toInt
  }
  
  /**
   * Beispielaufruf
   */
  def main(args: Array[String]) = {
    println(sampleRate(new File("recordings/01.wav")))
  }
}