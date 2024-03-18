import serial
import sys


class SerialComunication:
    def __init__(self, port, baudrate):
        self.port = port
        self.baudrate = baudrate
        self.ser = None

    def _configure_serial_communication(self):
        if self.ser is None:
            self.ser = serial.Serial(port=self.port, baudrate=self.baudrate, timeout=1)
        return self.ser

    def read_data(self):
        ser = self._configure_serial_communication()
        line = ser.readline().decode('utf-8').rstrip()
        return line

    def send_data(self, data):
        ser = self._configure_serial_communication()
        ser.write(data)
        ser.flush()
