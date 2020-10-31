#include <DHT.h>

// DHT22 DAT pin 
#define DHTPIN 2

// Type of DHT sensor
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}

String measure_env() {
  
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  String message = "";

  if (isnan(t) || isnan(h)) {
    return message;
  }

  // Create JSON as a message
  message = message + "{\"humidity\": ";
  message = message + h;
  message = message + ", \"temperature\": ";
  message = message + t;
  message = message + "}";
  
  return message;
}

void loop() {
  delay(2000);

  String message = measure_env();

  // Send message
  Serial.println(message);
}
