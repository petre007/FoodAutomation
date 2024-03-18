#include    <Servo.h>

// 舵机控制引脚 Steering gear control pin
#define MOTOR_PIN           9
// PWM 控制引脚  Pin of control
#define PWM1_PIN            5
#define PWM2_PIN            6
// 74HCT595N 芯片引脚
#define SHCP_PIN            2                               // 位移时钟  Shift shift clock
#define EN_PIN              7                               // 使能控制  Enable control
#define DATA_PIN            8                               // 串行数据  Serial data
#define STCP_PIN            4                               // 存储寄存器时钟  Memory register clock   
// 超声波控制引脚 Ultrasonic control pin
#define Trig_PIN            12
#define Echo_PIN            13
// 循迹控制引脚  Trace control pin
#define LEFT_LINE_TRACJING      A0
#define CENTER_LINE_TRACJING    A1
#define right_LINE_TRACJING     A2


Servo MOTORservo;

const int Forward       = 92;                               // 前进 forward
const int Backward      = 163;                              // 后退 back
const int Turn_Left     = 149;                              // 左平移 Translation to the left
const int Turn_Right    = 106;                              // 右平移 Translation to the right
const int Top_Left      = 20;                               // 左上移动 Top left
const int Bottom_Left   = 129;                              // 左下移动 Down left
const int Top_Right     = 72;                               // 右上移动 Up to right
const int Bottom_Right  = 34;                               // 右下移动 Down right
const int Stop          = 0;                                // 停止 stop
const int Contrarotate  = 172;                              // 逆时针旋转Counterclockwise rotation 
const int Clockwise     = 83;                               // 顺时针旋转Rotate clockwise 
const int Moedl1        = 25;                               // 模式1 Mode 1
const int Moedl2        = 26;                               // 模式2 Mode 2
const int Moedl3        = 27;                               // 模式3 Mode 3
const int Moedl4        = 28;                               // 模式4 Mode 4
const int MotorLeft     = 230;                              // 舵机左转 Steering gear left turn
const int MotorRight    = 231;                              // 舵机右转 Steering gear turn right

int Left_Tra_Value;
int Center_Tra_Value;
int Right_Tra_Value;
int Black_Line = 500;

byte RX_package[3] = {0};
uint16_t angle = 90;
byte order = Stop;
char model_var = 0;
int UT_distance = 0;

void setup()
{
    Serial.begin(115200);

    MOTORservo.attach(MOTOR_PIN);

    pinMode(SHCP_PIN, OUTPUT);
    pinMode(EN_PIN, OUTPUT);
    pinMode(DATA_PIN, OUTPUT);
    pinMode(STCP_PIN, OUTPUT);
    pinMode(PWM1_PIN, OUTPUT);
    pinMode(PWM2_PIN, OUTPUT);

    pinMode(Trig_PIN, OUTPUT);
    pinMode(Echo_PIN, INPUT);

    pinMode(LEFT_LINE_TRACJING, INPUT);
    pinMode(CENTER_LINE_TRACJING, INPUT);
    pinMode(right_LINE_TRACJING, INPUT);

    MOTORservo.write(angle);

    Motor(Stop, 0);
    
}

void loop()
{
    RXpack_func();
    switch (model_var)
    {
    case 0:
        model1_func(order);
        break;
    case 1:
        model2_func();      // 避障模式 Obstacle avoidance mode
        break;
    case 2:
        model3_func();      // 跟随模式 Follow
        break;
    case 3:
        model4_func();      // 巡线模式 tracking
        break;
    }
    
}

void model1_func(byte orders)
{
    switch (orders)
    {
    case Stop:
        Motor(Stop, 0);
        MOTORservo.write(90);
        break;
    case Forward:
        Motor(Forward, 250);
        break;
    case Backward:
        Motor(Backward, 250);
        break;
    case Turn_Left:
        Motor(Turn_Left, 250);
        break;
    case Turn_Right:
        Motor(Turn_Right, 250);
        break;
    case Top_Left:
        Motor(Top_Left, 250);
        break;
    case Top_Right:
        Motor(Top_Right, 250);
        break;
    case Bottom_Left:
        Motor(Bottom_Left, 250);
        break;
    case Bottom_Right:
        Motor(Bottom_Right, 250);
        break;
    case Clockwise:
        Motor(Clockwise, 250);
        break;
    case MotorLeft:
        motorleft();
        break;
    case MotorRight:
        motorright();
        break;
    default:
        // Serial.println(".");
        order = 0;
        break;
    }
}

