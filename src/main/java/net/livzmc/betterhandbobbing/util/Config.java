package net.livzmc.betterhandbobbing.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Config {
    public static final String FILENAME = "config/BetterHandBobbing.json";

    public boolean HandBobEnabled = true;

    public Config() {
        try {
            FileReader reader = new FileReader(FILENAME);
            JsonParser jsonParser = new JsonParser();
            JsonElement rootElement = jsonParser.parse(reader);
            if (!rootElement.isJsonObject()) {
                throw new Exception("Root element is not a JSON object!");
            }
            HandBobEnabled = ((JsonObject) rootElement).get("HandBob").getAsBoolean();
        } catch (Exception e) {
            File file = new File(FILENAME);
            if (!file.exists()) {
                createConfig();
            }
            e.printStackTrace();
        }
    }

    private static void createConfig() {
        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write("{\"HandBob\": true}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try (FileWriter fileWriter = new FileWriter(FILENAME)) {
            fileWriter.write(String.format("{\"HandBob\": %s}", HandBobEnabled));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean getHandBob() {
        return this.HandBobEnabled;
    }

    public void setHandBob(Boolean option) {
        this.HandBobEnabled = option;
    }
}