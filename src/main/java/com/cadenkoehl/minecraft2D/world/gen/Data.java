package com.cadenkoehl.minecraft2D.world.gen;

import com.cadenkoehl.minecraft2D.physics.Vec2d;
import com.cadenkoehl.minecraft2D.world.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Data {

    public static void saveJsonArrayToFile(JSONArray jsonArray, File file) {
        try {
            FileWriter write = new FileWriter(file);
            write.write(jsonArray.toString(4));
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject getWorldFileAsJSONObject(World world) {
        String jsonString = "";
        try {
            Scanner scan = new Scanner(world.getFile());
            while (scan.hasNextLine()) {
                jsonString = jsonString + scan.nextLine();
            }
            jsonString = jsonString.replace("[", "").replace("]", "");
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if(jsonString.isEmpty()) return new JSONObject("{}");
        return new JSONObject(jsonString);
    }

    public static Vec2d convertBlockKeyToLocation(String key) {
        String[] xy = key.replace(",", "").split("\\s+");
        int x = Integer.parseInt(xy[0]);
        int y = Integer.parseInt(xy[1]);
        return new Vec2d(x, y);
    }
}
