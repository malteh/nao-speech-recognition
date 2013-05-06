package helper

import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import java.io.File

object Audio extends App {
  
  test
  
  def sampleRate(wavFile: File): Int = {
    val audioInputStream = AudioSystem.getAudioInputStream(wavFile)
    val audioFormat = audioInputStream.getFormat
    audioFormat.getSampleRate.toInt
  }
  
  def test() = {
    println(sampleRate(new File("recordings/01.wav")))
  }
}