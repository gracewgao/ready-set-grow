#include <DHT.h>
#include <aREST.h>
#include <Servo.h>

#include <SPI.h>


// DHT22 DAT pin 
#define DHTPIN 2
#define sensorPin A0
#define lightPin A1

#define redLED 13
#define greenLED 12

// Type of DHT sensor
#define DHTTYPE DHT11

Servo myservo;
DHT dht(DHTPIN, DHTTYPE);
aREST rest = aREST();

float temperature;
float humidity;
float light;
float water;

void setup() {
  Serial.begin(115200);

  myservo.attach(9);
  pinMode(redLED, OUTPUT);
  pinMode(greenLED, OUTPUT);

  char const *func = "openValve"; 
  rest.function(func, waterPlant);
  
  rest.variable("temperature",&temperature);
  rest.variable("humidity",&humidity);
  rest.variable("light",&light);
  rest.variable("water",&water);

  rest.set_id("001");
  
  char const *name = "ready_set_grow"; 
  rest.set_name(name);
  
  dht.begin();

  digitalWrite(redLED, LOW);
  digitalWrite(greenLED, LOW);

  waterPlant("0");

}

String measure() {
  
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  int w = analogRead(sensorPin);
  float l = analogRead(lightPin);

  humidity = dht.readHumidity();
  light = analogRead(
    lightPin);
  temperature = dht.readTemperature();
  water = analogRead(sensorPin);

  String message = "";

//  if (isnan(t) || isnan(h) || isnan(w)|| isnan(w)) {
//    return message;
//  }

//  // Create JSON as a message
//  message = message + "\"humidity\": " + h;
//  message = message + ", \"temperature\": " + t;
//  message = message + ", \"water_level\": " + w;
//  
  return message;
}

void changeLight(){

  bool red = water < 500;
  
  if (red){
    digitalWrite(redLED, HIGH);  
    digitalWrite(greenLED, LOW);  
    } else {
      digitalWrite(redLED, LOW);  
    digitalWrite(greenLED, HIGH);  
    }
  
}

int waterPlant(String open){
  int o = open.toInt();
  if (o){
    myservo.write(90);
  } else {
    myservo.write(140);
  }
  return 1;
}

void loop() {

  rest.handle(Serial);
  changeLight();

  String message = "";
  message += measure();
  
//  message = "{" + message + "}";
//
//  // Send message
//  Serial.println(message);
}
