#include <DHT.h>
#include <aREST.h>
#include <Servo.h>
#include <Ethernet.h>
#include <SPI.h>

// DHT22 DAT pin 
#define DHTPIN 2
#define sensorPin A0
#define lightPin A1

#define redLEDPin 13
#define greenLEDPin 12

// Type of DHT sensor
#define DHTTYPE DHT11

Servo myservo;
DHT dht(DHTPIN, DHTTYPE);
aREST rest = aREST();

// telnet defaults to port 23
EthernetServer server = EthernetServer(23);

// the media access control (ethernet hardware) address for the shield:
byte mac[] = { 0xDE, 0xAD, 0xBE, 0xEF, 0xFE, 0xED };  
//the IP address for the shield:
byte ip[] = { 10, 0, 0, 177 };    
// the router's gateway address:
byte gateway[] = { 10, 0, 0, 1 };
// the subnet:
byte subnet[] = { 255, 255, 0, 0 };

float temperature;
float humidity;
float light;
float water;

void setup() {
  Serial.begin(115200);

  myservo.attach(9);
  pinMode(redLEDPin, OUTPUT);
  pinMode(greenLEDPin, OUTPUT);

  char const *var = "open"; 
  rest.function(var, waterPlant);
  
  rest.variable("temperature",&temperature);
  rest.variable("humidity",&humidity);
  rest.variable("light",&light);
  rest.variable("water",&water);

  Ethernet.begin(mac, ip, gateway, subnet);

  server.begin();
  Serial.print("server IP: ");
  Serial.println(Ethernet.localIP());

  rest.set_id("001");
  
  char const *name = "ready_set_grow"; 
  rest.set_name(name);
  
  dht.begin();

}

String measure_env() {
  
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  int w = analogRead(sensorPin);
  float l = analogRead(lightPin);

  humidity = dht.readHumidity();
  light = analogRead(lightPin);
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
    digitalWrite(redLEDPin, HIGH);  
    digitalWrite(greenLEDPin, LOW);  
    } else {
      digitalWrite(redLEDPin, LOW);  
    digitalWrite(greenLEDPin, HIGH);  
    }
  }

int waterPlant(String open){
  bool o = (open == "true");
  if (o){
    myservo.write(90);
  } else {
    myservo.write(140);
  }
  return 0;
}

void loop() {

delay(1000);
  // if an incoming client connects, there will be bytes available to read:
  EthernetClient client = server.available();
  if (client == true) {
    // read bytes from the incoming client and write them back
    // to any clients connected to the server:
    server.write(client.read());
  }
//  rest.publish(client, "temperature", temperature);
//  rest.publish(client, "humidity", humidity);
//  rest.publish(client, "light", light);
//  rest.publish(client, "water", water);
  rest.handle(Serial);
  changeLight();
  
//  delay(2000);
//  changeLight(false);
  

  String message = "";
  message += measure_env();
//  message = "{" + message + "}";
//
//  // Send message
//  Serial.println(message);
}





///* Change these values based on your calibration values */
//int lowerThreshold = 420;
//int upperThreshold = 520;
//
//// Sensor pins
//#define sensorPower 7
//#define sensorPin A0
//
//// Value for storing water level
//int val = 0;
//
//// Declare pins to which LEDs are connected
//int redLED = 2;
//int yellowLED = 3;
//int greenLED = 4;
//
//int adc_id = 0;
//int HistoryValue = 0;
//char printBuffer[128];

//void setup() {
//    Serial.begin(9600);
//    pinMode(sensorPower, OUTPUT);
//    digitalWrite(sensorPower, LOW);
//    
//    // Set LED pins as an OUTPUT
//    pinMode(redLED, OUTPUT);
//    pinMode(yellowLED, OUTPUT);
//    pinMode(greenLED, OUTPUT);
//  
//    // Initially turn off all LEDs
//    digitalWrite(redLED, LOW);
//    digitalWrite(yellowLED, LOW);
//    digitalWrite(greenLED, LOW);
//}

//void loop()
//{   
//    
//    /* CODE FROM ELEGOO
//    int value = analogRead(adc_id); // get adc value
// 
//    if(((HistoryValue>=value) && ((HistoryValue - value) > 10)) || ((HistoryValue<value) && ((value - HistoryValue) > 10)))
//    {
//      sprintf(printBuffer,"ADC %d level is %d\n",adc_id, value);
//    //Convert binary numbers "adc_id" and "value" into strings, and store the whole string "ADC "adc_id" level is "value"\n"in C.
//      
//      Serial.print(printBuffer);
//      HistoryValue = value;
//    }
//    */
//    
//    //Code I found Online
//    int level = readSensor();
//
////    if (level == 0) {
////      Serial.println("Water Level: Empty");
////      digitalWrite(redLED, LOW);
////      digitalWrite(yellowLED, LOW);
////      digitalWrite(greenLED, LOW);
////    }
////    else if (level > 0 && level <= lowerThreshold) {
////      Serial.println("Water Level: Low");
////      digitalWrite(redLED, HIGH);
////      digitalWrite(yellowLED, LOW);
////      digitalWrite(greenLED, LOW);
////    }
////    else if (level > lowerThreshold && level <= upperThreshold) {
////      Serial.println("Water Level: Medium");
////      digitalWrite(redLED, LOW);
////      digitalWrite(yellowLED, HIGH);
////      digitalWrite(greenLED, LOW);
////    }
////    else if (level > upperThreshold) {
////      Serial.println("Water Level: High");
////      digitalWrite(redLED, LOW);
////      digitalWrite(yellowLED, LOW);
////      digitalWrite(greenLED, HIGH);
////    }
//    delay(1000);
//}
//
//
////This is a function used to get the reading
//int readSensor() {
//    digitalWrite(sensorPower, HIGH);
//    delay(10);
//    val = analogRead(sensorPin);
//    digitalWrite(sensorPower, LOW);
//    return val;
//}
