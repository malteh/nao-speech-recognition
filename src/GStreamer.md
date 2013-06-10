#Basic Architecture

Using ProtoBuf or on startup, the NAO will start streaming audio to a specific server using GStreamer.
The server will decode the stream and transfer the audio data to any clients requesting audio data.

##Communication

Between NAO and server: Ogg Vorbis encoded 16kHz Mono 16-Bit
Between server and client: 16kHz Mono 16-Bit Big-Endian PCM

##Buffers

NaoQi buffers 170ms of Audio data. In bytes, this is 170ms * 16 kHz * 16 Bit / 8 Bytes / 1 Channel = 5440 Bytes.
After decompression, the server will store a configurable number of preloaded 5440 Bytes buffers.
If 30 buffers are requested, about 10.2s or 160kB of raw audio data will be returned.
This logic has been copied to ensure low latency.
