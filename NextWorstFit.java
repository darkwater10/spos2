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

public class NextWorstFit {
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

        // Next Fit Strategy
        System.out.println("\nNext Fit Allocation:");
        nextFit(memoryBlocks, processes);

        // Reset memory blocks before Worst Fit
        resetMemoryBlocks(memoryBlocks);

        // Worst Fit Strategy
        System.out.println("\nWorst Fit Allocation:");
        worstFit(memoryBlocks, processes);

        scanner.close();
    }

    // Next Fit allocation
    public static void nextFit(MemoryBlock[] memoryBlocks, Process[] processes) {
        int lastAllocatedIndex = 0;
        for (Process process : processes) {
            boolean allocated = false;
            for (int i = lastAllocatedIndex; i < memoryBlocks.length; i++) {
                if (!memoryBlocks[i].isAllocated && memoryBlocks[i].size >= process.size) {
                    memoryBlocks[i].isAllocated = true;
                    lastAllocatedIndex = i;  // Update the last allocated index
                    System.out.println("Process " + process.pid + " allocated to memory block of size " + memoryBlocks[i].size);
                    allocated = true;
                    break;
                }
            }
            if (!allocated) {
                System.out.println("Process " + process.pid + " could not be allocated.");
            }
        }
    }

    // Worst Fit allocation
    public static void worstFit(MemoryBlock[] memoryBlocks, Process[] processes) {
        for (Process process : processes) {
            int worstIdx = -1;
            int maxDiff = -1;
            for (int i = 0; i < memoryBlocks.length; i++) {
                if (!memoryBlocks[i].isAllocated && memoryBlocks[i].size >= process.size) {
                    int diff = memoryBlocks[i].size - process.size;
                    if (diff > maxDiff) {
                        maxDiff = diff;
                        worstIdx = i;
                    }
                }
            }
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
