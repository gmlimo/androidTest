#include <Arduino.h>
#include <WiFi.h>
#include <HTTPClient.h>
#include <ArduinoJson.h>

const char* ssid = "Redmi Note 8";
const char* password = "Limon_Cruiser";
char jsonOutput[128];
int LED = 2;

void setup() {
  Serial.begin(115200);
  pinMode(LED, OUTPUT);
  WiFi.begin(ssid, password);
  Serial.print("Connecting to WiFi");

  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(".");
    delay(500);
  }

  Serial.println("\nConnected to the WiFi network");
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

void loop() {
  if ((WiFi.status() == WL_CONNECTED)) {
    digitalWrite(LED, HIGH);

    HTTPClient client;

    client.begin("https://garden-21dd2-default-rtdb.firebaseio.com/location/house.json");
    client.addHeader("Content-Type", "application/json");

    const size_t CAPACITY = JSON_OBJECT_SIZE(3); //We will add three fields in the request
    StaticJsonDocument<CAPACITY> doc;

    JsonObject obj = doc.to<JsonObject>();

    obj["dhumidity"] = 0;    //Fields to be added
    obj["humidity"] = 98;
    obj["temperature"] = 69;

    serializeJson(doc, jsonOutput);
    Serial.println(jsonOutput);

    int httpCode = client.PUT(String(jsonOutput)); //PUT request

    if (httpCode == 200){ //If successfull we get a response back with the info we just sent
      String payload = client.getString();
      Serial.println(payload);

      client.end();
    }
    else {
      Serial.println("Error on HTTP request");
      Serial.println("\nStatus Code: " + String(httpCode));
    }
   
  }
  else {
    digitalWrite(LED, LOW);
  }
  delay(10000);
}

