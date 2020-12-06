#include <DHT.h>
#include <aREST.h>
#include <Servo.h>
#include <SPI.h>
#include <avr/wdt.h>

// defines pins
#define DHTPIN 2
#define sensorPin A0
#define lightPin A1
#define redLED 13
#define greenLED 12

#define DHTTYPE DHT11

Servo myservo;
DHT dht(DHTPIN, DHTTYPE);
aREST rest = aREST();

float temperature;
float humidity;
float light;
float water;

void setup() {
  
  // connects everything
  Serial.begin(115200);
  myservo.attach(9);
  dht.begin();

  // setup hardware positions
  pinMode(redLED, OUTPUT);
  pinMode(greenLED, OUTPUT);
  digitalWrite(redLED, LOW);
  digitalWrite(greenLED, LOW);
  waterPlant("0");

//  wdt_enable(WDTO_15MS);

  // aREST setup
  char const *name = "ready_set_grow"; 
  rest.set_name(name);

  char const *func = "openValve"; 
  rest.function(func, waterPlant);

  char const *f = "delay"; 
  rest.function(f, delayLight);
  
  rest.variable("temperature",&temperature);
  rest.variable("humidity",&humidity);
  rest.variable("light",&light);
  rest.variable("water",&water);

  rest.set_id("001");

}

String measure() {
  humidity = dht.readHumidity();
  temperature = dht.readTemperature();
  light = analogRead(lightPin);
  water = analogRead(sensorPin);
}

void changeLight(){

  bool red = water > 500;
  
  if (red){
    digitalWrite(redLED, HIGH);  
    digitalWrite(greenLED, LOW);  
  } else {
    digitalWrite(redLED, LOW);  
    digitalWrite(greenLED, HIGH);  
  }

}

void delayLight(){
  


    digitalWrite(redLED, HIGH);  
    digitalWrite(greenLED, LOW); 
        delay(10000); 
        digitalWrite(redLED, LOW);  
    digitalWrite(greenLED, HIGH);  

  }

int waterPlant(String time){
  int t = time.toInt();
  myservo.write(140);
  myservo.write(90);
  delay(t);
  myservo.write(140);
  
  return 1;
}

void loop() {
  rest.handle(Serial);
//  changeLight();
  measure();
//  wdt_reset();
}
