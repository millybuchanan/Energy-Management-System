// This is very similar to Example 3 - Receive with start- and end-markers
//    in Serial Input Basics   http://forum.arduino.cc/index.php?topic=396450.0

const byte numChars = 64;
char receivedChars[numChars];

boolean newData = false;

float num;

#define fan 11      // fan
#define ledPin 13   // the onboard LED
boolean led = true; // state of onboard LED
//===============

void receive() 
{
    static boolean recvInProgress = false;
    static byte ndx = 0;
    char startMarker = '<';
    char endMarker = '>';
    char rc;

    while (Serial.available() > 0 && newData == false) 
    {
        rc = Serial.read();

        if (recvInProgress == true) 
        {
            if (rc != endMarker) 
            {
                receivedChars[ndx] = rc;
                ndx++;
                if (ndx >= numChars) 
                {
                    ndx = numChars - 1;
                }
            } else 
            {
                receivedChars[ndx] = '\0'; // terminate the string
                recvInProgress = false;
                ndx = 0;
                newData = true;
            }
        }

        else if (rc == startMarker) 
        {
            recvInProgress = true;
        }
    }
}

//===============

void fanSpeed() 
{
    if (newData == true) 
    {
        num = atof(receivedChars);
        led = !led;
        digitalWrite(ledPin, led);
        newData = false;

        unsigned long highTime = (unsigned long) (10 * num);
        unsigned long lowTime = 1000 - highTime;
        unsigned long timer;
        Serial.print('<');
        Serial.print(highTime);
        Serial.print(',');
        Serial.print(lowTime);
        Serial.println('>');

        // hold speed for 5 seconds then turn off and wait for next input
        for (int i = 0; i < 5; i ++)
        {
          timer = millis();
          while ((millis() - timer) < highTime)
          {
            digitalWrite(fan, HIGH);
          }   
          timer = millis();
          while ((millis() - timer) < lowTime)
          {
            digitalWrite(fan, LOW);
          }
        }
    }
}

//===============
void setup() 
{
    // LED setup, write high
    pinMode(ledPin, OUTPUT);
    digitalWrite(ledPin, led);

    pinMode(fan, OUTPUT);
    digitalWrite(fan, LOW);

    // Serial setup
    Serial.begin(9600);
    Serial.println("<Arduino is ready>");
}

void loop() 
{
    receive();
    fanSpeed();
}