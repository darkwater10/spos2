import java.util.*;

class Process {
    int pid;
    int arrivalTime;
    int burstTime;
    int remainingTime;
    int completionTime;
    int turnaroundTime;
    int waitingTime;
}

public class RoundRobin {
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
            processes[i].remainingTime = processes[i].burstTime;
            processes[i].pid = i + 1;
        }
        
        System.out.print("Enter time quantum: ");
        int timeQuantum = scanner.nextInt();
        
        int currentTime = 0;
        Queue<Process> queue = new LinkedList<>();
        int completedProcesses = 0;
        
        while (completedProcesses < n) {
            for (int i = 0; i < n; i++) {
                if (processes[i].arrivalTime <= currentTime && processes[i].remainingTime > 0) {
                    queue.add(processes[i]);
                }
            }
            
            if (!queue.isEmpty()) {
                Process currentProcess = queue.poll();
                
                int timeToExecute = Math.min(timeQuantum, currentProcess.remainingTime);
                currentProcess.remainingTime -= timeToExecute;
                currentTime += timeToExecute;
                
                if (currentProcess.remainingTime == 0) {
                    currentProcess.completionTime = currentTime;
                    currentProcess.turnaroundTime = currentProcess.completionTime - currentProcess.arrivalTime;
                    currentProcess.waitingTime = currentProcess.turnaroundTime - currentProcess.burstTime;
                    completedProcesses++;
                } else {
                    queue.add(currentProcess);
                }
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
