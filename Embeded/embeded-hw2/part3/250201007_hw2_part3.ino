long randNumber; //random number
int guess = 0; //current guess
int lastDist = 100; // previous guess' distance
int dist = 0; // current guess' distance
String command = ""; //string input

int red_light_pin = 13; // rgb red led pin
int green_light_pin = 10; // rgb green led pin
int blue_light_pin = 11; // rgb blue led pin

int r = 0; // rgb red value
int g = 0; // rgb green value
int b = 0; // rgb blue value

bool wait = false; // wait for input value
bool finished = false; // is game finished

int randUpper = 11; // upper bound for random number
int randLower = 1; // lower bound for random number
void setup() {
    Serial.begin(9600);

    pinMode(red_light_pin, OUTPUT);
    pinMode(green_light_pin, OUTPUT);
    pinMode(blue_light_pin, OUTPUT);

    randomSeed(analogRead(0));
    randNumber = random(randLower, randUpper);
    Serial.println(randNumber);
}
void loop() {
    RGB_color(r, g, b); // rgb blink
    delay(1000); // rgb blink
    RGB_color(0, 0, 0); // rgb blink
    delay(1000); // rgb blink
    if (!finished) {
        if (!wait) {
            Serial.println("Guess the number");
            wait = true;
        }
        if (Serial.available()) {
            wait = false;
            command = Serial.readStringUntil('\n');
            guess = command.toInt();
            Serial.print("You guessed: ");
            Serial.println(guess);
            if (randLower <= guess && guess < randUpper) {
                dist = abs(guess - randNumber);
                if (dist == 0) {
                    r = 0;
                    g = 255;
                    b = 0;
                    lastDist = dist;
                    Serial.println("You guessed it right");
                    finished = true;
                } else if (dist <= lastDist) {
                    r = 255;
                    g = 0;
                    b = 0;
                    lastDist = dist;
                    Serial.println("Getting hotter");
                } else if (dist > lastDist) {
                    r = 0;
                    g = 0;
                    b = 255;
                    lastDist = dist;
                    Serial.println("Getting colder");
                }
            } else {
                Serial.print("Your guess should be in between ");
                Serial.print(randLower);
                Serial.print(" and ");
                Serial.println(randUpper);

            }
        }
    }

}
void RGB_color(int red_light_value, int green_light_value, int blue_light_value) {
    analogWrite(red_light_pin, red_light_value);
    analogWrite(green_light_pin, green_light_value);
    analogWrite(blue_light_pin, blue_light_value);
}