void model2_func()      // Obstacle avoidance mode
{
    UT_distance = SR04(Trig_PIN, Echo_PIN);
    // Serial.println(UT_distance);
    if (UT_distance <= 25)
    {
        if(UT_distance <= 15)
        {
            Motor(Stop, 0);
            RXpack_func();
            delay(100);
            RXpack_func();
            Motor(Backward, 180);
            RXpack_func();
            delay(600);
            RXpack_func();
            Motor(Clockwise, 180);
            RXpack_func();
            delay(300);
            RXpack_func();
        }
        else
        {
            Motor(Stop, 0);
            RXpack_func();
            delay(100);
            RXpack_func();
            Motor(Backward, 180);
            RXpack_func();
            delay(300);
            RXpack_func();
            Motor(Contrarotate, 180);
            RXpack_func();
            delay(600);
            RXpack_func();
        }
    }
    else
    {
        Motor(Forward, 180);
    }
}

void model3_func()      // Follow mode
{
    UT_distance = SR04(Trig_PIN, Echo_PIN);
    Serial.println(UT_distance);
    if (UT_distance < 15)
    {
        Motor(Backward, 200);
    }
    else if (15 <= UT_distance && UT_distance <= 20)
    {
        Motor(Stop, 0);
    }
    else if (20 <= UT_distance && UT_distance <= 25)
    {
        Motor(Forward, 180);
    }
    else if (25 <= UT_distance && UT_distance <= 50)
    {
        Motor(Forward, 220);
    }
    else
    {
        Motor(Stop, 0);
        MOTORservo.write(90); //The servo has stopped
    }
}

void model4_func()      // tracking model
{
    Left_Tra_Value = analogRead(LEFT_LINE_TRACJING);
    Center_Tra_Value = analogRead(CENTER_LINE_TRACJING);
    Right_Tra_Value = analogRead(right_LINE_TRACJING);
    if (Left_Tra_Value < Black_Line && Center_Tra_Value >= Black_Line && Right_Tra_Value < Black_Line)
    {
        Motor(Forward, 175);
    }
    else if (Left_Tra_Value >= Black_Line && Center_Tra_Value >= Black_Line && Right_Tra_Value < Black_Line)
    {
        Motor(Contrarotate, 165);
    }
    else if (Left_Tra_Value >= Black_Line && Center_Tra_Value < Black_Line && Right_Tra_Value < Black_Line)
    {
        Motor(Contrarotate, 190);
    }
    else if (Left_Tra_Value < Black_Line && Center_Tra_Value < Black_Line && Right_Tra_Value >= Black_Line)
    {
        Motor(Clockwise, 190);
    }
    else if (Left_Tra_Value < Black_Line && Center_Tra_Value >= Black_Line && Right_Tra_Value >= Black_Line)
    {
        Motor(Clockwise, 165);
    }
    else if (Left_Tra_Value >= Black_Line && Center_Tra_Value >= Black_Line && Right_Tra_Value >= Black_Line)
    {
        Motor(Stop, 0);
    }
}
void motorleft()
{
    MOTORservo.write(110);  //Adjust the speed 90~180 counterclockwise
    delay(80);  //Execution time per turn
}
void motorright()
{
    MOTORservo.write(70);   //Adjust the speed 0~90 clockwise
    delay(80);  //Execution time per turn
}

void Motor(int Dir, int Speed)      // Motor drive
{
    digitalWrite(EN_PIN, LOW);
    analogWrite(PWM1_PIN, Speed);
    analogWrite(PWM2_PIN, Speed);

    digitalWrite(STCP_PIN, LOW);
    shiftOut(DATA_PIN, SHCP_PIN, MSBFIRST, Dir);
    digitalWrite(STCP_PIN, HIGH);
}

float SR04(int Trig, int Echo)      // Ultrasonic ranging
{
    digitalWrite(Trig, LOW);
    delayMicroseconds(2);
    digitalWrite(Trig, HIGH);
    delayMicroseconds(10);
    digitalWrite(Trig, LOW);
    float distance = pulseIn(Echo, HIGH) / 58.00;
    delay(10);
    
    return distance;
}

void RXpack_func()
{
    if(Serial.available() > 0)
    {
        delay(1);                                           // 延时 1MS
        if(Serial.readBytes(RX_package, 3))
        {
            if (RX_package[0] == 0xA5 && RX_package[2] == 0x5A)     // 只验证了包头与包尾，暂未验证检验码
            {
                order = RX_package[1];
                if(order == Moedl1) 
                {
                    model_var = 0;
                }
                else if (order == Moedl2)
                {
                    model_var = 1;
                }
                else if (order == Moedl3)
                {
                    model_var = 2;
                }
                else if (order == Moedl4)
                {
                    model_var = 3;
                }
            }
            else{
                if(order == 230 || order ==231)
                 order = 0;
            }
        }
    }
}
