
package project.dao;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import project.domain.Observation;
import project.domain.User;

public class FileObservationDao implements ObservationDao {
    private List<Observation> observations;
    private String file;

    public FileObservationDao(String file, UserDao users) throws Exception {
        observations = new ArrayList<>();
        this.file = file;
        try {
            Scanner reader = new Scanner(new File(file));
            while (reader.hasNextLine()) {
                String[] parts = reader.nextLine().split(";");
                int id = Integer.parseInt(parts[0]);
                
                SimpleDateFormat dformatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = dformatter.parse(parts[3]);
                LocalTime time = LocalTime.parse(parts[4]);
                
                User user = users.getAll().stream().filter(u->u.getUsername().equals(parts[6])).findFirst().orElse(null);
                
                Observation obs = new Observation(id, parts[1], parts[2], date, time, parts[5], user);
                observations.add(obs);
            }
        } catch (Exception e) {
            FileWriter writer = new FileWriter(new File(file));
            writer.close();
        }
        
    }
    
    private void save() throws Exception{
        try (FileWriter writer = new FileWriter(new File(file))) {
            for (Observation obs : observations) {
                writer.write(obs.getId() + ";" + obs.getSpecies() + ";" + obs.getPlace() + ";" + obs.getDate()
                        + ";" + obs.getTime() + ";" + obs.getInfo() + ";" + obs.getUser() +  "\n");
            }
        } 
    }
    
    private int generateId() {
        return observations.size() + 1;
    }
    
    
    @Override
    public List<Observation> getAll() {
        return observations;
    }
    
    @Override
    public Observation findBySpecies(String species) {
        return observations.stream()
            .filter(o->o.getSpecies()
            .equals(species))
            .findFirst()
            .orElse(null);
    }
    
    @Override
    public Observation findByPlace(String place) {
                return observations.stream()
            .filter(o->o.getPlace()
            .equals(place))
            .findFirst()
            .orElse(null);
    }    
    
    @Override
    public Observation create(Observation obs) throws Exception {
        observations.add(obs);
        save();
        return obs;
    }    


    
    
}
