package helper

import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import java.io.File

object Audio extends App {
  
  test
  
  def sampleRate(wavFile: String): Int = {
    val audioInputStream = AudioSystem.getAudioInputStream(new File(wavFile))
    val audioFormat = audioInputStream.getFormat
    audioFormat.getSampleRate.toInt
  }
  
  def test() = {
    println(sampleRate("recordings/01.wav"))
  }
}