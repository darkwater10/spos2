import java.util.*;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
}

public class SJF {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.print("Enter the number of processes: ");
        int n = scanner.nextInt();
        
        Process[] processes = new Process[n];
        
        for (int i = 0; i < n; i++) {
            processes[i] = new Process();
            System.out.print("Enter arrival time for process " + (i + 1) + ": ");
            processes[i].arrivalTime = scanner.nextInt();
            System.out.print("Enter burst time for process " + (i + 1) + ": ");
            processes[i].burstTime = scanner.nextInt();
            processes[i].pid = i + 1;
        }
        
        Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
        
        int currentTime = 0;
        boolean[] processed = new boolean[n];
        int completedProcesses = 0;
        
        while (completedProcesses < n) {
            int idx = -1;
            int minBurstTime = Integer.MAX_VALUE;
            
            for (int i = 0; i < n; i++) {
                if (!processed[i] && processes[i].arrivalTime <= currentTime && processes[i].burstTime < minBurstTime) {
                    minBurstTime = processes[i].burstTime;
                    idx = i;
                }
            }
            
            if (idx != -1) {
                processed[idx] = true;
                processes[idx].completionTime = currentTime + processes[idx].burstTime;
                processes[idx].turnaroundTime = processes[idx].completionTime - processes[idx].arrivalTime;
                processes[idx].waitingTime = processes[idx].turnaroundTime - processes[idx].burstTime;
                currentTime = processes[idx].completionTime;
                completedProcesses++;
            } else {
                currentTime++;
            }
        }
        
        System.out.println("\nPID\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i].pid + "\t" + processes[i].arrivalTime + "\t\t" + processes[i].burstTime + "\t\t" + processes[i].completionTime + "\t\t" + processes[i].turnaroundTime + "\t\t" + processes[i].waitingTime);
        }
        
        scanner.close();
    }
}
