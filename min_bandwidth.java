import java.io.File;
import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.Scanner;
import java.util.ArrayList;
public class cse373_hw4 {
    static int minimumBandWidth;
    static ArrayList<Integer> globalSolution = new ArrayList<>();
    public static void main(String[] args) {

        Calendar calendar = Calendar.getInstance();
        long start = calendar.getTimeInMillis();

        Scanner stdin = new Scanner(System.in);
        System.out.print("File Name: ");
        String fileName = stdin.nextLine();
        ArrayList<Integer>[] adjacencyList;
        try {
            File myFile = new File(fileName);
            Scanner fileReader = new Scanner(myFile);
            int vertices = Integer.parseInt(fileReader.nextLine());
            int edges = Integer.parseInt(fileReader.nextLine());
            if (edges == 0) {
                System.out.println(0);
            }
            minimumBandWidth = vertices - 1;
            vertices++;
            adjacencyList = new ArrayList[vertices];
            for (int i = 0; i < vertices; i++) {
                adjacencyList[i] = new ArrayList<>();
            }
            for (int i = 0; i < edges; i++) {
                String data = fileReader.nextLine();
                String[] edgeInput = data.split(" ");
                int u = Integer.parseInt(edgeInput[0]);
                int v = Integer.parseInt(edgeInput[edgeInput.length - 1]);
                adjacencyList[u].add(v);
                adjacencyList[v].add(u);
            }
            fileReader.close();

            ArrayList<Integer> candidates = new ArrayList<>();
            for (int i = 1; i < vertices; i++) {
                candidates.add(i);
            }
            ArrayList<Integer> solution = new ArrayList<>();
            int[] positions = new int[vertices];
            for (int i = 0; i < positions.length; i++) {
                positions[i] = -1;
            }
            minBandWidth(adjacencyList, candidates, positions, solution, 0, candidates.size());
            System.out.println("Minimum Bandwidth: " + minimumBandWidth);
            System.out.println("Permutation: " + globalSolution);

            calendar = Calendar.getInstance();
            long end = calendar.getTimeInMillis();
            System.out.println(end-start + "ms");

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void minBandWidth(ArrayList<Integer>[] adjacencyList, ArrayList<Integer> candidates, int[] positions, ArrayList<Integer> solution, int currentBandWidth, int n) {
        if ((minimumBandWidth == 1) || (currentBandWidth != 0 && currentBandWidth >= minimumBandWidth)) {
            return;
        }
        if (solution.size() == n) {
            if (currentBandWidth < minimumBandWidth) {
                minimumBandWidth = currentBandWidth;
                globalSolution = new ArrayList<>();
                globalSolution.addAll(solution);
            }
            return;
        }
        int position = solution.size();

        for (int i = 0; i < candidates.size(); i++) {
            int temp = candidates.get(i);
            boolean greaterThanMin = false;
            for (int j = 0; j < adjacencyList[temp].size(); j++) {
                if ((positions[adjacencyList[temp].get(j)] != -1) && (currentBandWidth < (position - positions[adjacencyList[temp].get(j)]))) {
                    currentBandWidth = position - positions[adjacencyList[temp].get(j)];
                    if (minimumBandWidth <= currentBandWidth) {
                        greaterThanMin = true;
                        break;
                    }
                }
            }
            if (greaterThanMin) {
                continue;
            }
            candidates.remove(i);
            solution.add(temp);
            positions[temp] = position;
            minBandWidth(adjacencyList, candidates, positions, solution, currentBandWidth, n);
            candidates.add(i, temp);
            solution.remove(solution.size() - 1);
            positions[temp] = -1;
        }
    }
}
