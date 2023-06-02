#include <Arduino.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

//The ID and Password from our WiFi network
const char* ssid = "Redmi Note 8";
const char* password = "Limon_Cruiser";

//A LED to be used as successfull connection
int LED = 2;

void setup() {
  Serial.begin(115200);
  pinMode(LED, OUTPUT); //LED configured as output
  WiFi.begin(ssid, password); //Initialize connection
  Serial.print("Connecting to WiFi");

  while (WiFi.status() != WL_CONNECTED) { //While trying to connect ... will be added
    Serial.print(".");
    delay(500);
  }

  Serial.println("\nConnected to the WiFi network"); //If connected the IP will be printed
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  if ((WiFi.status() == WL_CONNECTED)) {
    digitalWrite(LED, HIGH); //Once connected the LED will turn on

    HTTPClient client; //HTTP Client is used with a get request

    //The url is used to establish a connection with a REST API
    client.begin("https://garden-21dd2-default-rtdb.firebaseio.com/location/house.json");
    int httpCode = client.GET();

    if (httpCode == 200){ //If the GET request is successfull all the response is loaded in payload
      String payload = client.getString();
      Serial.println(payload);

      //The response received a little modifications, space removing and info trimmed and converted into an Array
      char json[100]; 
      payload.replace(" ", "");
      payload.replace("\n", "");
      payload.trim();
      payload.toCharArray(json, 100);

      //Info is deserialized in a document
      StaticJsonDocument<100> doc;
      deserializeJson(doc, json);

      //Search for the desired key and parse it to a variable
      int dhumidity = doc["dhumidity"];
      //const char* email = doc["email"];

      Serial.println("dhumidity: " + String(dhumidity)); 

      client.end();
    }
    else {
      Serial.println("Error on HTTP request"); //If the connection with the REST API isn´t established, a message is printed with the error code
      Serial.println("\nStatus Code: " + String(httpCode));
    }
   
  }
  else {
    digitalWrite(LED, LOW); //If the WiFi connection is not established the LED is off
  }
  delay(10000);
}

