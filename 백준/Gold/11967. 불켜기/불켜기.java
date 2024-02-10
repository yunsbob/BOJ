import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	private static int N, res = 1;
	private static boolean[][] map;
	private static boolean[][] flag;
	private static int[][] dir = {{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
	private static List<List<List<XY>>> switches = new ArrayList<>();

	private static class XY {
		int x, y;
		XY (int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		map = new boolean[N + 1][N + 1];
		flag = new boolean[N + 1][N + 1];
		switches.add(new ArrayList<>());
		for (int i = 1; i <= N; i++) {
			switches.add(new ArrayList<>());
			for (int j = 0; j <= N; j++) {
				switches.get(i).add(new ArrayList<>());
			}
		}

		int M = Integer.parseInt(st.nextToken());
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			int x2 = Integer.parseInt(st.nextToken());
			int y2 = Integer.parseInt(st.nextToken());
			switches.get(x).get(y).add(new XY(x2, y2));
		}

		map[1][1] = true;
		bfs();
		System.out.println(res);
	}

	private static void bfs() {
		boolean r = false; // 탐색을 더 할지 여부
		boolean[][] visited = new boolean[N + 1][N + 1];
		visited[1][1] = true;
		Queue<XY> q = new ArrayDeque<>();
		q.offer(new XY(1, 1));

		while (!q.isEmpty()) {
			XY now = q.poll();
			if (!flag[now.x][now.y]) { // 해당 방에서 스위치를 킨적이 없으면
				for (XY next : switches.get(now.x).get(now.y)) { // 해당 방에 존재하는 모든 스위치를 탐색
					if (!map[next.x][next.y]) { // 불이 꺼져있는 방이면 스위치를 켬
						r = true;
						res++;
						map[next.x][next.y] = true;
					}
				}

				flag[now.x][now.y] = true; // 해당 방에서 스위치를 켬
			}

			for (int i = 0; i < 4; i++) {
				int nx = now.x + dir[i][0];
				int ny = now.y + dir[i][1];
				if (nx <= 0 || ny <= 0 || nx > N || ny > N || !map[nx][ny] || visited[nx][ny]) continue;
				visited[nx][ny] = true;
				q.offer(new XY(nx, ny));
			}
		}

		if (r) bfs();
	}
}