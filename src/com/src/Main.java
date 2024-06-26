package com.src;
import org.apache.commons.cli.*;


import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Options options = new Options();
        options.addOption("h", "help", false, "Print help");
        options.addOption("v", "version", false, "Print version");
        options.addOption("f", "file", true, "File to process");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("Main", options);
            } else if (cmd.hasOption("v")) {
                System.out.println("Software Lab 1.0.0");
            } else if(cmd.hasOption("f")) {
                System.out.println("Processing file: " + cmd.getOptionValue("f"));
                String filepath = cmd.getOptionValue("f");
                TextToGraph textToGraph = new TextToGraph();
                textToGraph.BuildGraph(filepath);
                Menu(textToGraph);
            } else
            {
                System.out.println("Hello, world!");
            }
        } catch (ParseException e) {
            System.err.println("Parsing failed.  Reason: " + e.getMessage());
        }
    }
    private static void Menu(TextToGraph textToGraph){
        while (true){
            System.out.println("1. Show Graph");
            System.out.println("2. Search Bridge Words");
            System.out.println("3. Generate New Text");
            System.out.println("4. Show Shortest Path");
            System.out.println("5. Random Walk");
            System.out.println("6. Exit");
            System.out.println("Enter your choice:");
            Scanner scanner = new Scanner(System.in);
            //边界处理
            while (!scanner.hasNextInt()){
                System.out.println("Invalid choice, please enter a number(choice 1-6):");
                scanner.next();
            }
            int choice = scanner.nextInt();
            switch (choice){
                case 1:
                    textToGraph.DrawGraphics();
                    break;
                case 2:
                    System.out.println("Enter word1:");
                    String word1 = scanner.next();
                    System.out.println("Enter word2:");
                    String word2 = scanner.next();
                    String result = textToGraph.queryBridgeWords(word1, word2);
                    if (result != null)
                        System.out.println("The bridge words from "+ word1 + " to "+ word2 + ":"+ result);
                    else
                        System.out.println("No bridge words from "+ word1 +" to "+ word2);
                    System.out.println();
                    break;
                case 3:
                    scanner.nextLine();
                    System.out.println("Enter text:");
                    String text = scanner.nextLine();
                    System.out.println("New text: " + textToGraph.generateNewText(text));
                    System.out.println();
                    break;
                case 4:
                    scanner.nextLine();
                    System.out.println("Enter word1:");
                    String node1 = scanner.nextLine();
                    System.out.println("Enter word2(can be null to calc all shortest path to all nodes):");
                    String node2 = scanner.nextLine();

                    if (!Objects.equals(node2, "") && node2!=null &&textToGraph.calcShortestPath(node1, node2) != null) {
                        System.out.println(textToGraph.calcShortestPath(node1, node2));
                    } else if(Objects.equals(node2, "") || node2==null){
                        String[] result1 = textToGraph.calcShortestPath(node1);
                        if (result1 != null) {
                            for (String s : result1) {
                                System.out.println(s);
                            }
                        }
                    }
                    else {
                        System.out.println("No path from " + node1 + " to " + node2);
                    }
                    break;
                case 5:
                    System.out.println(textToGraph.randomWalk());
                    System.out.println();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice");
            }
        }
    }
}

