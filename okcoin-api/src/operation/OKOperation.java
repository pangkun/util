package operation;

import org.json.JSONException;
import org.json.JSONObject;
import utils.MD5Utils;
import utils.NetUtils;
import utils.UserApi;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 庞昆 on 2017/9/5.
 */
public class OKOperation {
    public static void main(String[] args) {
        try {
            OKOperation okOperation = new OKOperation();
            String userInfo = okOperation.getUserInfo();
            System.out.println(okOperation.getBuy());
            System.out.println(okOperation.getSell());
            String trade = okOperation.trade(UserApi.SELL, 300.0, 0.01);
            String orderInfo = okOperation.getOrderInfo("94749762");
            System.out.println(orderInfo);
            System.out.println(trade);
            System.out.println(userInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getUserInfo() throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("api_key", UserApi.OK_API_KEY);
        String sign = MD5Utils.buildSign(map, UserApi.OK_SECRET_KEY);
        BufferedWriter bufferedWriter = NetUtils.upLoad("https://www.okex.com/api/v1/userinfo.do");
        bufferedWriter.write("api_key=" + UserApi.OK_API_KEY + "&sign=" + sign);
        bufferedWriter.flush();
        return NetUtils.downLoad();
    }

    public String getSell() throws IOException, JSONException {
        String s = NetUtils.downLoad("https://www.okex.com/api/v1/ticker.do?symbol=" + UserApi.OK_COIN_TYPE);
        JSONObject jsonObject = new JSONObject(s);
        JSONObject ticker = (JSONObject) jsonObject.get("ticker");
        return ticker.getString("sell");
    }

    public String getBuy() throws IOException, JSONException {
        String s = NetUtils.downLoad("https://www.okex.com/api/v1/ticker.do?symbol=" + UserApi.OK_COIN_TYPE);
        JSONObject jsonObject = new JSONObject(s);
        JSONObject ticker = (JSONObject) jsonObject.get("ticker");
        return ticker.getString("buy");
    }

    public String getOrderInfo(String orderId) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("api_key", UserApi.OK_API_KEY);
        map.put("symbol", UserApi.OK_COIN_TYPE);
        map.put("order_id", orderId);
        String sign = MD5Utils.buildSign(map, UserApi.OK_SECRET_KEY);
        BufferedWriter bufferedWriter = NetUtils.upLoad("https://www.okex.com/api/v1/order_info.do");
        bufferedWriter.write("api_key=" + UserApi.OK_API_KEY + "&symbol=" + UserApi.OK_COIN_TYPE + "&order_id=" + orderId + "&sign=" + sign);
        bufferedWriter.flush();
        return NetUtils.downLoad();
    }

    public String cancelOrder(String orderId) throws IOException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("api_key", UserApi.OK_API_KEY);
        map.put("symbol", UserApi.OK_COIN_TYPE);
        map.put("order_id", orderId);
        String sign = MD5Utils.buildSign(map, UserApi.OK_SECRET_KEY);
        BufferedWriter bufferedWriter = NetUtils.upLoad("https://www.okex.com/api/v1/cancel_order.do");
        bufferedWriter.write("api_key=" + UserApi.OK_API_KEY + "&symbol=" + UserApi.OK_COIN_TYPE + "&order_id=" + orderId + "&sign=" + sign);
        bufferedWriter.flush();
        return NetUtils.downLoad();
    }

    public String trade(String type, Double price, Double amount) throws IOException, JSONException {
        Map<String, String> map = new HashMap<String, String>();
        map.put("api_key", UserApi.OK_API_KEY);
        map.put("symbol", UserApi.OK_COIN_TYPE);
        map.put("type", type);
        map.put("price", String.valueOf(price));
        map.put("amount", String.valueOf(amount));
        String sign = MD5Utils.buildSign(map, UserApi.OK_SECRET_KEY);
        BufferedWriter bufferedWriter = NetUtils.upLoad("https://www.okex.com/api/v1/trade.do");
        bufferedWriter.write("api_key=" + UserApi.OK_API_KEY + "&amount=" + amount + "&symbol=" + UserApi.OK_COIN_TYPE + "&type=" + type + "&price=" + price + "&sign=" + sign);
        bufferedWriter.flush();
        String downLoad = NetUtils.downLoad();
        JSONObject jsonObject = new JSONObject(downLoad);
        if (jsonObject.getString("result").equals("true")) {
            String order_id = jsonObject.getString("order_id");
            return order_id;
        }
        return null;
    }

}
