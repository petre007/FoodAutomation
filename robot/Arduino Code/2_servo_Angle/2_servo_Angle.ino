#include <Servo.h>

#define servo_PIN 9
Servo myservo;

void setup()
{
  Serial.begin(9600);
  myservo.attach(servo_PIN);
  myservo.write(90);
  delay(100);
}

void loop()
{
        myservo.write(45);
        delay(15);

        myservo.write(135);
        delay(15);
}
