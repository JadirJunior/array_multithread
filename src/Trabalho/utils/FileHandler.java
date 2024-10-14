package Trabalho.utils;

import javax.swing.text.html.Option;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FileHandler {

    private File file;

    private boolean isLoaded;

    public FileHandler(String path) {
        file = new File(path);
    }

    public Optional<User> getUserByUserName(User user) {

        List<String> lines = new ArrayList<>();
        try (
                BufferedReader br = new BufferedReader(new FileReader(file))
                ) {
            String st;

            boolean isValid = false;
            while ((st = br.readLine()) != null) {
                String[] separated = st.split(";");

                if (separated[0].equals(user.getUser()) && separated[1].equals(user.getPass())) {
                    boolean authenticated = separated.length == 3;

                    if (authenticated) {
                        return null;
                    }

                    st = user.getUser()+";"+user.getPass()+";"+"1";
                    isValid = true;
                }

                lines.add(st);
            }

            if (!isValid) {
                return Optional.empty();
            }

            //Writing file
            writeFile(lines);

            user.setAuthenticated(true);

            return Optional.of(user);

        } catch (IOException e) {
            System.err.println("FileHandler: Erro de I/O na leitura dos dados");
            return Optional.empty();
        }

    }

    private void writeFile(List<String> lines) {
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(file));
                ) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("FileHandler: Ocorreu um erro ao atualizar o arquivo de usuários");
            e.printStackTrace();
        }
    }

    public void resetFile() {

    }


}
