gst-launch-0.10 tcpserversrc host=192.168.1.108 port=3000 ! oggdemux ! vorbisdec ! audioconvert ! "audio/x-raw-int", channels=1, width=16, depth=16, signed=TRUE, rate=16000, endianess=1234 ! pulsesink

