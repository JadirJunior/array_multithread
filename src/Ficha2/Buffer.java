package Ficha2;

public class Buffer {

    //Volatile => Variável vai ser lida e escrita por várias Threads em simultâneo, utilizando cache
    public volatile static int[] buffer;

    public static boolean[] isOccuupied;

    public static int bufferSize;
    public static int min;
    public static int max;

}
