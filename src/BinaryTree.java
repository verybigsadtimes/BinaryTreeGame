import java.io.*;
import java.util.Scanner;

class Node implements Serializable {
    String data;
    Node left;
    Node right;

    public Node(String data) {
        this.data = data;
        this.left = null;
        this.right = null;
    }
}

public class BinaryTree {
    private Node root;

    public BinaryTree() {
        root = null;
    }

    public void constructTree() {
        root = new Node("Is it living?");
        root.left = new Node("Does it have arms?");
        root.right = new Node("Can you sit on it?");
        root.left.left = new Node("Is it a mammal?");
        root.left.right = new Node("Is it a bird?");
        root.right.left = new Node("Is it a chair?");
        root.right.right = new Node("Is it a table?");
    }

    public void saveTree(String filename) {
        try {
            FileOutputStream fileOut = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(root);
            out.close();
            fileOut.close();
            System.out.println("Saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving to " + filename);
        }
    }

    public void loadTree(String filename) {
        try {
            FileInputStream fileIn = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            root = (Node) in.readObject();
            in.close();
            fileIn.close();
            System.out.println("Loaded from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Error loading from " + filename);
        }
    }

    public void playGame() {
        Node current = root;
        Scanner scanner = new Scanner(System.in);
        int max_q = 20;
        for (int i = 0; i < max_q; i++) {
            System.out.println(current.data + " (y/n)");
            String answer = scanner.nextLine().toLowerCase();
            try {
                if (!answer.equals("y") && !answer.equals("n")) {
                    throw new IllegalArgumentException("Invalid input, please answer y or n");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
                i--;
                continue;
            }

            if (answer.equals("y")) {
                if (current.left == null) {
                    System.out.println("Is it " + current.data + "? (y/n)");
                    String guess = scanner.nextLine().toLowerCase();
                    if (guess.equals("y")) {
                        System.out.println("I win");
                    } else {
                        System.out.println("You win");
                    }
                    return;
                } else {
                    current = current.left;
                }
            } else {
                if (current.right == null) {
                    System.out.println("Is it " + current.data + "? (y/n)");
                    String guess = scanner.nextLine().toLowerCase();
                    if (guess.equals("y")) {
                        System.out.println("I win!");
                    } else {
                        System.out.println("You win!");
                    }
                    return;
                } else {
                    current = current.right;
                }
            }
        }
        System.out.println("I give up!");
    }

    public static void main(String[] args) {
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.constructTree();
        binaryTree.playGame();

        binaryTree.saveTree("tree_state.ser");
        binaryTree.loadTree("tree_state.ser");
    }
}
