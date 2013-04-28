/*File AudioRecorder02.java
 * http://www.developer.com/java/other/article.php/2105421/Java-Sound-Capturing-Microphone-Data-into-an-Audio-File.htm
 Copyright 2003, Richard G. Baldwin

 This program demonstrates the capture of audio
 data from a microphone into an audio file.

 A GUI appears on the screen containing the
 following buttons:
 Capture
 Stop

 In addition, five radio buttons appear on the
 screen allowing the user to select one of the
 following five audio output file formats:

 AIFC
 AIFF
 AU
 SND
 WAVE

 When the user clicks the Capture button, input
 data from a microphone is captured and saved in
 an audio file named junk.xx having the specified
 file format.  (xx is the file extension for the
 specified file format.  You can easily change the
 file name to something other than junk if you
 choose to do so.)

 Data capture stops and the output file is closed
 when the user clicks the Stop button.

 It should be possible to play the audio file
 using any of a variety of readily available
 media players, such as the Windows Media Player.

 Not all file types can be created on all systems.
 For example, types AIFC and SND produce a "type
 not supported" error on my system.

 Be sure to release the old file from the media
 player before attempting to create a new file
 with the same extension.

 Tested using SDK 1.4.1 under Win2000
 ************************************************/
package recorder

import javax.swing.JFrame
import javax.sound.sampled._
import javax.swing._
import java.awt.event.ActionListener
import java.awt.FlowLayout
import java.io.File
import javaFlacEncoder.FLAC_FileEncoder
import java.awt.event.ActionEvent

class AudioRecorder02 extends JFrame("Copyright 2003, R.G.Baldwin") {

  val audioFormat: AudioFormat = getAudioFormat
  val dataLineInfo = new DataLine.Info(classOf[TargetDataLine], audioFormat);
  val targetDataLine: TargetDataLine = AudioSystem.getLine(dataLineInfo).asInstanceOf[TargetDataLine]

  val captureBtn = new JButton("Capture")
  val stopBtn = new JButton("Stop")

  val btnPanel = new JPanel()

  captureBtn.setEnabled(true);
  stopBtn.setEnabled(false);

  // Register anonymous listeners    
  captureBtn.addActionListener(new ActionListener() {
    def actionPerformed(e: ActionEvent) = {
      captureBtn.setEnabled(false);
      stopBtn.setEnabled(true);
      // Capture input data from the
      // microphone until the Stop button is
      // clicked.
      captureAudio();
    } // end actionPerformed
  } // end ActionListener
  ); // end addActionListener()

  stopBtn.addActionListener(new ActionListener() {
    def actionPerformed(e: ActionEvent) = {
      captureBtn.setEnabled(true);
      stopBtn.setEnabled(false);
      // Terminate the capturing of input data
      // from the microphone.
      targetDataLine.stop();
      targetDataLine.close();
    } // end actionPerformed
  } // end ActionListener
  ); // end addActionListener()

  // Put the buttons in the JFrame
  getContentPane().add(captureBtn);
  getContentPane().add(stopBtn);

  // Put the JPanel in the JFrame
  getContentPane().add(btnPanel);

  // Finish the GUI and make visible
  getContentPane().setLayout(new FlowLayout());
  pack
  setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
  setSize(300, 120);
  setVisible(true);

  // This method captures audio input from a
  // microphone and saves it in an audio file.
  def captureAudio() = {
    try {
      // Get things set up for capture
      // audioFormat = getAudioFormat
      //val dataLineInfo = new DataLine.Info(classOf[TargetDataLine], audioFormat);
      // targetDataLine = AudioSystem.getLine(dataLineInfo).asInstanceOf[TargetDataLine];

      // Create a thread to capture the microphone
      // data into an audio file and start the
      // thread running. It will run until the
      // Stop button is clicked. This method
      // will return after starting the thread.
      (new CaptureThread).start
    } catch {
      case e: Exception => {
        e.printStackTrace();
        System.exit(0);
      }

    } // end catch
  } // end captureAudio method

  // This method creates and returns an
  // AudioFormat object for a given set of format
  // parameters. If these parameters don't work
  // well for you, try some of the other
  // allowable parameter values, which are shown
  // in comments following the declarations.
  def getAudioFormat: AudioFormat = {
    val sampleRate = 16000.0F;
    // 8000,11025,16000,22050,44100
    val sampleSizeInBits = 16;
    // 8,16
    val channels = 1;
    // 1,2
    val signed = true;
    // true,false
    val bigEndian = false;
    // true,false
    new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
  } // end getAudioFormat

  // Inner class to capture data from microphone
  // and write it to an output audio file.
  class CaptureThread extends Thread {

    override def run() = {
      var fileType: AudioFileFormat.Type = null;
      var audioFile: File = null;

      // Set the file type and the file extension
      // based on the selected radio button.

      fileType = AudioFileFormat.Type.WAVE;
      audioFile = new File("junk.wav");
      try {
        targetDataLine.open(audioFormat);
        targetDataLine.start();
        AudioSystem.write(new AudioInputStream(targetDataLine),
          fileType, audioFile);
        val flacEncoder = new FLAC_FileEncoder();
        val inputFile = new File("junk.wav");
        val outputFile = new File("hello.flac");

        flacEncoder.encode(inputFile, outputFile);
      } catch {
        case e: Exception => e.printStackTrace();
      } // end catch

    } // end run
  } // end inner class CaptureThread

}

object AudioRecorder02 extends App {
  val win = new AudioRecorder02
}