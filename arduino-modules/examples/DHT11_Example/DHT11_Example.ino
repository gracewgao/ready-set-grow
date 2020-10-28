#include <DHT.h> //IDK how to fix this

// DHT22 DAT pin 
#define DHTPIN 12

// Type of DHT sensor
#define DHTTYPE DHT11

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();
}

void loop() {
  delay(2000);
  float h = dht.readHumidity();
  float t = dht.readTemperature();

  String message = "";

  if (isnan(h) || isnan(t)) {
    return;
  }

  // Create JSON as a message
  message = message + "{\"humidity\": ";
  message = message + h;
  message = message + ", \"temperature\": ";
  message = message + t;
  message = message + "}";

  // Send message
  Serial.println(message);
}
/*
#include <DHT.h>
#include <DHT_U.h>

#include <DHT.h>
#include <DHT_U.h>

#include <DHT.h>
#include <DHT_U.h>

//www.elegoo.com
//2018.10.25


#include "dht_nonblocking.h"
#define DHT_SENSOR_TYPE DHT_TYPE_11

static const int DHT_SENSOR_PIN = 2;
DHT_nonblocking dht_sensor( DHT_SENSOR_PIN, DHT_SENSOR_TYPE );



/*
 * Initialize the serial port.

void setup( )
{
  Serial.begin( 9600);
}



/*
 * Poll for a measurement, keeping the state machine alive.  Returns
 * true if a measurement is available.

static bool measure_environment( float *temperature, float *humidity )
{
  static unsigned long measurement_timestamp = millis( );

  /* Measure once every four seconds.
  if( millis( ) - measurement_timestamp > 3000ul )
  {
    if( dht_sensor.measure( temperature, humidity ) == true )
    {
      measurement_timestamp = millis( );
      return( true );
    }
  }

  return( false );
}



/*
 * Main program loop.

void loop( )
{
  float temperature;
  float humidity;

  /* Measure temperature and humidity.  If the functions returns
     true, then a measurement is available.
  if( measure_environment( &temperature, &humidity ) == true )
  {
    Serial.print( "T = " );
    Serial.print( temperature, 1 );
    Serial.print( " deg. C, H = " );
    Serial.print( humidity, 1 );
    Serial.println( "%" );
  }
}
*/
