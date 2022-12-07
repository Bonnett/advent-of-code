package uk.co.pete_b.advent.aoc2022;

import org.apache.commons.lang3.builder.HashCodeBuilder;
import uk.co.pete_b.advent.utils.TreeNode;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Day07 {

    private static final int TOTAL_DISK_SPACE = 70000000;
    private static final int NEEDED_DISK_SPACE = 30000000;

    public static Answer findTotalSizeOfSub100KDirectories(final List<String> fileSystemInfo) {
        final Map<String, TreeNode<String>> directories = buildFileSystem(fileSystemInfo);

        int totalSizeOfSub100kDirectories = 0;

        for (Map.Entry<String, TreeNode<String>> entry : directories.entrySet()) {
            final int treeSize = entry.getValue().getTotalSize();

            if (treeSize <= 100000) {
                totalSizeOfSub100kDirectories += treeSize;
            }
        }


        final int neededToDelete = NEEDED_DISK_SPACE - (TOTAL_DISK_SPACE - directories.get("/").getTotalSize());

        int smallestToDelete = Integer.MAX_VALUE;

        for (Map.Entry<String, TreeNode<String>> entry : directories.entrySet()) {
            final int treeSize = entry.getValue().getTotalSize();

            if (treeSize < smallestToDelete && treeSize >= neededToDelete) {
                smallestToDelete = treeSize;
            }
        }

        return new Answer(totalSizeOfSub100kDirectories, smallestToDelete);
    }

    private static Map<String, TreeNode<String>> buildFileSystem(final List<String> fileSystemInfo) {
        final Map<String, TreeNode<String>> directories = new LinkedHashMap<>();
        final TreeNode<String> fileSystem = new TreeNode<>("/");

        String currentDir;
        TreeNode<String> currentLocation = fileSystem;

        for (int i = 0; i < fileSystemInfo.size(); i++) {
            String instruction = fileSystemInfo.get(i);
            if (instruction.startsWith("$ cd")) {
                currentDir = instruction.split(" ")[2];

                if (currentDir.equals("/")) {
                    currentLocation = fileSystem;
                    directories.put(currentDir, currentLocation);
                } else if (currentDir.equals("..")) {
                    currentLocation = currentLocation.getParentNode();
                } else {
                    currentLocation = currentLocation.getChildren().get(currentDir);
                }
            } else if (instruction.equals("$ ls")) {
                while (i < fileSystemInfo.size() - 1) {
                    i++;
                    instruction = fileSystemInfo.get(i);
                    if (instruction.startsWith("dir")) {
                        final String dirname = instruction.split(" ")[1];
                        final TreeNode<String> directory = new TreeNode<>(dirname);

                        TreeNode<String> loc = currentLocation;
                        final StringBuilder dirPath = new StringBuilder(dirname);
                        while (!loc.getName().equals("/")) {
                            dirPath.insert(0, loc.getName() + "/");
                            loc = loc.getParentNode();
                        }
                        directories.put("/" + dirPath, directory);
                        currentLocation.addChild(directory);
                    } else if (instruction.startsWith("$")) {
                        i--;
                        break;
                    } else {
                        final String[] file = instruction.split(" ");
                        final int fileSize = Integer.parseInt(file[0]);
                        final String fileName = file[1];
                        currentLocation.addChild(new TreeNode<>(fileName));
                        currentLocation.getChildren().get(fileName).setSize(fileSize);
                    }
                }
            }
        }

        return directories;
    }

    public static class Answer {
        private final int sumOfSub100KDirectories;
        private final int sizeOfSmallestDirectoryToDelete;

        Answer(final int sumOfSub100KDirectories, final int sizeOfSmallestDirectoryToDelete) {
            this.sumOfSub100KDirectories = sumOfSub100KDirectories;
            this.sizeOfSmallestDirectoryToDelete = sizeOfSmallestDirectoryToDelete;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this, false);
        }

        @Override
        public boolean equals(final Object otherAnswer) {
            boolean isEqual = false;
            if (otherAnswer instanceof Answer) {
                isEqual = ((Answer) otherAnswer).sumOfSub100KDirectories == this.sumOfSub100KDirectories && ((Answer) otherAnswer).sizeOfSmallestDirectoryToDelete == this.sizeOfSmallestDirectoryToDelete;
            }

            return isEqual;
        }

        @Override
        public String toString() {
            return String.format("Sum Of Sum 100K directories: %d, Smallest Directory To Delete: %d", sumOfSub100KDirectories, sizeOfSmallestDirectoryToDelete);
        }
    }
}
