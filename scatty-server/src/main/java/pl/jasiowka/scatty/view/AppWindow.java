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
package pl.jasiowka.scatty.view;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import pl.jasiowka.scatty.App;

/**
 * @author Piotr Jasiowka
 */
public class AppWindow {

    public enum Panel { Dictionary, Edit, Setup }
 
    private class JMultiPanel<P extends Enum<P>> extends JPanel {

        private static final long serialVersionUID = -8011673936771430989L;
        private CardLayout layout;
        private Map<P, JPanel> panelMap;

        public JMultiPanel(Class<P> cls) {
            layout = new CardLayout();
            setLayout(layout);
            panelMap = new HashMap<P, JPanel>();
            for (P panelName : cls.getEnumConstants()) {
                JPanel panel = new JPanel();
                panel.setName(panelName.toString());
                panelMap.put(panelName, panel);
                add(panel, panelName.toString());
            }
        }

        public JPanel getPanel(P name) {
            return panelMap.get(name);
        }

        public void showPanel(P name) {
          layout.show(JMultiPanel.this, name.toString());
        }

    }

    private static AppWindow singleton;
    private final JFrame window;
    private Dimension windowSize;
    private Dimension screenResolution;
    private JMultiPanel<Panel> multiPanel;
    private Timer timer;
    private DictionaryPanel dictionaryPanel;
    private EditPanel editPanel;
    private App app;

    public static AppWindow getWindow(App app) {
        if (singleton == null)
            singleton = new AppWindow(app);
        return singleton;
    }

    private AppWindow(App app) {
        this.app = app;
        window = new JFrame();
        window.setUndecorated(true);
        window.setAlwaysOnTop(true);
        screenResolution = getScreenResolution();
        windowSize = getWindowSize();
        window.setSize(windowSize);
        window.setLocation(locationBottomRight());
        timer = new Timer();
        multiPanel = new JMultiPanel<Panel>(Panel.class);
        window.getContentPane().add(multiPanel);
        dictionaryPanel = new DictionaryPanel(app, this, multiPanel.getPanel(Panel.Dictionary));
        editPanel = new EditPanel(app, this, multiPanel.getPanel(Panel.Edit));
    }

    private Dimension getScreenResolution() {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        return new Dimension(gd.getDisplayMode().getWidth(), gd.getDisplayMode().getHeight());
    }

    private Dimension getWindowSize() {
        return new Dimension(300, 200);
    }

    private Point locationBottomRight() {
        return new Point(screenResolution.width - windowSize.width, screenResolution.height - windowSize.height);
    }

    public void show(Panel name) {
        multiPanel.showPanel(name);
        window.setVisible(true);
    }

    public void hide() {
        window.setVisible(false);
    }

    private void hide(int milliseconds) {
        timer.schedule(new TimerTask () {
            public void run () {
                window.setVisible(false);
                timer.cancel();
            }
        }, milliseconds);
    }

    public void destroy() {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                timer.cancel();
                window.dispose();
            }
        });
    }

    public void showForEdit(String data, String meaning) {
        editPanel.setData(data);
        editPanel.setMeaning(meaning);
        show(Panel.Edit);
    }

    public void showForMeaning(String data) {
        dictionaryPanel.setData(data);
        show(Panel.Dictionary);
        hide(4000);
    }

}
