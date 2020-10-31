//www.elegoo.com
//2016.12.9

// Code from https://lastminuteengineers.com/water-level-sensor-arduino-tutorial/?fbclid=IwAR2nURCrm133D6ezx--GU59CJ8EP9dyatZHpW9rac3m8Gdq5JgxKfDQRSJA

/*
 * I copied code from this website and I think we can just work off of this to read the water level
 * It even has the LED light thingns we wanted, and we can just simplify modify some numbers
 */

/* Change these values based on your calibration values */
int lowerThreshold = 420;
int upperThreshold = 520;

// Sensor pins
#define sensorPower 7
#define sensorPin A0

// Value for storing water level
int val = 0;

// Declare pins to which LEDs are connected
int redLED = 2;
int yellowLED = 3;
int greenLED = 4;

int adc_id = 0;
int HistoryValue = 0;
char printBuffer[128];

void setup() {
    Serial.begin(9600);
    pinMode(sensorPower, OUTPUT);
    digitalWrite(sensorPower, LOW);
    
    // Set LED pins as an OUTPUT
    pinMode(redLED, OUTPUT);
    pinMode(yellowLED, OUTPUT);
    pinMode(greenLED, OUTPUT);
  
    // Initially turn off all LEDs
    digitalWrite(redLED, LOW);
    digitalWrite(yellowLED, LOW);
    digitalWrite(greenLED, LOW);
}

void loop()
{   
    
    /* CODE FROM ELEGOO
    int value = analogRead(adc_id); // get adc value
 
    if(((HistoryValue>=value) && ((HistoryValue - value) > 10)) || ((HistoryValue<value) && ((value - HistoryValue) > 10)))
    {
      sprintf(printBuffer,"ADC %d level is %d\n",adc_id, value);
    //Convert binary numbers "adc_id" and "value" into strings, and store the whole string "ADC "adc_id" level is "value"\n"in C.
      
      Serial.print(printBuffer);
      HistoryValue = value;
    }
    */
    
    //Code I found Online
    int level = readSensor();

    if (level == 0) {
      Serial.println("Water Level: Empty");
      digitalWrite(redLED, LOW);
      digitalWrite(yellowLED, LOW);
      digitalWrite(greenLED, LOW);
    }
    else if (level > 0 && level <= lowerThreshold) {
      Serial.println("Water Level: Low");
      digitalWrite(redLED, HIGH);
      digitalWrite(yellowLED, LOW);
      digitalWrite(greenLED, LOW);
    }
    else if (level > lowerThreshold && level <= upperThreshold) {
      Serial.println("Water Level: Medium");
      digitalWrite(redLED, LOW);
      digitalWrite(yellowLED, HIGH);
      digitalWrite(greenLED, LOW);
    }
    else if (level > upperThreshold) {
      Serial.println("Water Level: High");
      digitalWrite(redLED, LOW);
      digitalWrite(yellowLED, LOW);
      digitalWrite(greenLED, HIGH);
    }
    delay(1000);
}


//This is a function used to get the reading
int readSensor() {
    digitalWrite(sensorPower, HIGH);
    delay(10);
    val = analogRead(sensorPin);
    digitalWrite(sensorPower, LOW);
    return val;
}
