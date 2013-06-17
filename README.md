nao-speech-recognition
######################

Übersicht:
Dieses Repository beinhaltet eine lauffähige Installation für die NAO Spracherkennung. Die Sprache wird dabei zur Zeit über ein externes (z.B. am Notebook) aufgenommen und zur Analyse an die Google Speach API gesendet 

Aktuelle Umgebung auf Entwicklungssystem
* Ubuntu 12 64-bit
* OpenJava 7 oder Java 7
* Scala 2.10.1


Installation
Mittels <code>git clone</code> das Repo Klonen.
Es handelt sich um ein vollständiges Scala Eclipse Projekt welches mittels Rechtsklick, General, Existing Project into Workspace direkt importiert werden kann.

ToDo:
Der Nao hat einen eingebauten g-streamer0.10 Client. Um diesen  zu nutzen, benötigt man einen g-streamer Server den man auf einem beliebigen Rechner innerhalb des NAO Netzwerkes starten muss. Anschließend kann man Naoqi neu starten. Der Naoqi Prozess sendet nun einen dauerhaften Audio stream an den g-streamer. Mittels des g-streamer Java Framework, welches im Projekt bereits integriert ist, lässt sich dieser Server auch aus Java / Scala heraus starten. Anschließend kann über eine TCP Socket Verbindung der Stream Abgegriffen werden. Dies funktioniert leider jedoch noch nicht.


Ordnerstruktur:
## value
### Hawactormsg.proto
Beinhaltet das Protobuf Nachrichtenformat für die Kommunikation mit den Actoren.
### HawCam.proto
Beinhaltet das Protobuf Nachrichtenformat für die Kommunikation mit den Kamaras des Roboters
# lib
Beinhaltet die externen Pakete die von diesem Projekt benutzt werden.
* AKKA Aktoren Framework, benötigt für Kommunikation
* naogateway, benötigt für Kommunikation
* protobuf, benötigt für Kommunikation
* javaFlacEncoder, konvertiert die WAV Files in FLAC zur Reduktion der Datenmenge, wird außerdem von der Spracherkennungs-API als Eingabe benötigt

# recordings
Beinhaltet die Aufnahmedatei wie sie von dem Mikrophon zurück geliefrt wurde. Die Aufnahme wird als WAV gespeichert und anschließend von der FlacEncoder Bibliothek nach FLAC umgewandelt.

# src 
Beinhaltet die eigentlichen Scala Sourcen
## helper
### Audio.scala
<code>sampleRate(wavFile: File)</code>
   * Liest die Sample Rate aus eimer WAV aus
   * @param wavFile Die WAV-Datei
   * @return Die Sample Rate

### Parser.scala
Verarbeitet die JSON Antwort die von der Google API zurückgeliefert wird.
## localiztion
### Locale.scala
Auflistung aller Befehle die mittels Sprachbefehlen ausgeführt werden können.

Die Klasse "De" ist die Abbildung der Deutschen Befehle auf die allgemeinen.
## naogateway.value
Nachrichtenformate für Protobuf
## recognition
### Converter.scala
Mapping Funktionalität von allgemeinen Begriffen auf konkrete Methode die dann im unteren Block implementiert ist.
### Google.scala
beinhaltet die Adresse und Header Informationen zum API Aufruf an die Google Spracherkennung.


<code>recognize(wavFile:String)</code>
* Konvertiert die eingegene WAV Datei nach FLAC. Diese Konvertierung wird benötigt, da die Google API Flac als Eingabformat unterstützt.
* Die Sprache die erkannt werden soll muss vor dem versenden bereits bekannt sein. 
* Sample Rate auslesen, diese wird ebenfalls für den API Aufruf benötigt.
* Die Übertragung erfolgt als HTTP POST Aufruf an die API
* Die Rückgabe wird in einem Byte[] gespeichert.
* Das Byte[] wird anschließend nach UTF-8 Konvertiert.
* Anschließend wird der String in seine Bestandteile zerlegt.
* Und kann anschließend in der Klase Info auf die Methoden gemappt werden.

### Info.scala
Fasst das Erkennungsergebnis zusammen
## recorder
### AudioRecorder02.scala
Test Anwendung zum erzeugen von WAV Files aus Java heraus. Es wird das Default Mikrofone auf der Host-Maschine benutzt.
### TestFrame.scala
GUI Die das Aufnehmen der WAV Files vereinfacht.
### HttpRecorder.scala
Dieses Beispielprogramm ließt einen WAV Stream von einer definierten IP Adresse. Als Quelle dient zu Testzwecken ein Android Smartphone auf dem die App "IPWebcam" aus dem Google Play Store. Diese App kann einen Audio/Video Stream liefern der dann in dieser Anwendung verarbeitet wird. 
### GStreamer.scala
Beinhaltet die GStreamer Schnitstelle wie sie aus dem Nao geliefert wird. Dies ist die Low Level Schnitstelle. ToDo: Es sollte der Actor verwendet werden der dann den gewünschten Stream weiterleitet. Der Nao dient dabei als Client der sich auf einem GStreamer Server verbindet. Der Verbindungsaufbau findet bereits beim start von naoqi statt. Vorraussetzungen für den Server sind neben gstreamer-0.10 und dieser Datei folgende Pakete: 
<code>sudo apt-get install libgstreamer-plugins-base0.10-dev libgstreamer0.10-dev libglib2.0-dev</code>
Diese werden für die GStreamer-Java API benötigt.

## application.conf
Beinhaltet die Konfigurationseinstellungen zur Kommunikation mit dem Aktoren System.
Aktueller Inhalt: Example.conf

# README.md
Diese Datei.


Mit RemoteTest.scala im default package ausprobieren

Aufnahmen können mit recorder/AudioRecorder02.scala gemacht werden
