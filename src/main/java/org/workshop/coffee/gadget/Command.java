package org.workshop.coffee.gadget;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

public class Command implements Runnable, Serializable {

    private String command;

    public Command(String command) {
        this.command = command;
    }

    @Override
    public void run() {
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

            String line;
            StringBuilder output = new StringBuilder();

            while ((line = stdInput.readLine()) != null) {
                output.append("\n");
                output.append(line);
            }

            while ((line = stdError.readLine()) != null) {
                output.append("\n");
                output.append(line);
            }

            process.waitFor();

            System.out.println(output.toString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }




}
