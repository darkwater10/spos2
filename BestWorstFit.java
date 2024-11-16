import java.util.*;

class MemoryBlock {
    int size;
    boolean isAllocated;

    MemoryBlock(int size) {
        this.size = size;
        this.isAllocated = false;
    }
}

class Process {
    int pid;
    int size;

    Process(int pid, int size) {
        this.pid = pid;
        this.size = size;
    }
}

public class BestWorstFit {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of memory blocks: ");
        int m = scanner.nextInt();

        MemoryBlock[] memoryBlocks = new MemoryBlock[m];

        System.out.println("Enter the size of memory blocks:");
        for (int i = 0; i < m; i++) {
            System.out.print("Memory block " + (i + 1) + " size: ");
            memoryBlocks[i] = new MemoryBlock(scanner.nextInt());
        }

        System.out.print("\nEnter the number of processes: ");
        int n = scanner.nextInt();

        Process[] processes = new Process[n];

        System.out.println("Enter the size of processes:");
        for (int i = 0; i < n; i++) {
            System.out.print("Process " + (i + 1) + " size: ");
            processes[i] = new Process(i + 1, scanner.nextInt());
        }

        // Best Fit Strategy
        System.out.println("\nBest Fit Allocation:");
        bestFit(memoryBlocks, processes);

        // Reset memory blocks before Worst Fit
        resetMemoryBlocks(memoryBlocks);

        // Worst Fit Strategy
        System.out.println("\nWorst Fit Allocation:");
        worstFit(memoryBlocks, processes);

        scanner.close();
    }

    // Best Fit allocation
    public static void bestFit(MemoryBlock[] memoryBlocks, Process[] processes) {
        for (Process process : processes) {
            int bestIdx = -1;
            int minDiff = Integer.MAX_VALUE;

            // Find the best block that fits the process
            for (int i = 0; i < memoryBlocks.length; i++) {
                if (!memoryBlocks[i].isAllocated && memoryBlocks[i].size >= process.size) {
                    int diff = memoryBlocks[i].size - process.size;
                    if (diff < minDiff) {
                        minDiff = diff;
                        bestIdx = i;
                    }
                }
            }

            // Allocate the process to the best block found
            if (bestIdx != -1) {
                memoryBlocks[bestIdx].isAllocated = true;
                System.out.println("Process " + process.pid + " allocated to memory block of size " + memoryBlocks[bestIdx].size);
            } else {
                System.out.println("Process " + process.pid + " could not be allocated.");
            }
        }
    }

    // Worst Fit allocation
    public static void worstFit(MemoryBlock[] memoryBlocks, Process[] processes) {
        for (Process process : processes) {
            int worstIdx = -1;
            int maxDiff = -1;

            // Find the worst block that fits the process
            for (int i = 0; i < memoryBlocks.length; i++) {
                if (!memoryBlocks[i].isAllocated && memoryBlocks[i].size >= process.size) {
                    int diff = memoryBlocks[i].size - process.size;
                    if (diff > maxDiff) {
                        maxDiff = diff;
                        worstIdx = i;
                    }
                }
            }

            // Allocate the process to the worst block found
            if (worstIdx != -1) {
                memoryBlocks[worstIdx].isAllocated = true;
                System.out.println("Process " + process.pid + " allocated to memory block of size " + memoryBlocks[worstIdx].size);
            } else {
                System.out.println("Process " + process.pid + " could not be allocated.");
            }
        }
    }

    // Reset the allocation status of memory blocks for the next strategy
    public static void resetMemoryBlocks(MemoryBlock[] memoryBlocks) {
        for (MemoryBlock block : memoryBlocks) {
            block.isAllocated = false;
        }
    }
}
