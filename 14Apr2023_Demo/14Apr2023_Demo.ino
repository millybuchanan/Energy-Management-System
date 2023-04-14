#include "protothreads.h"
#include "DHT20.h"

// HW declarations
#define fan 11
DHT20 DHT;

// Serial declarations
float serialIn = 0;
const byte numChars = 64;
char receivedChars[numChars];
boolean newData = false;

// thread declarations
pt ptDHT;
pt ptFan;
pt ptSerial;

// -----------------------------------------------------------------
// HELPER FUNCTIONS
// -----------------------------------------------------------------

// Celcius to Farenhiet conversion
float CtoF(float c)
{
  float f = ((c*9)/5) + 32;
  return f;
}

// Conversion of float data to string character array
// Put in the format '<Temperature, Humidity>' to be read by script
// Prints string character array to Serial
void tempHumidFormat(float temperature, float humidity)
{
  char myString[20];
  char strTemperature[6];
  char strHumidity[6];
  dtostrf(temperature, 5, 2, strTemperature);
  dtostrf(humidity, 5, 2, strHumidity);
  sprintf(myString, "<%s, %s>", strTemperature, strHumidity);
  Serial.print(myString);
}

// -----------------------------------------------------------------
// THREADS
// -----------------------------------------------------------------

// Thread collecting temperature and humidity from DHT20
int DHTThread(struct pt* pt)
{
  int status;
  float humidity;
  float temperature;
  PT_BEGIN(pt);

  while(1)
  {
    if(millis() - DHT.lastRead() >= 1000)
    {
      status = DHT.read();
      
      switch(status)
      {
        case DHT20_OK:
          temperature = CtoF(DHT.getTemperature());
          humidity = DHT.getHumidity();
          tempHumidFormat(temperature, humidity);
          break;
        case DHT20_ERROR_CHECKSUM:
          Serial.println("<ERROR: checksum error>");
          break;
        case DHT20_ERROR_CONNECT:
          Serial.println("<ERROR: check connection>");
          break;
        case DHT20_MISSING_BYTES:
          Serial.println("<ERROR: missing bytes; check connection>");
          break;
        case DHT20_ERROR_BYTES_ALL_ZERO:
          Serial.println("<ERROR: all bytes zero; check connection>");
          break;
        case DHT20_ERROR_READ_TIMEOUT:
          Serial.println("<ERROR: timeout>");
          break;    
        case DHT20_ERROR_LASTREAD:
          Serial.println("<ERROR: reading too fast>");
          break;
        default:
          Serial.println("<ERROR: unknown>");
          break;
      }
    } 
    Serial.println();
    PT_SLEEP(pt, 10000);
  }

  PT_END(pt);
}

// Thread adjusting fan speed
int fanThread(struct pt* pt)
{
  PT_BEGIN(pt);

  while(1)
  {
    if(newData == true)
    {
      serialIn = atof(receivedChars);
      newData = false;
    }
    
    unsigned long highTime = (unsigned long) (serialIn * 10);
    unsigned long lowTime = (unsigned long) (1000 - highTime);
    unsigned long timer = millis();
    while ((millis() - timer) < highTime)
    {
      digitalWrite(fan, HIGH);
    }   
    timer = millis();
    while ((millis() - timer) < lowTime)
    {
      digitalWrite(fan, LOW);
    }

    PT_SLEEP(pt, 10);
  }
  
  PT_END(pt);
}

// Thread reading value 0.0-100.0 from serial to set fan speed
int serialThread(struct pt* pt)
{
  static boolean recvInProgress = false;
  static byte ndx = 0;
  char startMarker = '<';
  char endMarker = '>';
  char rc;
  
  PT_BEGIN(pt);
  long unsigned timer = millis();

  while(1)
  {
    // if no value read after 2 seconds, yeild
    //if(millis() - timer < 1000)
    //{
      while(Serial.available() > 0 && newData == false) 
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
    /*}else
    {
      Serial.println("<Time Yielded>");
      PT_YIELD(pt);
    }*/

    PT_SLEEP(pt, 5000);    
  }
  PT_END(pt);
}

//start DHT reading, set motor pin mode, and begin serial connection
void setup() 
{
  DHT.begin();

  pinMode(fan, OUTPUT);

  Serial.begin(9600);
  Serial.setTimeout(20000);
  Serial.println("<Arduino is ready>");
  
  PT_INIT(&ptDHT);
  PT_INIT(&ptSerial);
  PT_INIT(&ptFan);
}

void loop() 
{
  PT_SCHEDULE(DHTThread(&ptDHT));
  PT_SCHEDULE(serialThread(&ptSerial));
  PT_SCHEDULE(fanThread(&ptFan));
}
