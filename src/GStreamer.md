#Basic Architecture

Using ProtoBuf or on startup, the NAO will start streaming audio to a specific server using GStreamer.
The server will decode the stream and transfer the audio data to any clients requesting audio data.

##Communication to NAO

Between NAO and server: Ogg Vorbis encoded 16kHz Mono 16-Bit
Between server and client: 16kHz Mono 16-Bit Big-Endian PCM

##Buffers

NaoQi buffers 170ms of Audio data. In bytes, this is 170ms * 16 kHz * 16 Bit / 8 Bytes / 1 Channel = 5440 Bytes.
After decompression, the server will store a configurable number of preloaded 5440 Bytes buffers.
If 30 buffers are requested, about 10.2s or 160kB of raw audio data will be returned.
This logic has been copied to ensure low latency.

##Server

The server will connect to NAO by starting a listening GStreamer server in a separate process.
It will wait for the NAO to connect and return the binary PCM data to syso.
Java will read blocks from syso and store the data in a buffer queue, periodically dropping old data.

##Communication to client
Open up a TCP connection to the server and send a single byte, which is the number of buffers requested.
Immediately following, the server will return the buffered raw data, followed by an EOF.

#Settings

Currently, the server has a hard-coded IP in the NAO: 192.168.1.108 and the port 3000.
Also, it listens on port 3100 for audio data requests.
