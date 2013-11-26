/*
* Copyright (C) 2013 Piotr Jasiowka. All rights reserved.
*
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 2 of the License, or
* (at your option) any later version.
*
* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
* GNU General Public License for more details.
*
* You should have received a copy of the GNU General Public License
* along with this program. If not, see <http://www.gnu.org/licenses/>.
*
*/
package pl.jasiowka.scatty.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Piotr Jasiowka
 */
public class XmlConfig implements Config {

    private static final String FILENAME = "config.xml";
    private Properties properties;

  private Map<String, String> basicConf() {
    Map<String, String> basicConf = new HashMap<String, String>();
    basicConf.put("RmiPort", "3131");
    // basic configuration here..
    return basicConf;
  }

  public XmlConfig() {
    loadSettings();
  }

  public void loadSettings() {
    try {
      properties = new Properties();
      File file = new File(FILENAME);
      if (file.exists()) {
        FileInputStream fileInput = new FileInputStream(file);
        properties.loadFromXML(fileInput);
        fileInput.close();
      }
      else
        properties.putAll(basicConf());
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void storeSettings() {
    try {
      File file = new File(FILENAME);
      FileOutputStream fileOut = new FileOutputStream(file);
      properties.storeToXML(fileOut, "Scatty config file");
      fileOut.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public int getRmiPort() {
    return Integer.parseInt(properties.getProperty("RmiPort"));
  }

  @Override
  public void setRmiPort(int port) {
    properties.put("RmiPort", String.valueOf(port));
  }

}
