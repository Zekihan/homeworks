#include <Servo.h>

int ledPin = 13; // choose the pin for the LED
int inputPin = 12; // choose the input pin (for PIR sensor)
int pirState = LOW; // we start, assuming no motion detected
int val = 0; // variable for reading the pin status

Servo servo;

void setup() {
    pinMode(ledPin, OUTPUT);
    pinMode(inputPin, INPUT);

    servo.attach(11);
    servo.write(0);
    delay(500);
    delay(1000);

    Serial.begin(9600);
}

void loop() {
    val = digitalRead(inputPin); // read input value
    if (val == HIGH) { // check if the input is HIGH
        digitalWrite(ledPin, HIGH); // turn LED ON
        if (pirState == LOW) {
            // we have just turned on
            Serial.println("Motion detected!");

            servo.write(90);
            delay(500);
            delay(1000);

            pirState = HIGH;
        }
    } else {
        digitalWrite(ledPin, LOW); // turn LED OFF
        if (pirState == HIGH) {
            // we have just turned of
            Serial.println("Motion ended!");

            servo.write(0);
            delay(500);
            delay(1000);

            pirState = LOW;
        }
    }
}