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
package pl.jasiowka.scatty;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Piotr Jasiowka
 * @TODO być może chodzi o czas pomiędzy wykonaniem kolejnych komend. może trzeba trochę poczekać?
 */
public class Gnome3Like {

  private static final String S = " ";
  private static final String CMD = "dconf";
  private static final String RD = "read";
  private static final String WR = "write";
  private static final String SCHEMA = "/org/gnome/settings-daemon/plugins/media-keys";
  private static final String KEY = "/custom-keybindings";
  private static final String READ_BINDINGS = CMD + S + RD + S + SCHEMA + KEY;
  private static final String WRITE_BINDINGS = CMD + S + WR + S + SCHEMA + KEY + " \"<VALUES>\"";
  private static final String BINDING = SCHEMA + KEY + "/<BINDING>/";
  private static final String ATTR_NAME = CMD + S + WR + S + BINDING + "name '<VALUE>'";
  private static final String ATTR_BINDING = CMD + S + WR + S + BINDING + "binding '<VALUE>'";
  private static final String ATTR_COMMAND = CMD + S + WR + S + BINDING + "command '<VALUE>'";

  private class KeyBindings {
    String bindings;
    String newBindingId;
    String newBindingName;
    String newBindingBinding;
    String newBindingCommand;
  }

  private List<String> shellCommand(String command) throws IOException{
    List<String> output = new ArrayList<String>();
    Process proc = Runtime.getRuntime().exec(command);
    BufferedInputStream buffer = new BufferedInputStream(proc.getInputStream());
    BufferedReader commandOutput = new BufferedReader(new InputStreamReader(buffer));
    String line = null;
    while ((line = commandOutput.readLine()) != null)
      output.add(line);
    commandOutput.close();
    return output;
  }

  private KeyBindings readBindings() throws IOException {
    KeyBindings kb = new KeyBindings();
    kb.bindings = shellCommand(READ_BINDINGS).get(0);
    return kb;
  }

  private void writeBindings(KeyBindings kb) throws IOException {
    shellCommand(WRITE_BINDINGS.replace("<VALUES>", kb.bindings));
    shellCommand(ATTR_NAME.replace("<BINDING>", kb.newBindingId).replace("<VALUE>", kb.newBindingName));
    shellCommand(ATTR_BINDING.replace("<BINDING>", kb.newBindingId).replace("<VALUE>", kb.newBindingBinding));
    shellCommand(ATTR_COMMAND.replace("<BINDING>", kb.newBindingId).replace("<VALUE>", kb.newBindingCommand));
//    System.out.println(WRITE_BINDINGS.replace("<VALUES>", kb.bindings));
//    System.out.println(ATTR_NAME.replace("<BINDING>", kb.newBindingId).replace("<VALUE>", kb.newBindingName));
//    System.out.println(ATTR_BINDING.replace("<BINDING>", kb.newBindingId).replace("<VALUE>", kb.newBindingBinding));
//    System.out.println(ATTR_COMMAND.replace("<BINDING>", kb.newBindingId).replace("<VALUE>", kb.newBindingCommand));
  }

  private KeyBindings updateBindings(KeyBindings kb) throws IOException, IllegalArgumentException {
    kb.newBindingId = "custom" + kb.bindings.split("[,]").length;
    kb.bindings = kb.bindings.replace("]", ", '" + BINDING.replace("<BINDING>", kb.newBindingId) + "']");
    return kb;
  }

  public void registerNewKeyBinding(String name, String binding, String command) throws IOException {
    KeyBindings kb = readBindings();
    kb.newBindingName = name;
    kb.newBindingBinding = binding;
    kb.newBindingCommand = command;
    kb = updateBindings(kb);
	writeBindings(kb);
  }

}
