package com.mycompany.datasproject;

import java.io.File;
import java.util.Scanner;

public class reading {

    public static void Load(String fileName) {
        String line = null;
        try {
            File f = new File(fileName);
            Scanner s = new Scanner(f);
            s.nextLine(); // Skips the first line (usually the header)
            while (s.hasNextLine()) {
                line = s.nextLine();
                 
/*                if (line.trim().length() < 3) {
                    System.out.println("Empty line is found, skipping this line = " + line);
                    break;
                }
*/
                System.out.println(line);
                // String[] values = line.split(",");
/*                String x = line.substring(0, line.indexOf(","));
                int id = Integer.parseInt(x.trim());
                String content = line.substring(line.indexOf(",") + 1).trim();
*/
            }
        } catch (Exception e) {
            System.out.println("end of file");
        }
    }

    public static void main(String[] args) {
        Load("dataset.csv");
        Load ("stop.txt");
    }
}
