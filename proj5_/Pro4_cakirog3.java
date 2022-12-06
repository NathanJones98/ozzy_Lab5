import java.util.ArrayList;
import java.util.Arrays;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class Pro4_cakirog3 {
    public static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    private static int longestString(String[] values) {
        int maxLen = 0;
        for (String v : values) {
            if (v.length() > maxLen) maxLen = v.length();
        }
        return maxLen;
    }

    private static String paddedString(String x, int desiredLength) {
        int len = x.length();
        if (len >= desiredLength) return x;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < desiredLength - len; i++) {
            sb.append(" ");
        }
        sb.append(x);
        return sb.toString();
    }

    private static String[] gather(String[][] values, int idx) {
        String[] result = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            result[i] = values[i][idx];
        }
        return result;
    }

    public static void printResults(String[] header, String[][] values) {
        int furthestHeaderExtent = header[0].length();
        int furthestValueExtent = longestString(gather(values, 0));

        int[] spacing = new int[header.length];
        spacing[0] = Math.max(furthestHeaderExtent, furthestValueExtent);
        for (int i = 1; i < header.length; i++) {
            int nextFurthestHeaderExtent = header[i].length();
            int nextFurthestValueExtent = longestString(gather(values, i));
            int furthestExtent = Math.max(furthestHeaderExtent, furthestValueExtent);

            int paddingLength;
            if (nextFurthestHeaderExtent >= nextFurthestValueExtent) {
                paddingLength = nextFurthestHeaderExtent + 3;
            } else {
                paddingLength = nextFurthestValueExtent + 1;
            }

            spacing[i] = paddingLength;

            furthestHeaderExtent = nextFurthestHeaderExtent;
            furthestValueExtent = nextFurthestValueExtent;
        }

        String[] paddedHeaderRow = new String[header.length];
        for (int i = 0; i < header.length; i++) {
            paddedHeaderRow[i] = paddedString(header[i], spacing[i]);
        }
        String headerRow = String.join("", paddedHeaderRow);
        for (int i = 0; i < headerRow.length(); i++) {
            System.out.print("-");
        }
        System.out.println();
        System.out.println(headerRow);
        for (int i = 0; i < headerRow.length(); i++) {
            System.out.print("-");
        }

        for (int i = 0; i < values.length; i++) {
            System.out.println();
            for (int j = 0; j < values[i].length; j++) {
                System.out.print(paddedString(values[i][j], spacing[j]));
            }
        }
    }

    public static void displayMenu() {
        System.out.println("   JAVA POLYNOMIAL MINIMIZER (STEEPEST DESCENT)");
        System.out.println("L - Load polynomials from file");
        System.out.println("F - View polynomial functions");
        System.out.println("C - Clear polynomial functions");
        System.out.println("S - Set steepest descent parameters");
        System.out.println("P - View steepest descent parameters");
        System.out.println("R - Run steepest descent algorithms");
        System.out.println("D - Display algorithm performance");
        System.out.println("X - Compare average algorithm performance");
        System.out.println("Q - Quit");
        System.out.println();
    }

    public static void loadPolynomialFile(ArrayList<Polynomial> polynomials, String filename) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        String[] polynomialStrs = content.split("\n\\*\n");
        int temp = 1;
        int consistent = 1;
        for (int idx = 0; idx < polynomialStrs.length; idx++) {
            temp++;
            consistent = 1;
            String[] variables = polynomialStrs[idx].split("\n");
            String[][] coefficientStrs = new String[variables.length][];
            for (int i = 0; i < variables.length; i++) {
                coefficientStrs[i] = variables[i].split(",");
            }
            int a=1;
            while (a < variables.length) {
                if (coefficientStrs[a].length != coefficientStrs[a - 1].length) {
                    System.out.println("ERROR: Inconsistent dimensions in polynomial " + (temp + 1) + "!\n");
                    consistent = 0;
                    break;
                }
                a++;
            }
            if (consistent == 0) continue;
            double[][] coefficients = new double[variables.length][coefficientStrs[0].length];
            for (int i = 0; i < variables.length; i++) {
                for (int j = 0; j < coefficientStrs[i].length; j++) {
                    coefficients[i][j] = Double.parseDouble(coefficientStrs[i][j]);
                }
            }
            polynomials.add(new Polynomial(variables.length, coefficients[0].length - 1, coefficients));
        }
    }
    
    public static void getAlgorithmParams(SteepestDescent SD, int n) {
        SD.getParamsUser();
    }

    private static String[] polynomialColumns(int idx, Polynomial p) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        p.print();
        System.out.flush();
        System.setOut(old);
        String polynomialOut = baos.toString();
        String[] values = {
            String.valueOf(idx),
            String.valueOf(p.getDegree()),
            String.valueOf(p.getN()),
            polynomialOut
        };
        return values;
    }

    public static void printPolynomials(ArrayList<Polynomial> polynomials) {
        String[] headers = {
            "Poly No.",
            "Degree",
            "# vars",
            "Function"
        };
        String[][] columns = new String[polynomials.size()][];
        for (int i = 0; i < polynomials.size(); i++) {
            columns[i] = polynomialColumns(i + 1, polynomials.get(i));
        }
        printResults(headers, columns);
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Polynomial> polynomials = new ArrayList<Polynomial>();
        SteepestDescent SD = new SteepestDescent();
        try {
            while (true) {
                displayMenu();
                System.out.println("Enter choice: ");
                String a = Pro4_cakirog3.in.readLine();

                if (a.equals("L")) {
                    polynomials.clear();
                    System.out.println("Enter file name (0 to cancel): ");
                    String filename = Pro4_cakirog3.in.readLine();
                    if (filename.equals("0")) {
                        System.out.println("File loading process canceled.");
                    } else {
                        loadPolynomialFile(polynomials, filename);
                        System.out.printf("%d polynomials loaded!\n", polynomials.size());

                    }
                } else if (a.equals("F")) {
                    printPolynomials(polynomials);
                    System.out.println();
                } else if (a.equals("S")) {
                    if (polynomials.size() == 0) {
                        System.out.println("ERROR: No polynomial functions are loaded!");
                    } else {
                        SD.getParamsUser();
                    }
                } else if (a.equals("P")) {
                    SD.print();
                } else if (a.equals("R")) {
                    SD.init(polynomials);
                    for (int i = 0; i < polynomials.size(); i++) {
                        SD.run(i, polynomials.get(i));
                    }
                    System.out.println();
                    System.out.println("All polynomials done.");
                } else if (a.equals("X")) {
                    SD.init(polynomials);
                    for (int i = 0; i < polynomials.size(); i++) {
                        SD.run(i, polynomials.get(i));
                    }
                    System.out.println();
                    System.out.println("All polynomials done.");
                }else if (a.equals("D")) {
                    SD.printAll();
                } else if (a.equals("Q")) {
                    break;
                } else if (a.equals("C")) {
                    polynomials.clear();
                    System.out.println("All polynomials cleared.");
                } else {
                    System.out.println("ERROR: Invalid menu choice!");
                }

                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Error reading input.");
        }

        System.out.println("Fin.");
    }
}
