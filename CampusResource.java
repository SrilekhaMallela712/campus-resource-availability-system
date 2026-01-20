import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

// Resource class
class Resource implements Serializable {
    String name;
    String type;
    String status;

    public Resource(String name, String type) {
        this.name = name;
        this.type = type;
        this.status = "Available";
    }

    @Override
    public String toString() {
        return "Name: " + name + ", Type: " + type + ", Status: " + status;
    }
}

// ResourceManager class
class ResourceManager {
    ArrayList<Resource> resources;

    public ResourceManager() {
        resources = new ArrayList<>();
    }

    public void addResource(String name, String type) {
        Resource r = new Resource(name, type);
        resources.add(r);
        System.out.println("Resource added successfully.");
    }

    public void viewResources() {
        if (resources.isEmpty()) {
            System.out.println("No resources found.");
        } else {
            for (Resource r : resources) {
                System.out.println(r);
            }
        }
    }

    public void updateStatus(String name, String status) {
        boolean found = false;
        for (Resource r : resources) {
            if (r.name.equalsIgnoreCase(name)) {
                r.status = status;
                System.out.println("Status updated.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Resource not found.");
        }
    }

    // Save resources to file
    public void saveToFile(String filename) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(resources);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error saving resources: " + e.getMessage());
        }
    }

    // Load resources from file
    @SuppressWarnings("unchecked")
    public void loadFromFile(String filename) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            resources = (ArrayList<Resource>) ois.readObject();
            ois.close();
        } catch (Exception e) {
            resources = new ArrayList<>();
        }
    }
}

// Main class
public class CampusResource {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ResourceManager manager = new ResourceManager();
        String filename = "resources.txt";

        // Load existing resources if file exists
        manager.loadFromFile(filename);

        int choice;
        do {
            System.out.println("\n--- Campus Resource Availability System ---");
            System.out.println("1. Add Resource");
            System.out.println("2. View Resources");
            System.out.println("3. Update Status");
            System.out.println("0. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Resource Name: ");
                    String name = sc.nextLine();
                    System.out.print("Enter Resource Type: ");
                    String type = sc.nextLine();
                    manager.addResource(name, type);
                    break;
                case 2:
                    manager.viewResources();
                    break;
                case 3:
                    System.out.print("Enter Resource Name: ");
                    String rName = sc.nextLine();
                    System.out.print("Enter new status (Available/Occupied): ");
                    String status = sc.nextLine();
                    manager.updateStatus(rName, status);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }

            // Save resources after each operation
            manager.saveToFile(filename);

        } while (choice != 0);

        sc.close();
    }
}

