import java.util.*;

public class RecommendationSystem {

    // Sample user ratings
    // Rating scale: 1 to 5
    private static final Map<String, Map<String, Integer>> userRatings =
            new HashMap<>();

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("      AI RECOMMENDATION SYSTEM");
        System.out.println("======================================");

        loadSampleData();

        Scanner scanner = new Scanner(System.in);

        System.out.println("\nAvailable users:");
        System.out.println("1. Lavanya");
        System.out.println("2. Rahul");
        System.out.println("3. Priya");

        System.out.print("\nEnter username: ");
        String username = scanner.nextLine();

        if (!userRatings.containsKey(username)) {

            System.out.println(
                    "\nUser not found. Please use one of the sample users."
            );

            scanner.close();
            return;
        }

        System.out.println(
                "\nGenerating recommendations for " + username + "..."
        );

        List<String> recommendations =
                generateRecommendations(username);

        System.out.println(
                "\n========== RECOMMENDATIONS =========="
        );

        if (recommendations.isEmpty()) {

            System.out.println(
                    "No new recommendations available."
            );

        } else {

            int rank = 1;

            for (String product : recommendations) {

                System.out.println(
                        rank + ". " + product
                );

                rank++;
            }
        }

        System.out.println(
                "====================================="
        );

        scanner.close();
    }

    // Loads sample user-product ratings
    private static void loadSampleData() {

        Map<String, Integer> lavanyaRatings =
                new HashMap<>();

        lavanyaRatings.put("Laptop", 5);
        lavanyaRatings.put("Smartphone", 5);
        lavanyaRatings.put("Headphones", 4);
        lavanyaRatings.put("Camera", 2);
        lavanyaRatings.put("Smartwatch", 1);

        userRatings.put("Lavanya", lavanyaRatings);


        Map<String, Integer> rahulRatings =
                new HashMap<>();

        rahulRatings.put("Laptop", 5);
        rahulRatings.put("Smartphone", 4);
        rahulRatings.put("Headphones", 5);
        rahulRatings.put("Camera", 1);
        rahulRatings.put("Smartwatch", 4);

        userRatings.put("Rahul", rahulRatings);


        Map<String, Integer> priyaRatings =
                new HashMap<>();

        priyaRatings.put("Laptop", 2);
        priyaRatings.put("Smartphone", 3);
        priyaRatings.put("Headphones", 2);
        priyaRatings.put("Camera", 5);
        priyaRatings.put("Smartwatch", 5);

        userRatings.put("Priya", priyaRatings);
    }

    // Generates recommendations using user similarity
    private static List<String> generateRecommendations(
            String targetUser) {

        Map<String, Integer> targetRatings =
                userRatings.get(targetUser);

        String mostSimilarUser = null;
        double highestSimilarity = -1;

        // Find the most similar user
        for (String otherUser : userRatings.keySet()) {

            if (otherUser.equals(targetUser)) {
                continue;
            }

            double similarity =
                    calculateSimilarity(
                            targetRatings,
                            userRatings.get(otherUser)
                    );

            if (similarity > highestSimilarity) {

                highestSimilarity = similarity;
                mostSimilarUser = otherUser;
            }
        }

        System.out.println(
                "\nMost similar user: " + mostSimilarUser
        );

        System.out.printf(
                "Similarity score: %.2f%n",
                highestSimilarity
        );

        List<String> recommendations =
                new ArrayList<>();

        if (mostSimilarUser == null) {
            return recommendations;
        }

        Map<String, Integer> similarUserRatings =
                userRatings.get(mostSimilarUser);

        // Recommend highly rated products
        // that the target user has not rated highly
        for (Map.Entry<String, Integer> entry :
                similarUserRatings.entrySet()) {

            String product = entry.getKey();
            int similarUserRating = entry.getValue();

            int targetUserRating =
                    targetRatings.getOrDefault(product, 0);

            if (similarUserRating >= 4 &&
                    targetUserRating < 4) {

                recommendations.add(product);
            }
        }

        return recommendations;
    }

    // Calculates cosine similarity between two users
    private static double calculateSimilarity(
            Map<String, Integer> user1,
            Map<String, Integer> user2) {

        double dotProduct = 0;
        double magnitudeUser1 = 0;
        double magnitudeUser2 = 0;

        for (String product : user1.keySet()) {

            if (!user2.containsKey(product)) {
                continue;
            }

            int rating1 = user1.get(product);
            int rating2 = user2.get(product);

            dotProduct += rating1 * rating2;

            magnitudeUser1 += rating1 * rating1;
            magnitudeUser2 += rating2 * rating2;
        }

        if (magnitudeUser1 == 0 ||
                magnitudeUser2 == 0) {

            return 0;
        }

        return dotProduct /
                (Math.sqrt(magnitudeUser1) *
                        Math.sqrt(magnitudeUser2));
    }
}
