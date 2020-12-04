#include <Ethernet.h>
#include <DHT.h>
#define ethernet_h
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
  pinMode(redLED, OUTPUT);
  pinMode(greenLED, OUTPUT);

  char const *func = "openValve"; 
  rest.function(func, waterPlant);
  
  rest.variable("temperature",&temperature);
  rest.variable("humidity",&humidity);
  rest.variable("light",&light);
  rest.variable("water",&water);

  Ethernet.begin(mac);
//
//Ethernet.begin(mac, ip, gateway, subnet);

  server.begin();
  Serial.print("server IP: ");
  Serial.println(Ethernet.localIP());

  rest.set_id("001");
  
  char const *name = "ready_set_grow"; 
  rest.set_name(name);
  
  dht.begin();

    // Initially turn off all LEDs
    digitalWrite(redLED, LOW);
    digitalWrite(greenLED, LOW);

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
    digitalWrite(redLED, HIGH);  
    digitalWrite(greenLED, LOW);  
    } else {
      digitalWrite(redLED, LOW);  
    digitalWrite(greenLED, HIGH);  
    }
  }

int waterPlant(int open){
  
  if (open){
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
  Serial.println(client.remoteIP());
  if (client == true) {
    
    // read bytes from the incoming client and write them back
    // to any clients connected to the server:
    server.write(client.read());
  }

  rest.handle(client);
  changeLight();

  String message = "";
  message += measure_env();
  
//  message = "{" + message + "}";
//
//  // Send message
//  Serial.println(message);
}
