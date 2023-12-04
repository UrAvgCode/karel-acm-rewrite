package acm.karel.map;

import acm.karel.Direction;

import java.io.*;

public class KarelMapLoader {
    public File file = null;

    public KarelMapData loadWorld() {
        return loadWorld(file);
    }

    public KarelMapData loadWorld(String filename) {
        return loadWorld(new File(filename));
    }

    public KarelMapData loadWorld(File file) {
        this.file = file;

        try {
            FileReader input = new FileReader(file);
            BufferedReader reader = new BufferedReader(input);

            KarelMapData map;

            String firstLine = reader.readLine();
            String[] dimension = firstLine.split("(:?,?[)]?\\s+[(]?)|[)]$");
            if (dimension[0].equals("D") || dimension[0].equals("Dimension")) {
                int width = Integer.parseInt(dimension[1]);
                int height = Integer.parseInt(dimension[2]);
                map = new KarelMapData(width, height);
            } else {
                throw new RuntimeException("Invalid file format");
            }

            String line;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split("(:?,?[)]?\\s+[(]?)|[)]$");

                switch (tokens[0]) {
                    case "Wall", "W" -> {
                        int x = Integer.parseInt(tokens[1]) - 1;
                        int y = Integer.parseInt(tokens[2]) - 1;
                        String direction = tokens[3];

                        switch (direction) {
                            case "south", "s" -> map.getCell(x, y).addWallSouth();
                            case "west", "w" -> map.getCell(x, y).addWallWest();
                        }
                    }
                    case "Beeper", "B" -> {
                        int x = Integer.parseInt(tokens[1]) - 1;
                        int y = Integer.parseInt(tokens[2]) - 1;
                        int beepers = Integer.parseInt(tokens[3]);

                        map.getCell(x, y).addBeepers(beepers);
                    }
                    case "Karel" -> {
                        int x = Integer.parseInt(tokens[1]) - 1;
                        int y = Integer.parseInt(tokens[2]) - 1;

                        map.karelDirection = switch (tokens[3]) {
                            case "north", "n" -> Direction.NORTH;
                            case "east", "e" -> Direction.EAST;
                            case "south", "s" -> Direction.SOUTH;
                            case "west", "w" -> Direction.WEST;
                            default -> throw new IllegalStateException("Unexpected value: " + tokens[3]);
                        };

                        map.karelX = x;
                        map.karelY = y;
                    }
                }
            }

            reader.close();
            input.close();

            return map;
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + file);
            return loadDefaultWorld();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public KarelMapData loadDefaultWorld() {
        return new KarelMapData();
    }

    public void safeWorld(String fileName, KarelCell[][] world) {
        try {
            FileWriter output = new FileWriter(fileName);
            BufferedWriter writer = new BufferedWriter(output);

            writer.write("D " + world[0].length + " " + world.length + "\n");

            for (int y = 0; y < world.length; y++) {
                for (int x = 0; x < world[y].length; x++) {
                    if (world[y][x].hasWallSouth()) {
                        writer.write("W " + (x + 1) + " " + (y + 1) + " s\n");
                    }
                    if (world[y][x].hasWallWest()) {
                        writer.write("W " + (x + 1) + " " + (y + 1) + " w\n");
                    }
                    if (world[y][x].hasBeepers()) {
                        writer.write("B " + (x + 1) + " " + (y + 1) + " " + world[y][x].getBeepers() + "\n");
                    }
                }
            }

            writer.close();
            output.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
