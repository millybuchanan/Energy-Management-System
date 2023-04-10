#define motorPin 11
void setup() {
  // put your setup code here, to run once:
  pinMode(motorPin, OUTPUT);
}

void loop() {
  for(int i = 0; i < 255; i += 5)
  {
    analogWrite(motorPin, i);
    delay(500);
  }
  for (int i = 255; i > 0; i-= 5)
  {
    analogWrite(motorPin, i);
    delay(500);
  }

  delay(5000);

}
