import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class WordList
{
    public LinkedList<String> listOfWords;

    WordList()
    {
        this.listOfWords = new LinkedList<>();
    }

    public void readDictFile(String filename)
    {
        try (BufferedReader br = new BufferedReader(new FileReader(filename+".txt")))
        {
            listOfWords.clear();

            String line = br.readLine();
            int i = 0;

            while(line != null)
            {
                String splitedRow[] = line.split("\\s");

                if(splitedRow[3].equals("v") || splitedRow[3].equals("n") || splitedRow[3].equals("adv") || splitedRow[3].equals("a"))
                {
                    //System.out.println(i+" "+splitedRow[2]+" "+splitedRow[3]+" added to the list!");
                    if(listOfWords.contains(splitedRow[2])) // wywalanie duplikatow
                    {
                        i++;
                        line = br.readLine();
                        continue;
                    }
                    listOfWords.add(splitedRow[2]);
                }

                i++;
                line = br.readLine();
            }

            br.close();

            Collections.shuffle(listOfWords);

        } catch (Exception e) {
            System.out.println("Something went wrong!");
        }
    }
}
