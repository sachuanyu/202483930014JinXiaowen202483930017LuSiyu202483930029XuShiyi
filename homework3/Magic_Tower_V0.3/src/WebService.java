import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class WebService {
	// 固定端口为8081
	private static final String SERVER_PORT = "8081";

	// 登录验证核心方法（使用POST请求）
	boolean loginValidation(String un, String pw) {
		try {
			// 核心修改：登录路径添加 /api 前缀
			String url = "http://localhost:" + SERVER_PORT + "/api/login";

			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("POST");
			con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
			con.setRequestProperty("Accept", "application/json");
			con.setDoOutput(true);

			// 发送JSON格式的POST数据（原有逻辑不变）
			String jsonInput = "{\"username\":\"" + un + "\",\"password\":\"" + pw + "\"}";
			DataOutputStream wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(jsonInput);
			wr.flush();
			wr.close();

			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line.trim());
			}
			String result = response.toString();
			System.out.println("Response Body: " + result);

			// 解析登录结果（原有逻辑不变）
			boolean loginSuccess = parseSuccessFromJson(result);
			System.out.println("Login Success: " + loginSuccess);

			in.close();

			return loginSuccess;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}

	// 手动解析JSON success字段的方法（原有逻辑不变）
	private boolean parseSuccessFromJson(String json) {
		String successKey = "\"success\":";
		int successIndex = json.indexOf(successKey);
		if (successIndex != -1) {
			int valueStart = successIndex + successKey.length();
			int valueEnd = json.indexOf(',', valueStart);
			if (valueEnd == -1) {
				valueEnd = json.indexOf('}', valueStart);
			}
			if (valueEnd != -1) {
				String value = json.substring(valueStart, valueEnd).trim();
				return value.equals("true");
			} else {
				String value = json.substring(valueStart).trim();
				if (value.startsWith("true")) return true;
				if (value.startsWith("false")) return false;
			}
		}
		return false;
	}

	// 获取玩家列表（核心修改：添加 /api 前缀）
	void fetchPlayers(int wid) {
		try {
			String worldId = URLEncoder.encode(Integer.toString(wid), "UTF-8");
			// 核心修改：players路径添加 /api 前缀
			String url = "http://localhost:" + SERVER_PORT + "/api/players?wid=" + worldId;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line.trim());
			}
			String result = response.toString();
			System.out.println("Response Body: " + result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 获取世界列表（核心修改：添加 /api 前缀）
	void fetchWorlds(int pid) {
		try {
			String playerId = URLEncoder.encode(Integer.toString(pid), "UTF-8");
			// 核心修改：worlds路径添加 /api 前缀
			String url = "http://localhost:" + SERVER_PORT + "/api/worlds?pid=" + playerId;
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line.trim());
			}
			String result = response.toString();
			System.out.println("Response Body: " + result);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}