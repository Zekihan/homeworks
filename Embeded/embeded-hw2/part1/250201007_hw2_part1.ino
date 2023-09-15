const int buttonPin = 7; // the pin that the pushbutton is attached to
const int ledPin = 13; // the pin that the LED is attached to

int buttonPushCounter = 0; // counter for the number of button presses
int buttonState = 0; // current state of the button
int lastButtonState = 0; // previous state of the button

void setup() {
    pinMode(buttonPin, INPUT);
    pinMode(ledPin, OUTPUT);
    Serial.begin(9600);
}

void loop() {
    // read the pushbutton input pin:
    buttonState = digitalRead(buttonPin);

    // compare the buttonState to its previous state
    if (buttonState != lastButtonState) {
        // if the state has changed, increment the counter
        if (buttonState == HIGH) {
            // if the current state is HIGH then the button went from off to on:
            buttonPushCounter++;
            Serial.println("on");
            Serial.print("number of button pushes: ");
            Serial.println(buttonPushCounter);
        } else {
            // if the current state is LOW then the button went from on to off:
            Serial.println("off");
        }
        // Delay a little bit to avoid bouncing
        delay(50);
    }
    // save the current state as the last state, for next time through the loop
    lastButtonState = buttonState;

    if (buttonPushCounter == 5) {
        digitalWrite(ledPin, HIGH);
        delay(250);
        digitalWrite(ledPin, LOW);
        delay(250);
    }
}