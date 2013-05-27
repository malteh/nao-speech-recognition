gst-launch-0.10 -vet souphttpsrc location=http://192.168.178.22:8080/video timeout=5 ! jpegdec ! autovideosink

