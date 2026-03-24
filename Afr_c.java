package games;

import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class Afr_c {

    static String[] choices = {
        "Afghanistan", "Albania", "Algeria", "Andorra", "Angola",
        "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan",
        "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize",
        "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei",
        "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada",
        "Central African Republic", "Chad", "Chile", "China", "Colombia", "Comoros",
        "Democratic Republic of the Congo", "Republic of the Congo", "Costa Rica",
        "Côte d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czechia", "Denmark", "Djibouti",
        "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea",
        "Eritrea", "Estonia", "Eswatini", "Ethiopia", "Fiji", "Finland", "France", "Gabon",
        "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea",
        "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India",
        "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan",
        "Kazakhstan", "Kenya", "Kiribati", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon",
        "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Madagascar",
        "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania",
        "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro",
        "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands",
        "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "North Macedonia",
        "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea",
        "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia",
        "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines",
        "Samoa", "San Marino", "São Tomé and Príncipe", "Saudi Arabia", "Senegal", "Serbia",
        "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands",
        "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan",
        "Suriname", "Sweden", "Switzerland", "Syria", "Tajikistan", "Tanzania", "Thailand",
        "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey",
        "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom",
        "United States", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City", "Venezuela",
        "Vietnam", "Yemen", "Zambia", "Zimbabwe"
    };

    // Scramble a country name, preserving spaces and hyphens
    static String scramble(String country, Random random) {
        char[] letters = country.toCharArray();
        for (int i = 0; i < letters.length; i++) {
            if (letters[i] == ' ' || letters[i] == '-') continue; // Keep separators in place
            int j = random.nextInt(letters.length);
            while (letters[j] == ' ' || letters[j] == '-') {
                j = random.nextInt(letters.length);
            }
            char temp = letters[i];
            letters[i] = letters[j];
            letters[j] = temp;
        }
        return new String(letters);
    }

    // Provide a hint based on number of wrong attempts
    static String hint(String country, int tries) {
        if (tries == 2) {
            return "Hint: The first letter is '" + country.charAt(0) + "'";
        } else if (tries == 3) {
            StringBuilder vowels = new StringBuilder();
            for (char c : country.toCharArray()) {
                if ("aeiouAEIOU".indexOf(c) >= 0) {
                    vowels.append(c);
                } else if (c == ' ') {
                    vowels.append(' ');
                } else {
                    vowels.append('_');
                }
            }
            return "Hint: Vowels revealed: " + vowels;
        }
        return null;
    }

    // Play a single round with a country from the given list
    static void playRound(List<String> wordList, Random random, Scanner scanner) {
        int index = random.nextInt(wordList.size());
        String country = wordList.get(index);

        String scrambled = country;
        while (scrambled.equalsIgnoreCase(country)) {
            scrambled = scramble(country, random); // Ensure scrambled != original
        }

        System.out.println("Unscramble this country: " + scrambled.toUpperCase());
        System.out.println();

        int maxTries = 4;
        int tries = 0;

        while (tries < maxTries) {
            System.out.print("Enter your guess: ");
            String userGuess = scanner.nextLine().trim();
            tries++;

            if (userGuess.equalsIgnoreCase(country)) {
                System.out.println("Correct! You got it in " + tries + " attempt(s)!");
                return;
            }

            // Show hint after 2nd and 3rd wrong guess
            String h = hint(country, tries);
            if (h != null) {
                System.out.println(h);
            }

            int remaining = maxTries - tries;
            if (remaining > 0) {
                System.out.println("Wrong! " + remaining + " attempt(s) remaining.");
            } else {
                System.out.println("Out of attempts! The country was " + country + ".");
            }
        }
    }

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        List<String> easy = new ArrayList<>();
        List<String> medium = new ArrayList<>();
        List<String> hard = new ArrayList<>();

        for (String country : choices) {
            if (country.length() <= 6) {
                easy.add(country);
            } else if (country.length() <= 10) {
                medium.add(country);
            } else {
                hard.add(country);
            }
        }

        System.out.println("Welcome to the Unscramble the Country Game!");

        while (true) {
            System.out.println();
            System.out.print("Do you wish to play? (yes / no): ");
            String stillPlaying = scanner.nextLine().trim();

            if (stillPlaying.equalsIgnoreCase("no")) {
                System.out.println("Thanks for playing! Goodbye.");
                break;

            } else if (stillPlaying.equalsIgnoreCase("yes")) {
                System.out.print("Choose a level — (e) Easy, (m) Medium, (h) Hard: ");
                String level = scanner.nextLine().trim().toLowerCase();
                System.out.println();

                if (level.equals("e")) {
                    playRound(easy, random, scanner);
                } else if (level.equals("m")) {
                    playRound(medium, random, scanner);
                } else if (level.equals("h")) {
                    playRound(hard, random, scanner);
                } else {
                    System.out.println("Invalid level! Enter e, m, or h.");
                }

            } else {
                System.out.println("Please enter yes or no.");
            }
        }

        scanner.close();
    }
}