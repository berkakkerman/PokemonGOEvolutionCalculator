package my.app.pgevolutioncalc;

/**
 * Created by ASUS on 23.7.2016.
 */
public class Pokemon {
    public String name;
    public String imagePath;
    public double lowerMultiplier;
    public double higherMultiplier;
    public String evolvedPokemon;

    public Pokemon() {
    }

    public Pokemon(String name, double lowerMultiplier, double higherMultiplier, String evaluatedPokemon) {
        this.name = name;
        this.lowerMultiplier = lowerMultiplier;
        this.higherMultiplier = higherMultiplier;
        this.evolvedPokemon = evaluatedPokemon;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public double getLowerMultiplier() {
        return lowerMultiplier;
    }

    public void setLowerMultiplier(double lowerMultiplier) {
        this.lowerMultiplier = lowerMultiplier;
    }

    public String getEvolvedPokemon() {
        return evolvedPokemon;
    }

    public void setEvolvedPokemon(String evaluatedPokemon) {
        this.evolvedPokemon = evaluatedPokemon;
    }

    public double getHigherMultiplier() {
        return higherMultiplier;
    }

    public void setHigherMultiplier(double higherMultiplier) {
        this.higherMultiplier = higherMultiplier;
    }
}
