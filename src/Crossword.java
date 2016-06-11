import java.util.LinkedList;

public class Crossword
{
    public int width;
    public int height;
    public int wordsReq;
    public char[][] board;
    public LinkedList<Word> usedWords;
    public char placeholder = '.';
    public char prepostholder = '*';

    Crossword(int w, int h, int req)
    {
        width = w;
        height = h;
        wordsReq = req;
        usedWords = new LinkedList<>();

        board = new char[h][w];
        for(int i = 0; i<h; i++)
        {
            for(int j = 0; j<w; j++)
            {
                board[i][j]=placeholder;
            }
        }
    }

    public boolean checkIfContains(Word sprawdzaneDluzsze) // sprawdza czy mniejsze slowo zawiera sie w wiekszym
    {
        for(Word slowo : usedWords)
        {
            if(sprawdzaneDluzsze.content.contains(slowo.content))
            {
                if(slowo.isVertical && sprawdzaneDluzsze.isVertical)
                {
                    if(slowo.y_pos >= sprawdzaneDluzsze.y_pos && slowo.y_pos <= sprawdzaneDluzsze.y_pos+sprawdzaneDluzsze.content.length())
                    {
                        return true;
                    }
                }

                if(!slowo.isVertical && !sprawdzaneDluzsze.isVertical)
                {
                    if(slowo.x_pos >= sprawdzaneDluzsze.x_pos && slowo.x_pos <= sprawdzaneDluzsze.x_pos+sprawdzaneDluzsze.content.length())
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean isParallel(Word sprawdzane) // sprawddza czy slowa sa nieprawidlowo rownolegle
    {
        for(Word slowo : usedWords)
        {
            if(slowo.isVertical && sprawdzane.isVertical)
            {
                if(Math.abs(sprawdzane.y_pos-slowo.y_pos)==1)
                {
                    if(sprawdzane.x_pos+sprawdzane.content.length() >= slowo.x_pos || sprawdzane.x_pos <= slowo.x_pos+slowo.content.length())
                    {
                        return true;
                    }
                }
            }

            if(!slowo.isVertical && !sprawdzane.isVertical)
            {
                if(Math.abs(sprawdzane.x_pos-slowo.x_pos)==1)
                {
                    if(sprawdzane.y_pos+sprawdzane.content.length() >= slowo.y_pos || sprawdzane.y_pos <= slowo.y_pos+slowo.content.length())
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public Word checkIfCanBeAddedVertically(String checkedWord) // sprawdza czy slowo moze byc dodane pionowo
    {
        Word zwracane = new Word(-1,-1,true,checkedWord);

        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if((checkedWord.length()+i)>height) // czy wychodzi poza board w pionie
                {
                    continue;
                }

                boolean notProper = false;

                for(int k = 0; k < checkedWord.length(); k++)
                {
                    if(board[i+k][j] != placeholder && checkedWord.charAt(k) != board[i+k][j]) // czy litery sie zgadzaja
                    {
                        notProper = true;
                        break;
                    }
                }

                if(i!=0)
                {
                    if(board[i-1][j] != placeholder) // czy na miejsce przed nie ma innego slowa
                    {
                        notProper = true;
                    }
                }

                if(i+checkedWord.length()+1<height)
                {
                    if(board[i+checkedWord.length()][j] != placeholder) // czy na miejsce po nie ma innego slowa
                    {
                        notProper = true;
                    }
                }

                if(checkIfContains(new Word(i,j,true,checkedWord))) // czy zawiera ktores z poprzednich slow
                {
                    notProper = true;
                }

                if(isParallel(new Word(i,j,true,checkedWord))) // czy zawiera nieprawidlowa rownoleglosc
                {
                    notProper = true;
                }

                if(!notProper)
                {
                    zwracane = new Word(i,j,true,checkedWord); // przypisuje prawidlowe dane
                    return zwracane;
                }
            }
        }

        return zwracane;
    }

    public Word checkIfCanBeAddedHorizontally(String checkedWord)
    {
        Word zwracane = new Word(-1,-1,true,checkedWord);

        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if((checkedWord.length()+j)>width) // czy wychodzi poza board w pionie
                {
                    continue;
                }

                boolean notProper = false;

                for(int k = 0; k < checkedWord.length(); k++)
                {
                    if(board[i][j+k] != placeholder && checkedWord.charAt(k) != board[i][j+k])
                    {
                        notProper = true;
                        break;
                    }
                }

                if(j!=0)
                {
                    if(board[i][j-1] != placeholder)
                    {
                        notProper = true;
                    }
                }

                if(j+checkedWord.length()+1<width)
                {
                    if(board[i][j+checkedWord.length()] != placeholder)
                    {
                        notProper = true;
                    }
                }

                if(checkIfContains(new Word(i,j,false,checkedWord)))
                {
                    notProper = true;
                }

                if(isParallel(new Word(i,j,false,checkedWord)))
                {
                    notProper = true;
                }

                if(!notProper)
                {
                    zwracane = new Word(i,j,false,checkedWord);
                    return zwracane;
                }
            }
        }

        return zwracane;
    }

    public boolean addWord(String addedWord)
    {
        for(Word slowo : usedWords)
        {
            if(slowo.content.equals(addedWord))
            {
                return false;
            }
        }

        Word temp = checkIfCanBeAddedHorizontally(addedWord);


        if(temp.y_pos!=-1)
        {
            usedWords.add(temp);

            addWordsToBoard();
            return true;
        }

        temp = checkIfCanBeAddedVertically(addedWord);

        if(temp.x_pos!=-1)
        {
            usedWords.add(temp);

            addWordsToBoard();
            return true;
        }

        return false;
    }

    public void addWordsToBoard() // fizyczne wpisywanie znakow na boarda
    {
        for(int i = 0; i<height; i++)
        {
            for(int j = 0; j<width; j++)
            {
                board[i][j]=placeholder; // zerowanie
            }
        }

        for(Word slowo : usedWords)
        {
            if(slowo.isVertical)
            {
                for(int k = 0; k < slowo.content.length(); k++)
                {
                    board[slowo.x_pos+k][slowo.y_pos]=slowo.content.charAt(k);
                }

                if(slowo.x_pos!=0)
                {
                    board[slowo.x_pos-1][slowo.y_pos]=prepostholder; // blokada przed slowem
                }
                if(slowo.x_pos+slowo.content.length()!=height)
                {
                    board[slowo.x_pos+slowo.content.length()][slowo.y_pos]=prepostholder; // blokada po slowie
                }
            }
            else
            {
                for(int k = 0; k < slowo.content.length(); k++)
                {
                    board[slowo.x_pos][slowo.y_pos+k]=slowo.content.charAt(k);
                }

                if(slowo.y_pos!=0)
                {
                    board[slowo.x_pos][slowo.y_pos-1]=prepostholder; // blokada przed slowem
                }
                if(slowo.y_pos+slowo.content.length()!=width)
                {
                    board[slowo.x_pos][slowo.y_pos+slowo.content.length()]=prepostholder; // blokada po slowie
                }
            }
        }
    }

    public void drawCrossword() // rysowanie boarda
    {
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if(j==0)
                {
                    System.out.print("\n");
                }
                if(board[i][j]==prepostholder)
                {
                    System.out.print(placeholder);
                }
                else
                {
                    System.out.print(board[i][j]);
                }
            }
        }

        System.out.print("\n\n");
    }

    //int licznik = 0;

    public boolean backTracking(WordList danaLista, Crossword krzyzowka)
    {
        // To understand recursion, see the bottom of this method

        if(usedWords.size()==wordsReq)
        {
            return true;
        }

        for(String slowo : danaLista.listOfWords)
        {
            if(krzyzowka.addWord(slowo))
            {
                drawCrossword();
                System.out.println("\n"+usedWords.size()+"\n");

                //licznik++;

                if(backTracking(danaLista, krzyzowka))
                {
                    return true;
                }
            }
        }

        //System.out.println("Zbaktrakowalem "+licznik);
        usedWords.removeLast();
        return false;

        // To understand recursion, see the top of this method
    }

    public int boardScore()
    {
        int score = 0;

        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                switch (board[i][j])
                {
                    case 'a':
                    case 'e':
                    case 'i':
                    case 'o':
                    case 'u':
                    case 'l':
                    case 'n':
                    case 'r':
                    case 's':
                    case 't': score=score+1; break;
                    case 'd':
                    case 'g': score=score+2; break;
                    case 'b':
                    case 'c':
                    case 'm':
                    case 'p': score=score+3; break;
                    case 'f':
                    case 'h':
                    case 'w':
                    case 'y':
                    case 'v': score=score+4; break;
                    case 'k': score=score+5; break;
                    case 'j':
                    case 'x': score=score+8; break;
                    case 'q':
                    case 'z': score=score+10; break;
                    default: break;
                }
            }
        }

        return score;
    }

    public double boardFullPercent()
    {
        double percentage = 0;
        int totalCells = 0;//width*height;
        int letters = 0;

        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                totalCells++;

                if(board[i][j]!=placeholder)
                {
                    letters++;
                }
            }
        }

        percentage = (double) letters / (double) totalCells;

        return percentage;
    }

    public static void main(String[] args)
    {
        long startTime = System.nanoTime();

        WordList mainWordList = new WordList();
        mainWordList.readDictFile("lemmas");

        int szerokosc = Integer.parseInt(args[0]);
        int wysokosc = Integer.parseInt(args[1]);
        int wordsReq = Integer.parseInt(args[2]);

        Crossword myCW = new Crossword(szerokosc, wysokosc, wordsReq);

        myCW.backTracking(mainWordList,myCW);

        myCW.drawCrossword();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        duration = duration / 1000000;

        System.out.println("Time of execution: "+duration+" miliseconds");
        System.out.println("Board Scrabble-like score: "+myCW.boardScore());
        System.out.println("Percentage of board filled: "+myCW.boardFullPercent());
    }
}
