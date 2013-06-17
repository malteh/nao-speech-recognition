nao-speech-recognition
######################

�bersicht:
Dieses Repository beinhaltet eine lauff�hige Installation f�r die NAO Spracherkennung. Die Sprache wird dabei zur Zeit �ber ein externes (z.B. am Notebook) aufgenommen und zur Analyse an die Google Speach API gesendet 

Aktuelle Umgebung auf Entwicklungssystem
* Ubuntu 12 64-bit
* OpenJava 7 oder Java 7
* Scala 2.10.1


Installation
Mittels <code>git clone</code> das Repo Klonen.
Es handelt sich um ein vollst�ndiges Scala Eclipse Projekt welches mittels Rechtsklick, General, Existing Project into Workspace direkt importiert werden kann.

ToDo:
Der Nao hat einen eingebauten g-streamer0.10 Client. Um diesen  zu nutzen, ben�tigt man einen g-streamer Server den man auf einem beliebigen Rechner innerhalb des NAO Netzwerkes starten muss. Anschlie�end kann man Naoqi neu starten. Der Naoqi Prozess sendet nun einen dauerhaften Audio stream an den g-streamer. Mittels des g-streamer Java Framework, welches im Projekt bereits integriert ist, l�sst sich dieser Server auch aus Java / Scala heraus starten. Anschlie�end kann �ber eine TCP Socket Verbindung der Stream Abgegriffen werden. Dies funktioniert leider jedoch noch nicht.

g-streamer Server wurde nur unter Ubuntu getestet. In Ordner "doc" befindet sich eine kleine yed Grafik die den Prozess veranschaulichen soll.

Ordnerstruktur:
## sh
In diesem Ordner befinden sich mehrere Shell Scripte die die Aufrufe von gstreamer aus unterschiedlichen Sichten demonstrieren. Bedenkt das ihr euch im NAO Netzwerk befinden m�sst. 
### Client auf Laptop fuer IPWebcam.sh
Ein Beispielskript von Antonio wie ihr den g-streamer Datenstrom aus dem Smartphone anzapfen k�nnt die die M�glichkeiten von g-streamer verdeutlichen.

### Client auf Laptop fuer Smartphone.sh
Ein Beispielaufruf wie ihr den g-streamer Datenstrom aus dem Smartphone anzapfen k�nnt.

### Client auf Nao.sh
Ein Beispielaufruf wie aus dem Nao heraus der Stream an einen g-streamer Server versendet werden kann. Naoqi darf fuer diesen Aufruf nicht laufen.

Die Anpassungen im Naoqi damit dies bei Start funktioniert, m�ssen bei Bj�rn gesucht werden - diese sind hier nicht dokumentiert.

### Server auf Laptop fuer Nao.sh
Ein Beispielaufruf wie der Server fuer Nao eingestellt werden kann.

## value
### Hawactormsg.proto
Beinhaltet das Protobuf Nachrichtenformat f�r die Kommunikation mit den Actoren.
### HawCam.proto
Beinhaltet das Protobuf Nachrichtenformat f�r die Kommunikation mit den Kamaras des Roboters
# lib
Beinhaltet die externen Pakete die von diesem Projekt benutzt werden.
* AKKA Aktoren Framework, ben�tigt f�r Kommunikation
* naogateway, ben�tigt f�r Kommunikation
* protobuf, ben�tigt f�r Kommunikation
* javaFlacEncoder, konvertiert die WAV Files in FLAC zur Reduktion der Datenmenge, wird au�erdem von der Spracherkennungs-API als Eingabe ben�tigt

# recordings
Beinhaltet die Aufnahmedatei wie sie von dem Mikrophon zur�ck geliefrt wurde. Die Aufnahme wird als WAV gespeichert und anschlie�end von der FlacEncoder Bibliothek nach FLAC umgewandelt.

# src 
Beinhaltet die eigentlichen Scala Sourcen
## Client.java
Beispielanwendung um den WAV Stream aus dem TCP Socket abzufangen. Funktioniert leider nicht.
## Server.java
Beispielanwendung um den WAV Stream bereitzustellen.
Funktioniert in der kombination mit dem Client leider nicht.

## gstreamer
### GStreamer.scala
Beinhaltet die GStreamer Schnitstelle wie sie aus dem Nao geliefert wird. Dies ist die Low Level Schnitstelle. ToDo: Es sollte der Actor verwendet werden der dann den gew�nschten Stream weiterleitet. Der Nao dient dabei als Client der sich auf einem GStreamer Server verbindet. Der Verbindungsaufbau findet bereits beim start von naoqi statt. Vorraussetzungen f�r den Server sind neben gstreamer-0.10 und dieser Datei folgende Pakete: 
<code>sudo apt-get install libgstreamer-plugins-base0.10-dev libgstreamer0.10-dev libglib2.0-dev</code>
Diese werden f�r die GStreamer-Java API ben�tigt.

## helper
### Audio.scala
<code>sampleRate(wavFile: File)</code>
   * Liest die Sample Rate aus eimer WAV aus
   * @param wavFile Die WAV-Datei
   * @return Die Sample Rate

### Parser.scala
Verarbeitet die JSON Antwort die von der Google API zur�ckgeliefert wird.
## localiztion
### Locale.scala
Auflistung aller Befehle die mittels Sprachbefehlen ausgef�hrt werden k�nnen.

Die Klasse "De" ist die Abbildung der Deutschen Befehle auf die allgemeinen.
## naogateway.value
Nachrichtenformate f�r Protobuf
## recognition
### Converter.scala
Mapping Funktionalit�t von allgemeinen Begriffen auf konkrete Methode die dann im unteren Block implementiert ist.
### Google.scala
beinhaltet die Adresse und Header Informationen zum API Aufruf an die Google Spracherkennung.


<code>recognize(wavFile:String)</code>
* Konvertiert die eingegene WAV Datei nach FLAC. Diese Konvertierung wird ben�tigt, da die Google API Flac als Eingabformat unterst�tzt.
* Die Sprache die erkannt werden soll muss vor dem versenden bereits bekannt sein. 
* Sample Rate auslesen, diese wird ebenfalls f�r den API Aufruf ben�tigt.
* Die �bertragung erfolgt als HTTP POST Aufruf an die API
* Die R�ckgabe wird in einem Byte[] gespeichert.
* Das Byte[] wird anschlie�end nach UTF-8 Konvertiert.
* Anschlie�end wird der String in seine Bestandteile zerlegt.
* Und kann anschlie�end in der Klase Info auf die Methoden gemappt werden.

### Info.scala
Fasst das Erkennungsergebnis zusammen
## recorder
### AudioRecorder02.scala
Test Anwendung zum erzeugen von WAV Files aus Java heraus. Es wird das Default Mikrofone auf der Host-Maschine benutzt.
### TestFrame.scala
GUI Die das Aufnehmen der WAV Files vereinfacht.
### HttpRecorder.scala
Dieses Beispielprogramm lie�t einen WAV Stream von einer definierten IP Adresse. Als Quelle dient zu Testzwecken ein Android Smartphone auf dem die App "IPWebcam" aus dem Google Play Store installiert ist. Diese App kann einen Audio/Video Stream liefern der dann in dieser Anwendung verarbeitet wird. 


## application.conf
Beinhaltet die Konfigurationseinstellungen zur Kommunikation mit dem Aktoren System.
Aktueller Inhalt: Example.conf

# README.md
Diese Datei.


Mit RemoteTest.scala im default package ausprobieren

Aufnahmen k�nnen mit recorder/AudioRecorder02.scala gemacht werden
