import java.io.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringBuilder sb = new StringBuilder();

        int year;

        year = Integer.parseInt(br.readLine());
        sb.append(year - 1946);

        bw.write(sb.toString());
        bw.flush();
        bw.close();
    }
}