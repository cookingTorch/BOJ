import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st;
		
		int m, f;
		
		st = new StringTokenizer(br.readLine());
		m = Integer.parseInt(st.nextToken());
		f = Integer.parseInt(st.nextToken());
		while (!(m == 0 && f == 0)) {
			sb.append(m + f).append('\n');
			st = new StringTokenizer(br.readLine());
			m = Integer.parseInt(st.nextToken());
			f = Integer.parseInt(st.nextToken());
		}
		
		bw.write(sb.toString());
		bw.flush();
		bw.close();
		br.close();
	}
}