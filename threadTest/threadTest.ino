#include "protothreads.h"
#include "DHT20.h"

// variable setup
#define fan 11
DHT20 DHT;

pt ptDHT;
pt ptFan;

// Celcius to Farenhiet conversion
float CtoF(float c)
{
  float f = ((c*9)/5) + 32;
  return f;
}

// Conversion of float data to string character array
void floatToString(float temperature, float humidity)
{
  char myString[20];
  char strTemperature[6];
  char strHumidity[6];
  dtostrf(temperature, 5, 2, strTemperature);
  dtostrf(humidity, 5, 2, strHumidity);
  sprintf(myString, "%s, %s", strTemperature, strHumidity);
  Serial.print(myString);
}

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
          floatToString(temperature, humidity);
          break;
        case DHT20_ERROR_CHECKSUM:
          Serial.println("ERROR: checksum error");
          break;
        case DHT20_ERROR_CONNECT:
          Serial.println("ERROR: check connection");
          break;
        case DHT20_MISSING_BYTES:
          Serial.println("ERROR: missing bytes; check connection");
          break;
        case DHT20_ERROR_BYTES_ALL_ZERO:
          Serial.println("ERROR: all bytes zero; check connection");
          break;
        case DHT20_ERROR_READ_TIMEOUT:
          Serial.println("ERROR: timeout");
          break;    
        case DHT20_ERROR_LASTREAD:
          Serial.println("ERROR: reading too fast");
          break;
        default:
          Serial.println("ERROR: unknown");
          break;
      }
    } 
    Serial.println();
    // read every 30 seconds
    PT_SLEEP(pt, 5000);
  }

  PT_END(pt);
}

// Thread adjusting fan speed
int fanThread(struct pt* pt)
{
  PT_BEGIN(pt);

  while(1)
  {
    unsigned long highTime = (unsigned long) (50 * 10);
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

//start DHT reading, set motor pin mode, and begin serial connection
void setup() 
{
  DHT.begin();

  pinMode(fan, OUTPUT);

  Serial.begin(9600);
  Serial.println("Starting...");
  delay(3000);
  
  PT_INIT(&ptDHT);
  PT_INIT(&ptFan);
}

void loop() 
{
  PT_SCHEDULE(DHTThread(&ptDHT));
  PT_SCHEDULE(fanThread(&ptFan));
}
