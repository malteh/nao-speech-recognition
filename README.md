nao-speech-recognition
======================

/src/recognition/RecognitionExample.java

Methode recognize:
	sendet flac-File an Google-Server. Dieser führt die Erkennung durch und sendet den erkannten Text (inkl. Statuscodes und eigener Bewertung der Erkennung) zurück.
	Rückgabe (Info-Klasse, toString): text=rar rar rar rar rar rar rar;status=0;confidence=0.5146254

/src/recorder/AudioRecorder02.java
	Quelle: http://www.developer.com/java/other/article.php/2105421/Java-Sound-Capturing-Microphone-Data-into-an-Audio-File.htm
	nimmt vom Mikrofon auf und speichert als wav, konvertiert anschließend nach flac