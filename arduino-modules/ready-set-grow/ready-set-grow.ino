#include <DHT.h>
#include <Servo.h>

// DHT22 DAT pin 
#define DHTPIN 2
#define sensorPin A0

// Type of DHT sensor
#define DHTTYPE DHT11

Servo myservo;
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
  myservo.attach(9);
}

String measure_env() {
  
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  int w = analogRead(sensorPin);

  String message = "";

  if (isnan(t) || isnan(h) || isnan(w)) {
    return message;
  }

  // Create JSON as a message
  message = message + "\"humidity\": " + h;
  message = message + ", \"temperature\": " + t;
  message = message + ", \"water_level\": " + w;
  
  return message;
}

void water(bool open){
  if (open){
    myservo.write(90);
  } else {
    myservo.write(150);
  }
}

void loop() {
  delay(2000);

  water(true);

  String message = "";
  message += measure_env();
  message = "{" + message + "}";

  // Send message
  Serial.println(message);
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
