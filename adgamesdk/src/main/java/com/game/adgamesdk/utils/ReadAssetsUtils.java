package com.game.adgamesdk.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 作者：heshuiguang
 * 日期：2020-06-03 16:19
 * 类说明：
 */
public class ReadAssetsUtils {



    private String secion;
    private Map<String, Properties> sections;
    private Properties properties;

    public ReadAssetsUtils(String filename) throws IOException {
        sections = new HashMap<String, Properties>();
        BufferedReader reader = new BufferedReader(new FileReader(filename));
        read(reader);
        reader.close();
    }

    private void read(BufferedReader reader) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            parseLine(line);
        }
    }

    private void parseLine(String line) {
        line = line.trim();
        if (line.matches("\\[.*\\]") == true) {
            secion = line.replaceFirst("\\[(.*)\\]", "$1");
            properties = new Properties();
            sections.put(secion, properties);
        } else if (line.matches(".*=.*") == true) {
            if (properties != null) {
                int i = line.indexOf('=');
                String name = line.substring(0, i);
                String value = line.substring(i + 1);
                properties.setProperty(name, value);
            }
        }
    }

    public String getValue(String section, String name) {
        Properties p = sections.get(section);

        if (p == null) {
            return null;
        }

        String value = p.getProperty(name);
        return value;
    }


}
