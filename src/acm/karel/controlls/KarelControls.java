package acm.karel.controlls;

import acm.karel.Direction;
import acm.karel.Karel;
import acm.karel.components.KarelLoadDialog;
import acm.karel.components.KarelWorld;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class KarelControls extends JPanel implements ActionListener, ChangeListener, ListSelectionListener {
    private final CardLayout cardLayout;
    private final KarelStartControls startControls;
    private final KarelNewWorldControls newWorldControls;
    private final KarelEditControls editWorldControls;
    private final Runnable runnable;
    private final KarelWorld karelWorld;


    public KarelControls(Runnable runnable, KarelWorld karelWorld) {
        super();
        this.runnable = runnable;
        this.karelWorld = karelWorld;

        cardLayout = new CardLayout();
        setLayout(cardLayout);
        setPreferredSize(new Dimension(200, 0));

        startControls = new KarelStartControls(this);
        newWorldControls = new KarelNewWorldControls(this);
        editWorldControls = new KarelEditControls(this);
        karelWorld.setPauseLength(startControls.getPauseLength());

        add(startControls, "start");
        add(newWorldControls, "new");
        add(editWorldControls, "edit");
        cardLayout.show(this, "start");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());
        switch (e.getActionCommand()) {
            case "Start" -> {
                startControls.setEnabled(false);
                Thread karelThread = new Thread(runnable);
                karelThread.start();

                Thread waitThread = new Thread(() -> {
                    try {
                        karelThread.join();
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                    startControls.setEnabled(true);
                });
                waitThread.start();

            }
            case "Reset" -> karelWorld.resetMap();
            case "Load World" -> {
                KarelLoadDialog loadDialog = new KarelLoadDialog();
                File loadFile = new File(loadDialog.getDirectory() + loadDialog.getFile());
                if (loadFile.exists() && loadFile.isFile()) {
                    karelWorld.loadMap(loadFile);
                }
            }
            case "New World" -> cardLayout.show(this, "new");
            case "Edit World" -> {
                karelWorld.editMode = true;
                cardLayout.show(this, "edit");
            }
            case "Cancel" -> {
                karelWorld.editMode = false;
                cardLayout.show(this, "start");
            }
            case "NORTH", "EAST", "SOUTH", "WEST" -> {
                Direction direction = Direction.valueOf(e.getActionCommand());
                karelWorld.setKarelDirection(direction);
            }
            case "1", "10", "" + Karel.INFINITE -> {
                int amount = switch (e.getActionCommand()) {
                    case "1" -> 1;
                    case "10" -> 10;
                    case "" + Karel.INFINITE -> Karel.INFINITE;
                    default -> 0;
                };
                karelWorld.setBeeperEditAmount(amount);
            }
            case "0" -> karelWorld.setEditColor(new Color(0, 0, 0, 0));
            case "noColor" -> karelWorld.setEditColor(null);
            case "beeperBagButton" -> {
                karelWorld.addBeepersToBag(karelWorld.getBeeperEditAmount());
                editWorldControls.setBeeperBagAmount(karelWorld.getBeepersInBag());
            }
            case "beeperBagButtonRightClick" -> {
                karelWorld.removeBeepersFromBag(karelWorld.getBeeperEditAmount());
                editWorldControls.setBeeperBagAmount(karelWorld.getBeepersInBag());
            }
            default -> {
                Color color = Color.decode(e.getActionCommand());
                karelWorld.setEditColor(color);
            }
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        karelWorld.setPauseLength(startControls.getPauseLength());
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        System.out.println(e.getFirstIndex());
    }
}
