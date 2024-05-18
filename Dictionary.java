package interface_wordle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class Dictionary {
	
	public static boolean containsAccentCharacters(String text) {
	    String normalizedText = Normalizer.normalize(text, Form.NFD);
	    Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
	    return pattern.matcher(normalizedText).find();
	}
	
    public static List<String> getDico() {
        String urlStr = "https://fr.wiktionary.org/wiki/Wiktionnaire:Liste_de_1750_mots_français_les_plus_courants";
        
        List<String> words = new ArrayList<>(); 
         
        try {
            
            URL url = new URL(urlStr);

            // Connexion HTTP vers l'URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");

            // Lire le code source de la page web
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            StringBuilder sourcePage = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                sourcePage.append(line);
            }

            conn.disconnect();

            // Expression régulière pour extraire le texte entre les balises <p>
            Pattern patternP = Pattern.compile("<p[^>]*>(.*?)</p>");
            Matcher matcherP = patternP.matcher(sourcePage.toString());

            while (matcherP.find()) {
                String paragraph = matcherP.group(1);

                // Expression régulière pour extraire le texte entre les balises <a> à l'intérieur de <p>
                Pattern patternA = Pattern.compile("<a[^>]*>(.*?)</a>");
                Matcher matcherA = patternA.matcher(paragraph);

                while (matcherA.find()) {
                    String linkText = matcherA.group(1);
                    if (!linkText.contains("<span>")&& !linkText.contains("<img>") && !linkText.contains(" ") && !linkText.contains("-")) {
                    	if (!linkText.contains("'") && !containsAccentCharacters(linkText)) {
                    		words.add(linkText);
                    	}
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return words;
    }
    
    // Creation dictionnaire pour élément de comparaison avec le mot entré
    public List<String> getBigDictionary(){
    	String[] urls = {
                "https://fr.wiktionary.org/wiki/Wiktionnaire:Listes_de_fréquence/wortschatz-fr-1-2000",
                "https://fr.wiktionary.org/wiki/Wiktionnaire:Listes_de_fréquence/wortschatz-fr-2001-4000",
                "https://fr.wiktionary.org/wiki/Wiktionnaire:Listes_de_fréquence/wortschatz-fr-4001-6000",
                "https://fr.wiktionary.org/wiki/Wiktionnaire:Listes_de_fréquence/wortschatz-fr-6001-8000",
                "https://fr.wiktionary.org/wiki/Wiktionnaire:Listes_de_fréquence/wortschatz-fr-8001-10000"
            };

            List<String> dico = new ArrayList<>();
            
            for (String urlStr : urls) {
                try {
                    URL url = new URL(urlStr);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("GET");
                    
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    StringBuilder pageSource = new StringBuilder();
                    
                    while ((line = reader.readLine()) != null) {
                        pageSource.append(line);
                    }
                    
                    conn.disconnect();
                    
                    Pattern patternP = Pattern.compile("<li[^>]*>(.*?)</li>");
                    Matcher matcherP = patternP.matcher(pageSource.toString());
                    
                    while (matcherP.find()) {
                        String paragraph = matcherP.group(1);
                        if (!paragraph.contains("<img")) {
                            Pattern patternA = Pattern.compile("<a[^>]*>(.*?)</a>");
                            Matcher matcherA = patternA.matcher(paragraph);
                            
                            while (matcherA.find()) {
                                String linkText = matcherA.group(1);
                                String[] motFiltre = {"Aller au contenu", "termes d’utilisation", "Licence", "Code de conduite", "Statistiques", "Version mobile"};
                                boolean estFiltre = false;
                                if (!linkText.contains("<span>") && !linkText.contains("<img>") && !linkText.contains("'") && !containsAccentCharacters(linkText)) {
                                    for (String mot : motFiltre) {
                                        if (linkText.equals(mot)) {
                                            estFiltre = true;
                                            break;
                                        }
                                    }
                                    if (!estFiltre) {
                                        dico.add(linkText);
                                    }
                                }
                            }
                        }
                    }
                    
                } 
                
                
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String urlStr = "https://fr.wiktionary.org/wiki/Wiktionnaire:Liste_de_1750_mots_français_les_plus_courants";
            
            try {
                
                URL url = new URL(urlStr);

                // Connexion HTTP vers l'URL
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setRequestMethod("GET");

                // Lire le code source de la page web
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                StringBuilder sourcePage = new StringBuilder();

                while ((line = reader.readLine()) != null) {
                    sourcePage.append(line);
                }

                conn.disconnect();

                // Expression régulière pour extraire le texte entre les balises <p>
                Pattern patternP = Pattern.compile("<p[^>]*>(.*?)</p>");
                Matcher matcherP = patternP.matcher(sourcePage.toString());

                while (matcherP.find()) {
                    String paragraph = matcherP.group(1);

                    // Expression régulière pour extraire le texte entre les balises <a> à l'intérieur de <p>
                    Pattern patternA = Pattern.compile("<a[^>]*>(.*?)</a>");
                    Matcher matcherA = patternA.matcher(paragraph);

                    while (matcherA.find()) {
                        String linkText = matcherA.group(1);
                        if (!linkText.contains("<span>")&& !linkText.contains("<img>") && !linkText.contains(" ") && !linkText.contains("-")) {
                        	if (!linkText.contains("'") && !containsAccentCharacters(linkText)) {
                        		dico.add(linkText);
                        	}
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            return dico;

    }
    
    public String randomWord(String difficulty) {
        List<String> words = getDico();
        List<String> filterWords = new ArrayList<>();
        int minLength = 0;
        int maxLength = 0;
        if(difficulty.equals("CLASSIC")) {
        	minLength=4;
        	maxLength=6;
        }
        else if(difficulty.equals("HARD")) {
        	minLength=7;
        	maxLength=12;
        }

        for (String word : words) {
            int wordLength = word.length();
            if (wordLength >= minLength && wordLength <= maxLength) {
                filterWords.add(word);
            }
        }

        Random random = new Random();
        int randomHint = random.nextInt(filterWords.size());
        String randomWord = filterWords.get(randomHint);

        return randomWord;
    }

    
 
    public static String decodeUnicode(String input) {
    	StringBuilder output = new StringBuilder();
        int length = input.length();
        for (int i = 0; i < length; i++) {
        	char currentChar = input.charAt(i);
            if (currentChar == '\\' && i + 6 < length && input.charAt(i + 1) == 'u') {
            	// Found a Unicode escape sequence
                String hex = input.substring(i + 2, i + 6);
                try {
                	int unicodeValue = Integer.parseInt(hex, 16);
                    output.append((char) unicodeValue);
                    i += 5; // Skip the Unicode escape sequence
                } catch (NumberFormatException e) {
                	output.append(currentChar);
                }
                } else {
                    output.append(currentChar);
                }
            	}
            	return output.toString();
        }
    

    
   
    public List<String> sendRandomWord(String randomWord) {
    	
        try {
            // URL du serveur python
            String url = "http://0.0.0.0:8080/votre-endpoint";

            URL serverUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();
            
            //Configuration de la connexion en POST
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            // corps de la reqûete en JSON
            String requestBody = "{\"motAleatoire\": \"" + randomWord + "\"}";

            // Écriture de la chaîne JSON dans le corps de la requête
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = requestBody.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            // Récupération de la requête
            int responseCode = conn.getResponseCode();
            if (responseCode == 200) {
                // La requête a réussi
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                
                String responseString = decodeUnicode(response.toString());
                int startIndex = responseString.indexOf("[") + 1; 
                int endIndex = responseString.lastIndexOf("]");    
                String chosenWordList = responseString.substring(startIndex, endIndex);
                String[] words = chosenWordList.split(","); 
                List<String> wordsList = new ArrayList<>();
                
                for (String word : words) {
                    wordsList.add(word.replace("\"", ""));
                }

                return wordsList;
            } else {
                return null;
            }
            
        } catch (Exception e) {
            return null;
        }
    }
}
