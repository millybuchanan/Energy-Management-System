#include "DHT20.h"

DHT20 DHT;
int status;
float humidity;
float temperature;

void setup()
{
  DHT.begin();

  Serial.begin(9600);
  Serial.println("DHT20 Test");

  delay(1000);
}
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

void loop()
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
  delay(1000);
  Serial.println();
}