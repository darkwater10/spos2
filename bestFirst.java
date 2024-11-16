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

public class bestFirst {
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

        // First Fit Strategy
        System.out.println("\nFirst Fit Allocation:");
        firstFit(memoryBlocks, processes);

        // Reset memory blocks before Best Fit
        resetMemoryBlocks(memoryBlocks);

        // Best Fit Strategy
        System.out.println("\nBest Fit Allocation:");
        bestFit(memoryBlocks, processes);

        scanner.close();
    }

    // First Fit allocation
    public static void firstFit(MemoryBlock[] memoryBlocks, Process[] processes) {
        for (Process process : processes) {
            boolean allocated = false;
            for (MemoryBlock block : memoryBlocks) {
                if (!block.isAllocated && block.size >= process.size) {
                    block.isAllocated = true;
                    System.out.println("Process " + process.pid + " allocated to memory block of size " + block.size);
                    allocated = true;
                    break;
                }
            }
            if (!allocated) {
                System.out.println("Process " + process.pid + " could not be allocated.");
            }
        }
    }

    // Best Fit allocation
    public static void bestFit(MemoryBlock[] memoryBlocks, Process[] processes) {
        for (Process process : processes) {
            int bestIdx = -1;
            int minDiff = Integer.MAX_VALUE;
            for (int i = 0; i < memoryBlocks.length; i++) {
                if (!memoryBlocks[i].isAllocated && memoryBlocks[i].size >= process.size) {
                    int diff = memoryBlocks[i].size - process.size;
                    if (diff < minDiff) {
                        minDiff = diff;
                        bestIdx = i;
                    }
                }
            }
            if (bestIdx != -1) {
                memoryBlocks[bestIdx].isAllocated = true;
                System.out.println("Process " + process.pid + " allocated to memory block of size " + memoryBlocks[bestIdx].size);
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
