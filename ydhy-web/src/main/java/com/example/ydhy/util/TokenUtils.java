package com.example.ydhy.util;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import net.minidev.json.JSONObject;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class TokenUtils {

    public static void main(String[] ages) {
        String token = createToken(1150299353,"fuzf0206");
        String token2=createToken(1150299352,"mady0115");

//        Map<String, Object> map = validToken(token);
//        for (Map.Entry<String, Object> entry : map.entrySet()) {
//            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//        }
    }

    public static String createToken(int uid,String account) {

        Map<String, Object> map = new HashMap<>();

        map.put("uid", uid);
        map.put("account",account);
        map.put("sta", LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8")));
        map.put("exp", LocalDateTime.now().plusHours(24).toEpochSecond(ZoneOffset.of("+8")));
        try {
            String token = TokenUtils.create(map);
            System.out.println("token=" + token);
            return token;
        } catch (JOSEException e) {
            System.out.println("生成token失败");
            e.printStackTrace();
        }
        return null;

    }

    public static Map<String, Object> validToken(String token) {

        try {
            if (token != null) {
                Map<String, Object> returnMap = new HashMap<>(16);
                Map<String, Object> validMap = TokenUtils.valid(token);
                int i = (int) validMap.get("Result");
                if (i == 0) {
                    System.out.println("token解析成功");
                    JSONObject jsonObject = (JSONObject) validMap.get("data");
                    returnMap.put("uid", jsonObject.get("uid"));
                    returnMap.put("sta", jsonObject.get("sta"));
                    returnMap.put("exp", jsonObject.get("exp"));
                } else if (i == 2) {
                    System.out.println("token已经过期");
                }
                return returnMap;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final byte[] secret = "zhejiangkejixueyuanruanjiangongcheng".getBytes();

    private static String create(Map<String, Object> payloadMap) throws JOSEException {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
        Payload payload = new Payload(new JSONObject(payloadMap));
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        JWSSigner jwsSigner = new MACSigner(secret);
        jwsObject.sign(jwsSigner);
        return jwsObject.serialize();
    }


    private static Map<String, Object> valid(String token) throws ParseException, JOSEException {

        JWSObject jwsObject = JWSObject.parse(token);
        Payload payload = jwsObject.getPayload();
        JWSVerifier jwsVerifier = new MACVerifier(secret);
        Map<String, Object> resultMap = new HashMap<>();
        if (jwsObject.verify(jwsVerifier)) {
            resultMap.put("Result", 0);
            JSONObject jsonObject = payload.toJSONObject();
            resultMap.put("data", jsonObject);
            if (jsonObject.containsKey("exp")) {
                Long expTime = Long.valueOf(jsonObject.get("exp").toString());
                Long nowTime = LocalDateTime.now().toEpochSecond(ZoneOffset.of("+8"));
                if (nowTime > expTime) {
                    resultMap.clear();
                    resultMap.put("Result", 2);
                }
            }
        } else {
            resultMap.put("Result", 1);
        }
        return resultMap;
    }
}
