import java.io.IOException;
import java.util.Scanner;

void main() throws InterruptedException, IOException {
    final Scanner SCANNER = new Scanner(System.in);
    final String RED = "\033[31m";
    final String BLUE = "\033[34m";
    final String RESET = "\033[0m";
    String colour;
    String name;
    String oldId = "S000";
    String newId;
    int idNum;
    int maxNameLength = 0;
    int studentCount = 0;
    int studentIndex;
    int pfMarks, osMarks;
    String allData = "";
    int total;
    double average;
    String grade;
    String bestStudentId = "";
    String worstStudentId = "";
    int bestStudentTotal = 0;
    int worstStudentTotal = 0;
    String searchOrDeleteStudentId;
    String selectedStudentData;
    int nextCommaIndex;
    int hyphenIndex;
    String hLine, titles, dataLine;
    String currentId;

    mainLoop:
    while (true) {
        new ProcessBuilder("clear").inheritIO().start().waitFor();

        System.out.println("=====================================");
        System.out.println("        WELCOME TO STUDENT DB");
        System.out.println("=====================================");

        System.out.println("1. Add new Student");
        System.out.println("2. Delete Student");
        System.out.println("3. Search Student");
        System.out.println("4. View All Students");
        System.out.println("5. Exit");

        System.out.println();
        while (true) {
            System.out.print("Enter a your command : ");

            switch (SCANNER.nextInt()) {

                case 1 -> {
                    SCANNER.skip("\n");

                    addStudentLoop:
                    while (true) {
                        new ProcessBuilder("clear").inheritIO().start().waitFor();

//            System.out.println("\033[H\033[2J"); // clear console
//            System.out.flush();
                        System.out.println("==========================");
                        System.out.println("      Add New Student");
                        System.out.println("==========================");

                        idNum = Integer.parseInt(oldId.substring(1)) + 1;
                        newId = String.format("S%03d", idNum);
                        System.out.println("Id is " + newId);

                        System.out.print("Enter student name : "); //Entering name
                        name = SCANNER.nextLine();

                        if (name.isBlank()) { // checking name validity
                            System.out.println("\033[31mInvalid Name\033[0m");
                            continue;
                        }

                        //Entering marks of 2 subjects
                        while (true) {
                            System.out.print("Enter Programming Fundamental Marks : ");
                            pfMarks = SCANNER.nextInt();

                            if (pfMarks < 0 || pfMarks > 100) {
                                System.out.println("\033[31mInvalid Marks\033[0m");
                                continue;
                            } else {
                                while (true) {
                                    System.out.print("Enter Operating Systems Marks : ");
                                    osMarks = SCANNER.nextInt();
                                    if (osMarks < 0 || osMarks > 100) {
                                        System.out.println("\033[31mInvalid Marks\033[0m");
                                        continue;
                                    }
                                    break;
                                }
                            }
                            break;
                        }

                        total = pfMarks + osMarks;
                        average = total / 2.0;

                        // getting best and worst students
                        if (studentCount == 0) {
                            worstStudentId = newId;
                            worstStudentTotal = total;

                            bestStudentId = newId;
                            bestStudentTotal = total;
                        } else {
                            if (total < worstStudentTotal) {
                                worstStudentTotal = total;
                                worstStudentId = newId;
                            } else if (total > bestStudentTotal) {
                                bestStudentTotal = total;
                                bestStudentId = newId;
                            }
                        }

                        // Finding grade
                        if (average >= 75) grade = "A";
                        else if (average >= 65) grade = "B";
                        else if (average >= 55) grade = "C";
                        else if (average >= 35) grade = "S";
                        else grade = "F";

                        if (name.length() > maxNameLength) maxNameLength = name.length(); // check max name length
                        //allIds += newId + ",";
                        //allNames += name + ",";
                        System.out.println("Added Successfully!");
                        studentCount++;
                        oldId = newId;
                        allData += String.format(newId + "-" + name + "-" + pfMarks + "-" + osMarks + "-" + total + "-%.2f-" + grade + ",", average);

                        System.out.println("All data : " + allData);
                        while (true) {
                            System.out.println("Do you want to add another student (y / n) ?");


                            switch (SCANNER.next()) {
                                case "y", "Y" -> {
                                    SCANNER.skip("\n");
                                    continue addStudentLoop;
                                }
                                case "n", "N" -> {
                                    continue mainLoop;
                                }
                                default -> System.out.println("\033[31mInvalid Command\033[0m");
                            }
                        }
                    }

                }
                case 2 -> {
                    SCANNER.skip("\n");

                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                    System.out.println("==========================");
                    System.out.println("     Delete Student");
                    System.out.println("==========================");
                    deleteStudentLoop:
                    while (true) {
                        System.out.print("Enter Student ID to delete : ");
                        searchOrDeleteStudentId = SCANNER.nextLine();
                        if (searchOrDeleteStudentId.isBlank()) {
                            System.out.println("ID is empty");
                            continue;
                        }
                        if (!allData.contains(searchOrDeleteStudentId + "-")) {
                            System.out.println("\033[31mID not found\033[0m");
                            continue;
                        }
                        studentIndex = allData.indexOf(searchOrDeleteStudentId);
                        nextCommaIndex = allData.indexOf(",", studentIndex);

                        selectedStudentData = allData.substring(studentIndex, nextCommaIndex + 1);

                        allData = allData.replace(selectedStudentData, "");
                        System.out.println("Deleted successfully");
                        studentCount--;

                        if (searchOrDeleteStudentId.equals(bestStudentId) || searchOrDeleteStudentId.equals(worstStudentId)) {
                            studentIndex = 0;
                            if (searchOrDeleteStudentId.equals(bestStudentId)) {
                                bestStudentTotal = 0;
                                // System.out.println("Best Deleted " + searchOrDeleteStudentId);
                            }
                            if (searchOrDeleteStudentId.equals(worstStudentId)) {
                                //  System.out.println("Worst Deleted " + searchOrDeleteStudentId);
                                worstStudentTotal = 200;
                            }

                            for (int i = 0; i < studentCount; i++) {
                                hyphenIndex = studentIndex;
                                currentId = allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex));

                                hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                                name = allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex));

                                hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                                pfMarks = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                                hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                                osMarks = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                                hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                                total = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                                if (total < worstStudentTotal) {
                                    worstStudentId = currentId;  // when worst student is deleted
                                    worstStudentTotal = total;
                                }
                                if (total > bestStudentTotal) {
                                    bestStudentId = currentId; // when best student is deleted
                                    bestStudentTotal = total;
                                }
                                studentIndex = allData.indexOf(",", studentIndex) + 1;
                            }
                        }

                        while (true) {
                            System.out.print("Do you want to delete another student (y / n) ? : ");

                            switch (SCANNER.next()) {
                                case "y", "Y" -> {
                                    SCANNER.skip("\n");
                                    continue deleteStudentLoop;
                                }
                                case "n", "N" -> {
                                    continue mainLoop;
                                }
                            }
                        }
                    }
                }
                case 3 -> {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                    System.out.println("==========================");
                    System.out.println("     Search Student");
                    System.out.println("==========================");
                    SCANNER.skip("\n");
                    searchStudentLoop:
                    while (true) {
                        //
                        System.out.print("Enter Student ID to search : ");
                        searchOrDeleteStudentId = SCANNER.nextLine();
                        if (searchOrDeleteStudentId.isBlank()) {
                            System.out.println("ID is empty");
                            continue;
                        }
                        if (!allData.contains(searchOrDeleteStudentId)) {
                            System.out.println("\033[31mID not found\033[0m");
                            continue;
                        }
                        studentIndex = allData.indexOf(searchOrDeleteStudentId);

                        // Get data of student
                        colour = RESET;
                        hyphenIndex = studentIndex;
                        currentId = allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex));

                        //if (currentId.equals(bestStudentId)) colour = BLUE;
                        //else if (currentId.equals(worstStudentId)) colour = RED;

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        name = allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        pfMarks = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        osMarks = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        total = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));


                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        average = Double.parseDouble(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        grade = allData.substring(hyphenIndex, allData.indexOf(",", hyphenIndex));

                        dataLine = String.format(STR."\{colour}Student Id : %-5s\nName : %-\{maxNameLength}s\nPF Marks : %-8d\nOS Marks : %-8d\nTotal : %-6d\nAverage : %-8.2f\nGrade : %-8s\{RESET}", currentId, name, pfMarks, osMarks, total, average, grade);

                        System.out.println(dataLine);

                        while (true) {
                            System.out.print("Do you want to search another student (y / n) ? : ");

                            switch (SCANNER.next()) {
                                case "y", "Y" -> {
                                    SCANNER.skip("\n");
                                    continue searchStudentLoop;
                                }
                                case "n", "N" -> {
                                    continue mainLoop;
                                }
                            }

                        }
                    }


                }
                case 4 -> {
                    new ProcessBuilder("clear").inheritIO().start().waitFor();
                    System.out.println("==========================");
                    System.out.println("     View All Students");
                    System.out.println("==========================");
                    studentIndex = 0;
                    hyphenIndex = -1;

                    if (studentCount == 0) maxNameLength = 10;

                    hLine = STR."+\{"-".repeat(5)}+\{"-".repeat(maxNameLength)}+\{"-".repeat(8)}+\{"-".repeat(8)}+\{"-".repeat(6)}+\{"-".repeat(8)}+\{"-".repeat(8)}+";
                    titles = String.format(STR."|%-5s|%-\{maxNameLength}s|%-8s|%-8s|%-6s|%-8s|%-8s|", "ID", "NAME", "PF MARKS", "OS MARKS", "TOTAL", "AVERAGE", "STATUS");
                    System.out.println(hLine);
                    System.out.println(titles);
                    System.out.println(hLine);

                    if (studentCount == 0) System.out.println("No Data Available");

                    for (int i = 0; i < studentCount; i++) {
                        colour = RESET;
                        hyphenIndex = studentIndex;
                        currentId = allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex));

                        if (currentId.equals(bestStudentId)) colour = BLUE;
                        else if (currentId.equals(worstStudentId)) colour = RED;

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        name = allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        pfMarks = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        osMarks = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        total = Integer.parseInt(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));


                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        average = Double.parseDouble(allData.substring(hyphenIndex, allData.indexOf("-", hyphenIndex)));

                        hyphenIndex = allData.indexOf("-", hyphenIndex) + 1;
                        grade = allData.substring(hyphenIndex, allData.indexOf(",", hyphenIndex));

                        dataLine = String.format(STR."|\{colour}%-5s\{RESET}|\{colour}%-\{maxNameLength}s\{RESET}|\{colour}%-8d\{RESET}|\{colour}%-8d\{RESET}|\{colour}%-6d\{RESET}|\{colour}%-8.2f\{RESET}|\{colour}%-8s\{RESET}|", currentId, name, pfMarks, osMarks, total, average, grade);
                        System.out.println(dataLine);
                        studentIndex = allData.indexOf(",", studentIndex) + 1;

                    }
                    System.out.println(hLine);
                    while (true) {
                        System.out.print("Do you want to go back to Home Menu (y / n) ? : ");

                        switch (SCANNER.next()) {
                            case "y", "Y" -> {
                                continue mainLoop;
                            }
                            case "n", "N" -> {
                                System.exit(0);
                            }
                            default -> System.out.println("\033[31mInvalid Command\033[0m");
                        }
                    }
                }
                case 5 -> {
                    System.exit(0);
                }
                default -> {
                    System.out.println("\033[31mInvalid Command\033[0m");
                    continue mainLoop;
                }

            }
        }
    }
}