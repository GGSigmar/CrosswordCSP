public class Word
{
    int x_pos;
    int y_pos;
    boolean isVertical;
    String content;

    public Word(int x_pos, int y_pos, boolean isVertical, String content)
    {
        this.x_pos = x_pos;
        this.y_pos = y_pos;
        this.isVertical = isVertical;
        this.content = content;
    }
}
