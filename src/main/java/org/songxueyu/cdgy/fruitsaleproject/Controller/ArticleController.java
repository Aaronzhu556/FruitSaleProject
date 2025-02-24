package org.songxueyu.cdgy.fruitsaleproject.Controller;

import org.songxueyu.cdgy.fruitsaleproject.DTO.ArticleDTO;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Article;
import org.songxueyu.cdgy.fruitsaleproject.Entity.Comment;
import org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.CommentMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.LikeMapper;
import org.songxueyu.cdgy.fruitsaleproject.Mapper.UserMapper;
import org.songxueyu.cdgy.fruitsaleproject.Response.MyResponse;
import org.songxueyu.cdgy.fruitsaleproject.Service.Interface.ArticleService;
import org.songxueyu.cdgy.fruitsaleproject.Util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
    @Autowired
    private LikeMapper likeMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserMapper userMapper;
    @ResponseBody
    @RequestMapping("/getallarticle")
    public MyResponse getALlArticle(@RequestBody QueryInfo queryInfo, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        List<ArticleDTO> allArticle = articleService.getAllArticle(queryInfo);
        //   List<Article> ArticleList = new LinkedList<>();
        int page = 0;
        if (!allArticle.isEmpty()){

            List<String> Likelist = likeMapper.GetAllArticleLike(JwtUtil.getUserAccountFromToken(token));
            for (int i=0;i<allArticle.size();i++) {
                allArticle.get(i).setArticle_dto_user_img(userMapper.GetUserImgByName(allArticle.get(i).getArticle_dto_user_name()));
                if (Likelist.contains(allArticle.get(i).getArticle_dto_id())){
                    allArticle.get(i).setArticle_dto_user_like(true);
                }else allArticle.get(i).setArticle_dto_user_like(false);
            }
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(allArticle)
                    .info(String.valueOf(allArticle.size()))
                    .page(String.valueOf(page)).build();

        }else return MyResponse.builder()
                .code("201")
                .msg("没有找到您需要的信息").build();

    }

    @ResponseBody
    @RequestMapping("/getcontent")
    public String getContentById(@RequestParam String article_id){
        return articleService.getContent(article_id);
    }

    @ResponseBody
    @RequestMapping("/addarticle")
    public MyResponse addArticle(@RequestBody ArticleDTO article ){

        int i = articleService.addArticle(article);
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("发帖成功").build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("发帖失败").build();
    }

    @RequestMapping("/deletearticle")
    @ResponseBody
    public MyResponse deleteArticle(@RequestParam String article_id ){

        int flag = articleService.deleteArticle(article_id);
        if (flag!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("删除成功").build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("删除失败").build();

    }

    @ResponseBody
    @RequestMapping("/addlikearticle")
    public MyResponse AddLikeArticle(@RequestParam String article_id, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        int i = articleService.AddArticleLike(article_id, JwtUtil.getUserAccountFromToken(token));
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("增添成功").build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("增添失败").build();



    }
    @ResponseBody
    @RequestMapping("/deletelikearticle")
    public MyResponse DeleteArticle(@RequestParam String article_id,HttpServletRequest request ){
        String token = request.getHeader("Authorization");
        int i = articleService.DeleteArticleLike(article_id,JwtUtil.getUserAccountFromToken(token));
        if (i!=0)
            return MyResponse.builder()
                    .code("200")
                    .msg("增添成功").build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("增添失败").build();


    }

    @ResponseBody
    @RequestMapping("/getallarticlebyuser")
    public MyResponse GetAllArticleByUser(@RequestParam String article_user_name,HttpServletRequest request){
        String token = request.getHeader("Authorization");
        List<ArticleDTO> articles = articleService.GetAllArticleByUser(article_user_name);
        List<String> Likelist = likeMapper.GetAllArticleLike(JwtUtil.getUserAccountFromToken(token));
        for (int i=0;i<articles.size();i++) {
            if (Likelist.contains(articles.get(i).getArticle_dto_id())){
                articles.get(i).setArticle_dto_user_like(true);
            }else articles.get(i).setArticle_dto_user_like(false);
        }

        if (!articles.isEmpty())
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(articles).build();
        else
            return MyResponse.builder()
                    .code("201")
                    .msg("查询失败").build();


    }
    @ResponseBody
    @RequestMapping("/addclicknum")
    public MyResponse AddClickNum(@RequestParam String article_id ){
        int i = articleService.UpdateArticleClick(article_id);
        return MyResponse.builder()
                .code("200")
                .msg("记录成功").build();

    }

    @ResponseBody
    @RequestMapping("/gethotarticle")
    public MyResponse GetHotArticle() throws ParseException {
        List<ArticleDTO> articles = articleService.GetHotArticle();

        return
                MyResponse.builder()
                        .code("200")
                        .msg("分析成功")
                        .object(articles.size()>=5?articles.subList(0,5):articles).build();
    }
    @ResponseBody
    @RequestMapping("/getallarticlenum")
    public MyResponse getAllArticleNum() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> aClass = Class.forName("org.songxueyu.cdgy.fruitsaleproject.Entity.QueryInfo");
        Constructor<?> declaredConstructor = aClass.getDeclaredConstructor();
        declaredConstructor.setAccessible(true);
        QueryInfo queryInfo = (QueryInfo)declaredConstructor.newInstance();
        queryInfo.setQuerytext("");
        List<ArticleDTO> allArticle = articleService.getAllArticle(queryInfo);
        return MyResponse.builder()
                .code("200")
                .msg("计算成功")
                .info(String.valueOf(allArticle.size())).build();
    }

    @ResponseBody
    @RequestMapping("/getsimilararticle")
    public MyResponse GetSimilarArticle(@RequestParam String article_id,@RequestHeader("Authorization")String token){

            List<ArticleDTO> articles = articleService.GetSimilarArticle(article_id);
            return MyResponse.builder()
                    .code("200")
                    .msg("查询成功")
                    .object(articles)
                    .info(String.valueOf(articles.size())).build();

    }



}
