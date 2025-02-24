package org.songxueyu.cdgy.fruitsaleproject.Until;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.Calendar;
import java.util.Date;

@Slf4j
public class JwtUtil {

    //密钥
    private final static String SECRET="20230802CreatedByAaronzhuInSouthampton000000000000000000000000";
    //Authorization


    /**
    * @param: user_account
    * @return: token
    * */
    public static String createToken(String user_account){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,24*60*60*7);
        String token = Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setSubject(user_account)
                .setIssuedAt(new Date())
                .setExpiration(calendar.getTime())
                .signWith(SignatureAlgorithm.HS256,SECRET)
                .compact();
        return token;
    }
    /**
    * @param: 检验token是否有效
    * @return : boolean
    * */
    public static boolean verifyToken(String token){
        if (StringUtils.isEmpty(token)) return false;
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        }catch (Exception e){
            log.info("Jwt校验失败"+e.toString());
            return false;
        }
        return true;
    }

    /**
    * @param: 检验token是否过期
    * @return : boolean
    * */
    public static boolean judgeTokenExpiration(Date expiration){
        return expiration.before(new Date());
    }
    /**
    * @param: token
    * @return: token中存放的用户信息，一般是user_account
    * */
    public static String getInfoFromToken(String token){
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
        return claimsJws.getBody().getSubject();
    }

}
