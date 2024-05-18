package interface_wordle;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Data {
	private String name;
	private int score;
	private int winStreak;
	private String difficulty; // CLASSIC or HARD
	private int flag[] = new int[4]; // 0 Classic Score 	1 Classic WS 	2 Hard Score 	3 Hard WS
	
	public Data(String name, int winStreak, int score, String difficulty, int[] flag) {
		this.name = name;
		this.score = score;
		this.winStreak = winStreak;
		this.difficulty = difficulty;
		this.flag = flag;
	}
	
	public void printData() {
		System.out.println(this.name + " " + this.score + " " + this.winStreak + " " + this.difficulty + " " + this.flag[0] + this.flag[1] + this.flag[2] + this.flag[3]);
	}
	
	public String getDataString() {
		return (this.name + " " + this.score + " " + this.winStreak + " " + this.difficulty + " " + this.flag[0] + this.flag[1] + this.flag[2] + this.flag[3]);
	}
	
	public String getDataStringForWinStreak() {
		return (this.name + "     " + this.winStreak + " " + this.difficulty);
	}
	
	public String getDataStringForScore() {
		return (this.name + "     " + this.score + " " + this.difficulty);
	}
	
	public String getName() {
		return name;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getWinStreak() {
		return winStreak;
	}
	
	public String getDifficulty() {
		return difficulty;
	}
	
	public int[] getFlag() {
		return flag;
	}
	
	public String getFlagAsString() {
		return "" + flag[0] + flag[1] + flag[2] + flag[3];
	}
	
	
	public static List<Data> readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String ligne;
            List<Data> dataTmp = new ArrayList<>();
            String[] colonnes;
            while ((ligne = reader.readLine()) != null) {
                colonnes = ligne.split("\\s+");
                int[] flag = new int[4];
                for (int i = 0; i < 4; i++) {
                    flag[i] = Character.getNumericValue(colonnes[4].charAt(i));
                }
                dataTmp.add(new Data(colonnes[0],Integer.parseInt(colonnes[1]),Integer.parseInt(colonnes[2]),colonnes[3],flag));
            }
            return dataTmp;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        }
        return null;
    }
	
	public static void writeFile(List<Data> dataTmp, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Data data : dataTmp) {
            	String ligne;
                ligne = data.getName() + " " + data.getWinStreak() + " " + data.getScore() + " " + data.getDifficulty() + " " + data.getFlagAsString();
                writer.write(ligne);
                writer.newLine();
            }

            System.out.println("Contenu mis à jour avec succès dans le fichier.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
    }
	
	public static List<Data> insertData(Data dataToInsert, List<Data> dataTmp) {
		List<Data> classicScore = new ArrayList<>();
		List<Data> classicWinStreak = new ArrayList<>();
		List<Data> hardScore = new ArrayList<>();
		List<Data> hardWinStreak = new ArrayList<>();
		
		//filling arrays with data by their flag
		for (Data data : dataTmp) {
			if(data.getFlag()[0] == 1) {
				classicScore.add(data);
			}
			if(data.getFlag()[1] == 1) {
				classicWinStreak.add(data);
			}
			if(data.getFlag()[2] == 1) {
				hardScore.add(data);
			}
			if(data.getFlag()[3] == 1) {
				hardWinStreak.add(data);
			}
        }
		
		
		//inserting data in the corresponding arrays
		if(dataToInsert.difficulty.equals("CLASSIC")) {
			classicScore.add(dataToInsert);
			dataToInsert.flag[0] = 1;
			classicWinStreak.add(dataToInsert);
			dataToInsert.flag[1] = 1;
		}
		if(dataToInsert.difficulty.equals("HARD")) {
			classicScore.add(dataToInsert);
			dataToInsert.flag[0] = 1;
			classicWinStreak.add(dataToInsert);
			dataToInsert.flag[1] = 1;
			hardScore.add(dataToInsert);
			dataToInsert.flag[2] = 1;
			hardWinStreak.add(dataToInsert);
			dataToInsert.flag[3] = 1;
		}
		
		//removing excess data and updating flags
		if(classicScore.size() <= 10 && classicWinStreak.size() <= 10 && hardScore.size() <= 10 && hardWinStreak.size() <= 10) {
			Set<Data> tmpSet = new HashSet<>();
			tmpSet.addAll(classicScore);
			tmpSet.addAll(classicWinStreak);
			tmpSet.addAll(hardScore);
			tmpSet.addAll(hardWinStreak);
			
			return dataTmp = new ArrayList<>(tmpSet);
		}
		else {
			//si le tableau contient plus de 10, enlever le 11e du tableau et lui retirer son flag
			while(classicScore.size() > 10 || classicWinStreak.size() > 10 || hardScore.size() > 10 || hardWinStreak.size() > 10){
				Data tmpData = null;
				
				//removing excess on classicScore
				if(classicScore.size() > 10) {
					for(Data data : classicScore) {
						if(tmpData == null || data.getScore() < tmpData.getScore()) {
							tmpData = data;
						}
					}
					if(tmpData != null) {
						classicScore.remove(tmpData);
						tmpData.flag[0] = 0;
						tmpData = null;
					}
				}
				
				//removing excess on classicWinStreak
				if(classicWinStreak.size() > 10) {
					for(Data data : classicWinStreak) {
						if(tmpData == null || data.getWinStreak() < tmpData.getWinStreak()) {
							tmpData = data;
						}
					}
					if(tmpData != null) {
						classicWinStreak.remove(tmpData);
						tmpData.flag[1] = 0;
						tmpData = null;
					}
				}
				
				//removing excess on hardScore
				if(hardScore.size() > 10) {
					for(Data data : hardScore) {
						if(tmpData == null || data.getScore() < tmpData.getScore()) {
							tmpData = data;
						}
					}
					if(tmpData != null) {
						hardScore.remove(tmpData);
						tmpData.flag[2] = 0;
						tmpData = null;
					}
				}
				
				//removing excess on hardWinStreak
				if(hardWinStreak.size() > 10) {
					for(Data data : hardWinStreak) {
						if(tmpData == null || data.getWinStreak() < tmpData.getWinStreak()) {
							tmpData = data;
						}
					}
					if(tmpData != null) {
						hardWinStreak.remove(tmpData);
						tmpData.flag[3] = 0;
						tmpData = null;
					}
				}
			}
			//merges les 4 tableaux (avec un set pour les duplicata) et return
			Set<Data> tmpSet = new HashSet<>();
			tmpSet.addAll(classicScore);
			tmpSet.addAll(classicWinStreak);
			tmpSet.addAll(hardScore);
			tmpSet.addAll(hardWinStreak);
			
			return dataTmp = new ArrayList<>(tmpSet);
		}
	}
}
