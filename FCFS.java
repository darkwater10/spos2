import java.util.Scanner;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
}

public class FCFS {
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
        
        int currentTime = 0;
        
        for (int i = 0; i < n; i++) {
            if (processes[i].arrivalTime > currentTime) {
                currentTime = processes[i].arrivalTime;
            }
            processes[i].completionTime = currentTime + processes[i].burstTime;
            processes[i].turnaroundTime = processes[i].completionTime - processes[i].arrivalTime;
            processes[i].waitingTime = processes[i].turnaroundTime - processes[i].burstTime;
            currentTime = processes[i].completionTime;
        }
        
        System.out.println("\nPID\tArrival Time\tBurst Time\tCompletion Time\tTurnaround Time\tWaiting Time");
        for (int i = 0; i < n; i++) {
            System.out.println(processes[i].pid + "\t" + processes[i].arrivalTime + "\t\t" + processes[i].burstTime + "\t\t" + processes[i].completionTime + "\t\t" + processes[i].turnaroundTime + "\t\t" + processes[i].waitingTime);
        }
        
        scanner.close();
    }
}
