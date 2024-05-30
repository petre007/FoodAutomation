                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        // PWM control pin
#define PWM1_PIN            5
#define PWM2_PIN            6      
// 74HCT595N Chip pins
#define SHCP_PIN            2                               // The displacement of the clock
#define EN_PIN              7                               // Can make control
#define DATA_PIN            8                               // Serial data
#define STCP_PIN            4                               // Memory register clock                  
#define GO_FORWARD 1
#define GO_BACKWARD 2
#define GO_LEFT 3
#define GO_RIGHT 4

String command = "no_command";

//const int Forward       = 92;                               // forward
//const int Backward      = 163;                              // back
//const int Turn_Left     = 149;                              // left translation
//const int Turn_Right    = 106;                              // Right translation 
//const int Top_Left      = 20;                               // Upper left mobile
//const int Bottom_Left   = 129;                              // Lower left mobile
//const int Top_Right     = 72;                               // Upper right mobile
//const int Bottom_Right  = 34;                               // The lower right move
//const int Stop          = 0;                                // stop
//const int Contrarotate  = 172;                              // Counterclockwise rotation
//const int Clockwise     = 83;                               // Rotate clockwise


void Move_Forward(int speed) //Define the forward function of the input speed
{
  digitalWrite(2,HIGH); //D2 digital I/O port controls the direction of the motor of interface A
  analogWrite(5,speed); //D5 digital I/O port outputs PWM signal to control the speed of the motor of port A.
  digitalWrite(4,LOW);//D4 digital I/O port controls the direction of the motor of interface B
  analogWrite(6,speed);//D6 digital I/O port outputs PWM signal to control the speed of interface B motor.
}

void Rotate_Left(int speed) //Define the left rotation function of the input speed
{
  digitalWrite(2,LOW);
  analogWrite(5,speed);
  digitalWrite(4,LOW);
  analogWrite(6,speed);
}

void Move_Backward(int speed) //Define the back function of the input speed
{
  digitalWrite(2,LOW);
  analogWrite(5,speed);
  digitalWrite(4,HIGH);
  analogWrite(6,speed);
}

void Stop() //Define stop function
{
  digitalWrite(2,LOW);
  analogWrite(5,0);
  digitalWrite(4,HIGH);
  analogWrite(6,0);
}

void Rotate_Right(int speed) //Define the right rotation function that can enter the speed
{
  digitalWrite(2,HIGH);
  analogWrite(5,speed);
  digitalWrite(4,HIGH);
  analogWrite(6,speed);
}

float checkdistance()
{
    digitalWrite(12, LOW);
    delayMicroseconds(2);
    digitalWrite(12, HIGH);
    delayMicroseconds(10);
    digitalWrite(12, LOW);
    float distance = pulseIn(13, HIGH) / 58.00;
    delay(10);
    return distance;
}

void Ultrasonic_Sensor_Module()
{
    int Distance = 0; 
    Distance = checkdistance();
    Serial.print("ULTRASONIC");
    Serial.println(Distance);
}

//void Motor(int Dir, int Speed)
//{
//    digitalWrite(EN_PIN, LOW);
//    analogWrite(PWM1_PIN, Speed);
//    analogWrite(PWM2_PIN, Speed);
//
//    digitalWrite(STCP_PIN, LOW);
//    shiftOut(DATA_PIN, SHCP_PIN, MSBFIRST, Dir);
//    digitalWrite(STCP_PIN, HIGH);
//}

void Move() 
{
  command = Serial.readString();

  switch(command.toInt()) {

    case GO_FORWARD:
    {
     Serial.println("GO_FORWARD command taken");
     Move_Forward(100);
     delay(50);
    }break;
    case GO_BACKWARD:
    {
     Serial.println("GO_BACKWARD command taken");
     Move_Backward(100);
     delay(50);    
    }break;
    case GO_LEFT:
    {
     Serial.println("GO_LEFT command taken");
     Rotate_Left(100);
     delay(50);
    }break;
    case GO_RIGHT:
    {
     Serial.println("GO_RIGHT command taken");
     Rotate_Right(100);
     delay(50);
    }break;
    default:{
        Stop();
        command = "no_command";
        Serial.println("No command taken");
    }break;
  }
}


void setup()
{
    Serial.begin(9600);
    pinMode(12, OUTPUT);
    
    pinMode(13, INPUT);
//    pinMode(SHCP_PIN, OUTPUT);
//    pinMode(EN_PIN, OUTPUT);
//    pinMode(DATA_PIN, OUTPUT);
//    pinMode(STCP_PIN, OUTPUT);
//    pinMode(PWM1_PIN, OUTPUT);
//    pinMode(PWM2_PIN, OUTPUT);

      pinMode(2, OUTPUT);
      pinMode(5, OUTPUT);
      pinMode(4, OUTPUT);
      pinMode(6, OUTPUT);
}
void loop()
{
    Move();
    Ultrasonic_Sensor_Module();
    
}
