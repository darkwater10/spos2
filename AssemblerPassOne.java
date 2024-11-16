import java.io.*;
import java.util.*;

public class AssemblerPassOne {
    static Map<String, Integer> symbolTable = new LinkedHashMap<>();
    static List<String> literalTable = new ArrayList<>();
    static List<String> poolTable = new ArrayList<>();
    static List<String> intermediateCode = new ArrayList<>();
    static Map<String, String> mnemonicTable = new HashMap<>();
    static int locationCounter = 0;

    public static void main(String[] args) {
        try {
            initializeMnemonicTable();
            List<String> code = readAssemblyCode("input.asm");
            passOne(code.toArray(new String[0]));
            writeOutputFiles();
            System.out.println("Files generated: ic, lit, pool, sym, mnemonictable");
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    static List<String> readAssemblyCode(String filename) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line.trim());
            }
        }
        return lines;
    }

    static void passOne(String[] code) {
        for (String line : code) {
            String[] tokens = line.split("\\s+");

            if (tokens[0].equals("START")) {
                locationCounter = Integer.parseInt(tokens[1]); // Initialize LC
                intermediateCode.add("(AD,01) C" + tokens[1]);
            } else if (tokens[0].equals("END")) {
                intermediateCode.add("(AD,02)");
            } else if (tokens[1].equals("DS") || tokens[1].equals("DC")) {
                symbolTable.put(tokens[0], locationCounter);
                intermediateCode.add("(DL,01) C" + tokens[1] + "," + tokens[2]);
                locationCounter++;
            } else {
                if (!symbolTable.containsKey(tokens[0]) && !tokens[0].equals("")) {
                    symbolTable.put(tokens[0], locationCounter);
                }

                // Process instructions
                String mnemonic = tokens[0].isEmpty() ? tokens[1] : tokens[0];
                if (tokens.length > 2 && tokens[2].startsWith("=")) {
                    if (!literalTable.contains(tokens[2])) {
                        literalTable.add(tokens[2]);
                    }
                }

                String instr = "(" + mnemonicTable.getOrDefault(mnemonic, "IS,00") + ") " +
                        (tokens.length > 2 ? tokens[2] : "");
                intermediateCode.add(instr);
                locationCounter++;
            }
        }

        // Populate pool table for literals
        for (int i = 0; i < literalTable.size(); i++) {
            poolTable.add("L" + i + ": " + literalTable.get(i));
        }
    }

    static void initializeMnemonicTable() {
        mnemonicTable.put("START", "AD,01");
        mnemonicTable.put("END", "AD,02");
        mnemonicTable.put("MOVER", "IS,04");
        mnemonicTable.put("ADD", "IS,05");
        mnemonicTable.put("SUB", "IS,06");
        mnemonicTable.put("JUMP", "IS,07");
        mnemonicTable.put("DS", "DL,01");
        mnemonicTable.put("DC", "DL,02");
    }

    static void writeOutputFiles() throws IOException {
        writeToFile("ic", intermediateCode);
        writeToFile("lit", literalTable);
        writeToFile("pool", poolTable);
        writeToFile("sym", formatSymbolTable());
        writeToFile("mnemonictable", formatMnemonicTable());
    }

    static void writeToFile(String filename, List<String> data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (String line : data) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    static List<String> formatSymbolTable() {
        List<String> formatted = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : symbolTable.entrySet()) {
            formatted.add(entry.getKey() + " -> " + entry.getValue());
        }
        return formatted;
    }

    static List<String> formatMnemonicTable() {
        List<String> formatted = new ArrayList<>();
        for (Map.Entry<String, String> entry : mnemonicTable.entrySet()) {
            formatted.add(entry.getKey() + " -> " + entry.getValue());
        }
        return formatted;
    }
}
